package com.workflow.engine.core;

import com.workflow.engine.event.*;
import com.workflow.engine.exception.*;
import com.workflow.engine.executor.*;
import com.workflow.engine.expression.ExpressionEvaluator;
import com.workflow.engine.node.*;
import com.workflow.engine.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

/**
 * Main workflow execution engine.
 * Orchestrates the execution of workflows and their nodes.
 */
public class WorkflowEngine {
    
    private static final Logger logger = LoggerFactory.getLogger(WorkflowEngine.class);
    
    private final ExecutorFactory executorFactory;
    private final ExpressionEvaluator expressionEvaluator;
    private final WorkflowEventPublisher eventPublisher;
    private final ExecutorService asyncExecutor;
    
    public WorkflowEngine() {
        this.executorFactory = new ExecutorFactory();
        this.expressionEvaluator = new ExpressionEvaluator();
        this.eventPublisher = new WorkflowEventPublisher();
        this.asyncExecutor = Executors.newCachedThreadPool();
    }
    
    /**
     * Registers a node executor.
     * 
     * @param executorClassName The executor class name
     * @param executor The executor instance
     */
    public void registerExecutor(String executorClassName, NodeExecutor executor) {
        executorFactory.registerExecutor(executorClassName, executor);
    }
    
    /**
     * Adds an event listener.
     * 
     * @param listener The event listener
     */
    public void addEventListener(WorkflowEventListener listener) {
        eventPublisher.addEventListener(listener);
    }
    
    /**
     * Removes an event listener.
     * 
     * @param listener The event listener
     */
    public void removeEventListener(WorkflowEventListener listener) {
        eventPublisher.removeEventListener(listener);
    }
    
    /**
     * Executes a workflow synchronously.
     * 
     * @param workflow The workflow to execute
     * @param context The workflow context
     * @return The workflow instance
     * @throws WorkflowException if execution fails
     */
    public WorkflowInstance execute(Workflow workflow, WorkflowContext context) throws WorkflowException {
        // Create workflow instance
        WorkflowInstance instance = new WorkflowInstance(
            IdGenerator.generateId("wf-instance"),
            workflow.getId()
        );
        instance.setContext(context);
        
        logger.info("Starting workflow execution: {} ({})", workflow.getName(), instance.getId());
        
        try {
            // Start workflow
            instance.start();
            eventPublisher.publishWorkflowStarted(
                new WorkflowStartedEvent(instance.getId(), workflow.getId(), context)
            );
            
            // Execute from start node
            Node startNode = workflow.getStartNode();
            if (startNode == null) {
                throw new InvalidWorkflowException("Workflow has no start node: " + workflow.getId());
            }
            
            executeNode(workflow, instance, startNode);
            
            // Mark as completed if not already in a terminal state
            if (!instance.getState().isTerminal()) {
                instance.complete();
                Long duration = instance.getDurationMs();
                eventPublisher.publishWorkflowCompleted(
                    new WorkflowCompletedEvent(instance.getId(), workflow.getId(), context, 
                                              duration != null ? duration : 0)
                );
            }
            
            logger.info("Workflow execution completed: {} ({})", workflow.getName(), instance.getId());
            return instance;
            
        } catch (Exception e) {
            logger.error("Workflow execution failed: {} ({})", workflow.getName(), instance.getId(), e);
            instance.fail(e.getMessage());
            eventPublisher.publishWorkflowFailed(
                new WorkflowFailedEvent(instance.getId(), workflow.getId(), context, e.getMessage(), e)
            );
            throw new WorkflowExecutionException("Workflow execution failed", e, 
                                                instance.getCurrentNodeId(), instance.getId());
        }
    }
    
    /**
     * Executes a workflow asynchronously.
     * 
     * @param workflow The workflow to execute
     * @param context The workflow context
     * @return The workflow instance (in RUNNING state)
     */
    public WorkflowInstance executeAsync(Workflow workflow, WorkflowContext context) {
        WorkflowInstance instance = new WorkflowInstance(
            IdGenerator.generateId("wf-instance"),
            workflow.getId()
        );
        instance.setContext(context);
        instance.start();
        
        asyncExecutor.submit(() -> {
            try {
                execute(workflow, context);
            } catch (Exception e) {
                logger.error("Async workflow execution failed", e);
            }
        });
        
        return instance;
    }
    
    /**
     * Resumes a waiting workflow instance.
     *
     * @param instance The workflow instance to resume
     * @param action The action that triggered the resume
     * @throws WorkflowException if resume fails
     */
    public void resume(WorkflowInstance instance, String action) throws WorkflowException {
        if (instance.getState() != WorkflowState.WAITING) {
            throw new WorkflowException("Cannot resume workflow that is not in WAITING state");
        }

        instance.resume();
        logger.info("Resumed workflow instance: {} with action: {}", instance.getId(), action);
    }

    /**
     * Executes a single node and its downstream nodes.
     *
     * @param workflow The workflow
     * @param instance The workflow instance
     * @param node The node to execute
     * @throws WorkflowException if execution fails
     */
    private void executeNode(Workflow workflow, WorkflowInstance instance, Node node)
            throws WorkflowException {

        if (node == null) {
            return;
        }

        instance.setCurrentNodeId(node.getId());
        logger.debug("Executing node: {} ({})", node.getId(), node.getType());

        ExecutionHistory history = new ExecutionHistory(node.getId(), node.getType().name());
        LocalDateTime startTime = LocalDateTime.now();

        try {
            ExecutionResult result = null;

            // Execute based on node type
            switch (node.getType()) {
                case START:
                    result = ExecutionResult.success("Workflow started");
                    break;

                case END:
                    result = ExecutionResult.success("Workflow ended");
                    instance.complete();
                    return;

                case TASK:
                    result = executeTaskNode((TaskNode) node, instance.getContext());
                    break;

                case DECISION:
                    result = executeDecisionNode((DecisionNode) node, instance.getContext());
                    break;

                case WAIT:
                    result = executeWaitNode((WaitNode) node, instance);
                    return; // Don't continue execution

                case PARALLEL:
                    result = executeParallelNode((ParallelNode) node, workflow, instance);
                    break;

                default:
                    result = ExecutionResult.failure("Unsupported node type: " + node.getType());
            }

            // Record execution
            long duration = java.time.Duration.between(startTime, LocalDateTime.now()).toMillis();

            if (result.isSuccess()) {
                history.markSuccess(result.getMessage());
                eventPublisher.publishNodeExecuted(
                    new NodeExecutedEvent(instance.getId(), workflow.getId(),
                                        instance.getContext(), node.getId(),
                                        node.getType().name(), result, duration)
                );
            } else {
                history.markFailure(result.getMessage());
                eventPublisher.publishNodeFailed(
                    new NodeFailedEvent(instance.getId(), workflow.getId(),
                                      instance.getContext(), node.getId(),
                                      node.getType().name(), result.getMessage(), null)
                );
                throw new NodeExecutionException(result.getMessage(), node.getId(), node.getType().name());
            }

            instance.addExecutionHistory(history);

            // Determine next node
            String nextNodeId = result.getNextNodeId();
            if (nextNodeId == null && !node.getTransitions().isEmpty()) {
                nextNodeId = node.getTransitions().get(0);
            }

            // Execute next node
            if (nextNodeId != null) {
                Node nextNode = workflow.getNode(nextNodeId);
                if (nextNode != null) {
                    executeNode(workflow, instance, nextNode);
                }
            }

        } catch (Exception e) {
            history.markFailure(e.getMessage());
            instance.addExecutionHistory(history);
            throw e;
        }
    }

    /**
     * Executes a task node.
     */
    private ExecutionResult executeTaskNode(TaskNode node, WorkflowContext context)
            throws NodeExecutionException {

        NodeExecutor executor = executorFactory.getExecutor(node.getExecutorClassName());
        if (executor == null) {
            throw new NodeExecutionException(
                "No executor found for: " + node.getExecutorClassName(),
                node.getId(),
                node.getType().name()
            );
        }

        // Add node config to context temporarily
        context.setMetadata("_node.config", node.getConfigMap());

        ExecutionResult result = executor.execute(context);

        // Clean up metadata
        context.setMetadata("_node.config", null);

        return result;
    }

    /**
     * Executes a decision node.
     */
    private ExecutionResult executeDecisionNode(DecisionNode node, WorkflowContext context) {
        boolean conditionResult = expressionEvaluator.evaluateBoolean(node.getCondition(), context);

        String nextNode = conditionResult ? node.getTrueTransition() : node.getFalseTransition();

        return ExecutionResult.success("Decision evaluated to: " + conditionResult)
                .withNextNode(nextNode);
    }

    /**
     * Executes a wait node.
     */
    private ExecutionResult executeWaitNode(WaitNode node, WorkflowInstance instance) {
        instance.waitFor(node.getAction());
        logger.info("Workflow waiting for: {}", node.getAction());
        return ExecutionResult.success("Waiting for: " + node.getAction());
    }

    /**
     * Executes a parallel node.
     */
    private ExecutionResult executeParallelNode(ParallelNode node, Workflow workflow,
                                               WorkflowInstance instance) throws WorkflowException {
        // For simplicity, execute branches sequentially for now
        // A production implementation would use true parallel execution
        for (List<String> branch : node.getBranches()) {
            for (String nodeId : branch) {
                Node branchNode = workflow.getNode(nodeId);
                if (branchNode != null) {
                    executeNode(workflow, instance, branchNode);
                }
            }
        }

        return ExecutionResult.success("Parallel branches completed");
    }

    /**
     * Shuts down the async executor.
     */
    public void shutdown() {
        asyncExecutor.shutdown();
        try {
            if (!asyncExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                asyncExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            asyncExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

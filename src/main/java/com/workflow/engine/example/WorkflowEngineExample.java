package com.workflow.engine.example;

import com.workflow.engine.builder.WorkflowBuilder;
import com.workflow.engine.core.*;
import com.workflow.engine.event.*;
import com.workflow.engine.exception.WorkflowException;
import com.workflow.engine.loader.JsonWorkflowLoader;
import com.workflow.engine.loader.WorkflowLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Comprehensive example demonstrating the Dynamic Workflow Engine.
 */
public class WorkflowEngineExample {
    
    private static final Logger logger = LoggerFactory.getLogger(WorkflowEngineExample.class);
    
    public static void main(String[] args) {
        logger.info("=== Dynamic Workflow Engine Example ===\n");
        
        // Example 1: Programmatic Workflow Creation
        example1ProgrammaticWorkflow();
        
        // Example 2: JSON-based Workflow
        // example2JsonWorkflow();
        
        // Example 3: Decision Workflow
        example3DecisionWorkflow();
    }
    
    /**
     * Example 1: Creating a workflow programmatically using the builder API.
     */
    private static void example1ProgrammaticWorkflow() {
        logger.info("\n--- Example 1: Programmatic Workflow ---");
        
        // Create workflow engine
        WorkflowEngine engine = new WorkflowEngine();
        
        // Register executors
        engine.registerExecutor(SimpleTaskExecutor.class.getName(), new SimpleTaskExecutor());
        engine.registerExecutor(ValidationTaskExecutor.class.getName(), new ValidationTaskExecutor());
        engine.registerExecutor(ProcessingTaskExecutor.class.getName(), new ProcessingTaskExecutor());
        
        // Add event listener for monitoring
        engine.addEventListener(new WorkflowEventListener() {
            @Override
            public void onWorkflowStarted(WorkflowStartedEvent event) {
                logger.info("✓ Workflow started: {}", event.getWorkflowInstanceId());
            }
            
            @Override
            public void onNodeExecuted(NodeExecutedEvent event) {
                logger.info("✓ Node executed: {} - {}", event.getNodeId(), event.getResult().getMessage());
            }
            
            @Override
            public void onWorkflowCompleted(WorkflowCompletedEvent event) {
                logger.info("✓ Workflow completed in {} ms", event.getDurationMs());
            }
        });
        
        // Build workflow using fluent API
        Workflow workflow = new WorkflowBuilder("simple-workflow", "Simple Processing Workflow")
            .version("1.0")
            .description("A simple workflow demonstrating basic task execution")
            .startNode("start")
            .transitionTo("validate")

            .addTaskNode("validate")
            .nodeName("Validate Input")
            .executor(ValidationTaskExecutor.class.getName())
            .transitionTo("process")

            .addTaskNode("process")
            .nodeName("Process Data")
            .executor(ProcessingTaskExecutor.class.getName())
            .transitionTo("end")

            .endNode("end")
            .build();
        
        // Create execution context
        WorkflowContext context = new WorkflowContext();
        context.setVariable("input", "Hello, Workflow Engine!");
        context.setVariable("userId", "user123");
        
        try {
            // Execute workflow
            WorkflowInstance instance = engine.execute(workflow, context);
            
            // Print results
            logger.info("\nWorkflow Result:");
            logger.info("  Status: {}", instance.getState());
            logger.info("  Duration: {} ms", instance.getDurationMs());
            logger.info("  Output: {}", context.getVariable("processedOutput"));
            
        } catch (WorkflowException e) {
            logger.error("Workflow execution failed", e);
        }
    }
    
    /**
     * Example 2: Loading a workflow from a JSON file.
     */
    private static void example2JsonWorkflow() {
        logger.info("\n--- Example 2: JSON-based Workflow ---");
        
        WorkflowEngine engine = new WorkflowEngine();
        engine.registerExecutor(SimpleTaskExecutor.class.getName(), new SimpleTaskExecutor());
        
        WorkflowLoader loader = new JsonWorkflowLoader();
        
        try {
            // Load workflow from JSON
            File workflowFile = new File("src/main/resources/workflows/example-workflow.json");
            Workflow workflow = loader.load(workflowFile);
            
            logger.info("Loaded workflow: {} ({})", workflow.getName(), workflow.getId());
            
            // Execute
            WorkflowContext context = new WorkflowContext();
            context.setVariable("input", "JSON-based workflow");
            
            WorkflowInstance instance = engine.execute(workflow, context);
            logger.info("Workflow completed with status: {}", instance.getState());
            
        } catch (Exception e) {
            logger.error("Failed to execute JSON workflow", e);
        }
    }
    
    /**
     * Example 3: Workflow with decision nodes.
     */
    private static void example3DecisionWorkflow() {
        logger.info("\n--- Example 3: Decision Workflow ---");
        
        WorkflowEngine engine = new WorkflowEngine();
        engine.registerExecutor(ValidationTaskExecutor.class.getName(), new ValidationTaskExecutor());
        engine.registerExecutor(ProcessingTaskExecutor.class.getName(), new ProcessingTaskExecutor());
        
        // Build workflow with decision
        Workflow workflow = new WorkflowBuilder("decision-workflow", "Approval Workflow")
            .startNode("start")
            .transitionTo("validate")

            .addTaskNode("validate")
            .executor(ValidationTaskExecutor.class.getName())
            .transitionTo("check-valid")

            .addDecisionNode("check-valid")
            .condition("${isValid == true}")
            .whenTrue("process")
            .whenFalse("reject")

            .addTaskNode("process")
            .nodeName("Process Approved Data")
            .executor(ProcessingTaskExecutor.class.getName())
            .transitionTo("end")

            .addTaskNode("reject")
            .nodeName("Handle Rejection")
            .executor(SimpleTaskExecutor.class.getName())
            .transitionTo("end")

            .endNode("end")
            .build();
        
        // Test with valid input
        WorkflowContext context = new WorkflowContext();
        context.setVariable("input", "Valid data");
        context.setVariable("userId", "user456");
        
        try {
            logger.info("Testing with VALID input:");
            WorkflowInstance instance = engine.execute(workflow, context);
            logger.info("  Result: {}, isValid: {}", instance.getState(), context.getVariable("isValid"));
            
            // Test with invalid input
            WorkflowContext context2 = new WorkflowContext();
            context2.setVariable("input", ""); // Empty = invalid
            context2.setVariable("userId", "user789");
            
            logger.info("\nTesting with INVALID input:");
            WorkflowInstance instance2 = engine.execute(workflow, context2);
            logger.info("  Result: {}, isValid: {}", instance2.getState(), context2.getVariable("isValid"));
            
        } catch (WorkflowException e) {
            logger.error("Workflow execution failed", e);
        }
    }
}

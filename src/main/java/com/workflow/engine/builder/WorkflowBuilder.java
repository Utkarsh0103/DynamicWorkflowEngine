package com.workflow.engine.builder;

import com.workflow.engine.core.Workflow;
import com.workflow.engine.executor.NodeExecutor;
import com.workflow.engine.node.*;
import com.workflow.engine.util.IdGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fluent builder for creating workflows programmatically.
 */
public class WorkflowBuilder {
    
    private final Workflow workflow;
    private final Map<String, NodeBuilder> nodeBuilders;
    private String currentNodeId;
    
    public WorkflowBuilder(String workflowId) {
        this.workflow = new Workflow(workflowId, workflowId);
        this.nodeBuilders = new HashMap<>();
    }
    
    public WorkflowBuilder(String workflowId, String name) {
        this.workflow = new Workflow(workflowId, name);
        this.nodeBuilders = new HashMap<>();
    }
    
    /**
     * Sets the workflow name.
     */
    public WorkflowBuilder name(String name) {
        workflow.setName(name);
        return this;
    }
    
    /**
     * Sets the workflow version.
     */
    public WorkflowBuilder version(String version) {
        workflow.setVersion(version);
        return this;
    }
    
    /**
     * Sets the workflow description.
     */
    public WorkflowBuilder description(String description) {
        workflow.setDescription(description);
        return this;
    }
    
    /**
     * Adds a start node.
     */
    public WorkflowBuilder startNode(String nodeId) {
        StartNode node = new StartNode(nodeId);
        workflow.addNode(node);
        workflow.setStartNodeId(nodeId);
        currentNodeId = nodeId;
        nodeBuilders.put(nodeId, new NodeBuilder(node));
        return this;
    }
    
    /**
     * Adds an end node.
     */
    public WorkflowBuilder endNode(String nodeId) {
        EndNode node = new EndNode(nodeId);
        workflow.addNode(node);
        workflow.addEndNodeId(nodeId);
        currentNodeId = nodeId;
        nodeBuilders.put(nodeId, new NodeBuilder(node));
        return this;
    }
    
    /**
     * Adds a task node and sets it as current.
     */
    public WorkflowBuilder addTaskNode(String nodeId) {
        TaskNode node = new TaskNode(nodeId);
        workflow.addNode(node);
        currentNodeId = nodeId;
        NodeBuilder builder = new NodeBuilder(node, this);
        nodeBuilders.put(nodeId, builder);
        return this;
    }

    /**
     * Adds a decision node and sets it as current.
     */
    public WorkflowBuilder addDecisionNode(String nodeId) {
        DecisionNode node = new DecisionNode(nodeId);
        workflow.addNode(node);
        currentNodeId = nodeId;
        NodeBuilder builder = new NodeBuilder(node, this);
        nodeBuilders.put(nodeId, builder);
        return this;
    }

    /**
     * Adds a wait node and sets it as current.
     */
    public WorkflowBuilder addWaitNode(String nodeId) {
        WaitNode node = new WaitNode(nodeId);
        workflow.addNode(node);
        currentNodeId = nodeId;
        NodeBuilder builder = new NodeBuilder(node, this);
        nodeBuilders.put(nodeId, builder);
        return this;
    }

    /**
     * Adds a parallel node and sets it as current.
     */
    public WorkflowBuilder addParallelNode(String nodeId) {
        ParallelNode node = new ParallelNode(nodeId);
        workflow.addNode(node);
        currentNodeId = nodeId;
        NodeBuilder builder = new NodeBuilder(node, this);
        nodeBuilders.put(nodeId, builder);
        return this;
    }

    /**
     * Sets the name for the current node.
     */
    public WorkflowBuilder nodeName(String name) {
        if (currentNodeId != null) {
            Node node = workflow.getNode(currentNodeId);
            if (node != null) {
                node.setName(name);
            }
        }
        return this;
    }

    /**
     * Sets the executor for the current task node.
     */
    public WorkflowBuilder executor(String executorClassName) {
        if (currentNodeId != null) {
            Node node = workflow.getNode(currentNodeId);
            if (node instanceof TaskNode) {
                ((TaskNode) node).setExecutorClassName(executorClassName);
            }
        }
        return this;
    }

    /**
     * Sets the executor for the current task node.
     */
    public WorkflowBuilder executor(NodeExecutor executor) {
        return executor(executor.getClass().getName());
    }

    /**
     * Sets the condition for the current decision node.
     */
    public WorkflowBuilder condition(String condition) {
        if (currentNodeId != null) {
            Node node = workflow.getNode(currentNodeId);
            if (node instanceof DecisionNode) {
                ((DecisionNode) node).setCondition(condition);
            }
        }
        return this;
    }

    /**
     * Sets the true transition for the current decision node.
     */
    public WorkflowBuilder whenTrue(String targetNodeId) {
        if (currentNodeId != null) {
            Node node = workflow.getNode(currentNodeId);
            if (node instanceof DecisionNode) {
                ((DecisionNode) node).setTrueTransition(targetNodeId);
            }
        }
        return this;
    }

    /**
     * Sets the false transition for the current decision node.
     */
    public WorkflowBuilder whenFalse(String targetNodeId) {
        if (currentNodeId != null) {
            Node node = workflow.getNode(currentNodeId);
            if (node instanceof DecisionNode) {
                ((DecisionNode) node).setFalseTransition(targetNodeId);
            }
        }
        return this;
    }

    /**
     * Sets config for the current node.
     */
    public WorkflowBuilder config(String key, Object value) {
        if (currentNodeId != null) {
            Node node = workflow.getNode(currentNodeId);
            if (node != null) {
                node.setConfig(key, value);
            }
        }
        return this;
    }
    
    /**
     * Connects the current node to a target node.
     */
    public WorkflowBuilder transitionTo(String targetNodeId) {
        if (currentNodeId != null) {
            Node currentNode = workflow.getNode(currentNodeId);
            if (currentNode != null) {
                currentNode.addTransition(targetNodeId);
            }
        }
        return this;
    }
    
    /**
     * Builds and returns the workflow.
     */
    public Workflow build() {
        return workflow;
    }
    
    /**
     * Gets the underlying workflow (for advanced use).
     */
    public Workflow getWorkflow() {
        return workflow;
    }
}

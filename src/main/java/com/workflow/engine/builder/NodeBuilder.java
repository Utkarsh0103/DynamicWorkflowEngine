package com.workflow.engine.builder;

import com.workflow.engine.executor.NodeExecutor;
import com.workflow.engine.node.*;

import java.util.List;

/**
 * Fluent builder for configuring nodes.
 */
public class NodeBuilder {
    
    private final Node node;
    private final WorkflowBuilder workflowBuilder;
    
    public NodeBuilder(Node node) {
        this.node = node;
        this.workflowBuilder = null;
    }
    
    public NodeBuilder(Node node, WorkflowBuilder workflowBuilder) {
        this.node = node;
        this.workflowBuilder = workflowBuilder;
    }
    
    /**
     * Sets the node name.
     */
    public NodeBuilder name(String name) {
        node.setName(name);
        return this;
    }
    
    /**
     * Sets the executor for a task node.
     */
    public NodeBuilder executor(NodeExecutor executor) {
        if (node instanceof TaskNode) {
            ((TaskNode) node).setExecutorClassName(executor.getClass().getName());
        }
        return this;
    }
    
    /**
     * Sets the executor class name for a task node.
     */
    public NodeBuilder executor(String executorClassName) {
        if (node instanceof TaskNode) {
            ((TaskNode) node).setExecutorClassName(executorClassName);
        }
        return this;
    }
    
    /**
     * Sets the condition for a decision node.
     */
    public NodeBuilder condition(String condition) {
        if (node instanceof DecisionNode) {
            ((DecisionNode) node).setCondition(condition);
        }
        return this;
    }
    
    /**
     * Sets the true transition for a decision node.
     */
    public NodeBuilder whenTrue(String targetNodeId) {
        if (node instanceof DecisionNode) {
            ((DecisionNode) node).setTrueTransition(targetNodeId);
        }
        return this;
    }
    
    /**
     * Sets the false transition for a decision node.
     */
    public NodeBuilder whenFalse(String targetNodeId) {
        if (node instanceof DecisionNode) {
            ((DecisionNode) node).setFalseTransition(targetNodeId);
        }
        return this;
    }
    
    /**
     * Sets what to wait for in a wait node.
     */
    public NodeBuilder waitFor(String waitFor) {
        if (node instanceof WaitNode) {
            ((WaitNode) node).setWaitFor(waitFor);
        }
        return this;
    }
    
    /**
     * Sets the action for a wait node.
     */
    public NodeBuilder action(String action) {
        if (node instanceof WaitNode) {
            ((WaitNode) node).setAction(action);
        }
        return this;
    }
    
    /**
     * Sets the timeout for a wait node.
     */
    public NodeBuilder timeout(long timeoutMs) {
        if (node instanceof WaitNode) {
            ((WaitNode) node).setTimeoutMs(timeoutMs);
        }
        return this;
    }
    
    /**
     * Adds a branch to a parallel node.
     */
    public NodeBuilder addBranch(List<String> nodeIds) {
        if (node instanceof ParallelNode) {
            ((ParallelNode) node).addBranch(nodeIds);
        }
        return this;
    }
    
    /**
     * Sets configuration for the node.
     */
    public NodeBuilder config(String key, Object value) {
        node.setConfig(key, value);
        return this;
    }
    
    /**
     * Adds a transition to another node.
     */
    public WorkflowBuilder transitionTo(String targetNodeId) {
        node.addTransition(targetNodeId);
        if (workflowBuilder != null) {
            return workflowBuilder.transitionTo(targetNodeId);
        }
        throw new IllegalStateException("Cannot call transitionTo without WorkflowBuilder context");
    }
    
    /**
     * Returns to the workflow builder.
     */
    public WorkflowBuilder and() {
        return workflowBuilder;
    }
    
    /**
     * Gets the built node.
     */
    public Node build() {
        return node;
    }
}

package com.workflow.engine.node;

/**
 * Represents a task node that executes business logic.
 */
public class TaskNode extends Node {
    
    private static final long serialVersionUID = 1L;
    
    private String executorClassName;
    
    public TaskNode() {
        super();
        this.type = NodeType.TASK;
    }
    
    public TaskNode(String id) {
        super(id, NodeType.TASK);
    }
    
    public TaskNode(String id, String executorClassName) {
        super(id, NodeType.TASK);
        this.executorClassName = executorClassName;
    }
    
    public String getExecutorClassName() {
        return executorClassName;
    }
    
    public void setExecutorClassName(String executorClassName) {
        this.executorClassName = executorClassName;
    }
    
    @Override
    public String toString() {
        return "TaskNode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", executor='" + executorClassName + '\'' +
                '}';
    }
}

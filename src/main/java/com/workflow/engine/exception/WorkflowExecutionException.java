package com.workflow.engine.exception;

/**
 * Exception thrown during workflow execution.
 */
public class WorkflowExecutionException extends WorkflowException {
    
    private static final long serialVersionUID = 1L;
    private final String nodeId;
    private final String workflowInstanceId;
    
    public WorkflowExecutionException(String message, String nodeId, String workflowInstanceId) {
        super(message);
        this.nodeId = nodeId;
        this.workflowInstanceId = workflowInstanceId;
    }
    
    public WorkflowExecutionException(String message, Throwable cause, String nodeId, String workflowInstanceId) {
        super(message, cause);
        this.nodeId = nodeId;
        this.workflowInstanceId = workflowInstanceId;
    }
    
    public String getNodeId() {
        return nodeId;
    }
    
    public String getWorkflowInstanceId() {
        return workflowInstanceId;
    }
    
    @Override
    public String toString() {
        return String.format("WorkflowExecutionException{workflowInstanceId='%s', nodeId='%s', message='%s'}", 
                workflowInstanceId, nodeId, getMessage());
    }
}

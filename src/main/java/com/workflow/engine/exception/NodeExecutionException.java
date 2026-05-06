package com.workflow.engine.exception;

/**
 * Exception thrown when a node execution fails.
 */
public class NodeExecutionException extends WorkflowException {
    
    private static final long serialVersionUID = 1L;
    private final String nodeId;
    private final String nodeType;
    
    public NodeExecutionException(String message, String nodeId, String nodeType) {
        super(message);
        this.nodeId = nodeId;
        this.nodeType = nodeType;
    }
    
    public NodeExecutionException(String message, Throwable cause, String nodeId, String nodeType) {
        super(message, cause);
        this.nodeId = nodeId;
        this.nodeType = nodeType;
    }
    
    public String getNodeId() {
        return nodeId;
    }
    
    public String getNodeType() {
        return nodeType;
    }
    
    @Override
    public String toString() {
        return String.format("NodeExecutionException{nodeId='%s', nodeType='%s', message='%s'}", 
                nodeId, nodeType, getMessage());
    }
}

package com.workflow.engine.event;

import com.workflow.engine.core.WorkflowContext;

/**
 * Event fired when a node execution fails.
 */
public class NodeFailedEvent extends WorkflowEvent {
    
    private final String nodeId;
    private final String nodeType;
    private final String error;
    private final Throwable cause;
    
    public NodeFailedEvent(String workflowInstanceId, String workflowId, WorkflowContext context,
                          String nodeId, String nodeType, String error, Throwable cause) {
        super(workflowInstanceId, workflowId, context);
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.error = error;
        this.cause = cause;
    }
    
    public String getNodeId() {
        return nodeId;
    }
    
    public String getNodeType() {
        return nodeType;
    }
    
    public String getError() {
        return error;
    }
    
    public Throwable getCause() {
        return cause;
    }
}

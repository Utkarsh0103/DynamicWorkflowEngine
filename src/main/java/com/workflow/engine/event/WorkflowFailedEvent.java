package com.workflow.engine.event;

import com.workflow.engine.core.WorkflowContext;

/**
 * Event fired when a workflow fails.
 */
public class WorkflowFailedEvent extends WorkflowEvent {
    
    private final String error;
    private final Throwable cause;
    
    public WorkflowFailedEvent(String workflowInstanceId, String workflowId, 
                               WorkflowContext context, String error, Throwable cause) {
        super(workflowInstanceId, workflowId, context);
        this.error = error;
        this.cause = cause;
    }
    
    public String getError() {
        return error;
    }
    
    public Throwable getCause() {
        return cause;
    }
}

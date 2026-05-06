package com.workflow.engine.event;

import com.workflow.engine.core.WorkflowContext;

/**
 * Event fired when a workflow is cancelled.
 */
public class WorkflowCancelledEvent extends WorkflowEvent {
    
    private final String reason;
    
    public WorkflowCancelledEvent(String workflowInstanceId, String workflowId, 
                                  WorkflowContext context, String reason) {
        super(workflowInstanceId, workflowId, context);
        this.reason = reason;
    }
    
    public String getReason() {
        return reason;
    }
}

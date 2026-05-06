package com.workflow.engine.event;

import com.workflow.engine.core.WorkflowContext;

/**
 * Event fired when a workflow completes successfully.
 */
public class WorkflowCompletedEvent extends WorkflowEvent {
    
    private final long durationMs;
    
    public WorkflowCompletedEvent(String workflowInstanceId, String workflowId, 
                                  WorkflowContext context, long durationMs) {
        super(workflowInstanceId, workflowId, context);
        this.durationMs = durationMs;
    }
    
    public long getDurationMs() {
        return durationMs;
    }
    
    public long getDuration() {
        return durationMs;
    }
}

package com.workflow.engine.event;

import com.workflow.engine.core.WorkflowContext;

/**
 * Event fired when a workflow starts.
 */
public class WorkflowStartedEvent extends WorkflowEvent {
    
    public WorkflowStartedEvent(String workflowInstanceId, String workflowId, WorkflowContext context) {
        super(workflowInstanceId, workflowId, context);
    }
}

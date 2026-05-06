package com.workflow.engine.event;

import com.workflow.engine.core.WorkflowContext;
import java.time.LocalDateTime;

/**
 * Base class for all workflow events.
 */
public abstract class WorkflowEvent {
    
    private final String workflowInstanceId;
    private final String workflowId;
    private final LocalDateTime timestamp;
    private final WorkflowContext context;
    
    public WorkflowEvent(String workflowInstanceId, String workflowId, WorkflowContext context) {
        this.workflowInstanceId = workflowInstanceId;
        this.workflowId = workflowId;
        this.context = context;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getWorkflowInstanceId() {
        return workflowInstanceId;
    }
    
    public String getWorkflowId() {
        return workflowId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public WorkflowContext getContext() {
        return context;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "workflowInstanceId='" + workflowInstanceId + '\'' +
                ", workflowId='" + workflowId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

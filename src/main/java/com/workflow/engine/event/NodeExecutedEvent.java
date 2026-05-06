package com.workflow.engine.event;

import com.workflow.engine.core.WorkflowContext;
import com.workflow.engine.executor.ExecutionResult;

/**
 * Event fired when a node is executed.
 */
public class NodeExecutedEvent extends WorkflowEvent {
    
    private final String nodeId;
    private final String nodeType;
    private final ExecutionResult result;
    private final long durationMs;
    
    public NodeExecutedEvent(String workflowInstanceId, String workflowId, WorkflowContext context,
                            String nodeId, String nodeType, ExecutionResult result, long durationMs) {
        super(workflowInstanceId, workflowId, context);
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.result = result;
        this.durationMs = durationMs;
    }
    
    public String getNodeId() {
        return nodeId;
    }
    
    public String getNodeType() {
        return nodeType;
    }
    
    public ExecutionResult getResult() {
        return result;
    }
    
    public long getDurationMs() {
        return durationMs;
    }
}

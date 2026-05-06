package com.workflow.engine.executor;

import com.workflow.engine.core.WorkflowContext;

/**
 * Interface for executing workflow nodes.
 * Implementations define the business logic for specific task types.
 */
public interface NodeExecutor {
    
    /**
     * Executes the node logic.
     * 
     * @param context The workflow context containing runtime data
     * @return The execution result
     */
    ExecutionResult execute(WorkflowContext context);
}

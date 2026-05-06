package com.workflow.engine.core;

/**
 * Enumeration of possible workflow instance states.
 */
public enum WorkflowState {
    /**
     * Workflow has been created but not yet started.
     */
    CREATED,
    
    /**
     * Workflow is currently running.
     */
    RUNNING,
    
    /**
     * Workflow is waiting for an external event or user action.
     */
    WAITING,
    
    /**
     * Workflow is temporarily suspended.
     */
    SUSPENDED,
    
    /**
     * Workflow has completed successfully.
     */
    COMPLETED,
    
    /**
     * Workflow has failed with an error.
     */
    FAILED,
    
    /**
     * Workflow has been cancelled.
     */
    CANCELLED,
    
    /**
     * Workflow has timed out.
     */
    TIMEOUT;
    
    /**
     * Checks if the workflow is in a terminal state.
     * 
     * @return true if the state is terminal (completed, failed, cancelled, timeout)
     */
    public boolean isTerminal() {
        return this == COMPLETED || this == FAILED || this == CANCELLED || this == TIMEOUT;
    }
    
    /**
     * Checks if the workflow is in an active state.
     * 
     * @return true if the state is active (running or waiting)
     */
    public boolean isActive() {
        return this == RUNNING || this == WAITING;
    }
}

package com.workflow.engine.event;

/**
 * Interface for listening to workflow events.
 */
public interface WorkflowEventListener {
    
    /**
     * Called when a workflow starts.
     * 
     * @param event The workflow started event
     */
    default void onWorkflowStarted(WorkflowStartedEvent event) {
        // Default implementation does nothing
    }
    
    /**
     * Called when a workflow completes successfully.
     * 
     * @param event The workflow completed event
     */
    default void onWorkflowCompleted(WorkflowCompletedEvent event) {
        // Default implementation does nothing
    }
    
    /**
     * Called when a workflow fails.
     * 
     * @param event The workflow failed event
     */
    default void onWorkflowFailed(WorkflowFailedEvent event) {
        // Default implementation does nothing
    }
    
    /**
     * Called when a workflow is cancelled.
     * 
     * @param event The workflow cancelled event
     */
    default void onWorkflowCancelled(WorkflowCancelledEvent event) {
        // Default implementation does nothing
    }
    
    /**
     * Called when a node is executed.
     * 
     * @param event The node executed event
     */
    default void onNodeExecuted(NodeExecutedEvent event) {
        // Default implementation does nothing
    }
    
    /**
     * Called when a node execution fails.
     * 
     * @param event The node failed event
     */
    default void onNodeFailed(NodeFailedEvent event) {
        // Default implementation does nothing
    }
}

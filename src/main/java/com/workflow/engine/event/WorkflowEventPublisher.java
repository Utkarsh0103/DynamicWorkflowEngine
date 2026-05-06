package com.workflow.engine.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Publishes workflow events to registered listeners.
 */
public class WorkflowEventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(WorkflowEventPublisher.class);
    
    private final List<WorkflowEventListener> listeners;
    
    public WorkflowEventPublisher() {
        this.listeners = new CopyOnWriteArrayList<>();
    }
    
    /**
     * Registers an event listener.
     * 
     * @param listener The listener to register
     */
    public void addEventListener(WorkflowEventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
            logger.debug("Registered event listener: {}", listener.getClass().getName());
        }
    }
    
    /**
     * Unregisters an event listener.
     * 
     * @param listener The listener to unregister
     */
    public void removeEventListener(WorkflowEventListener listener) {
        listeners.remove(listener);
        logger.debug("Unregistered event listener: {}", listener.getClass().getName());
    }
    
    /**
     * Publishes a workflow started event.
     * 
     * @param event The event to publish
     */
    public void publishWorkflowStarted(WorkflowStartedEvent event) {
        logger.debug("Publishing workflow started event: {}", event.getWorkflowInstanceId());
        for (WorkflowEventListener listener : listeners) {
            try {
                listener.onWorkflowStarted(event);
            } catch (Exception e) {
                logger.error("Error in event listener", e);
            }
        }
    }
    
    /**
     * Publishes a workflow completed event.
     * 
     * @param event The event to publish
     */
    public void publishWorkflowCompleted(WorkflowCompletedEvent event) {
        logger.debug("Publishing workflow completed event: {}", event.getWorkflowInstanceId());
        for (WorkflowEventListener listener : listeners) {
            try {
                listener.onWorkflowCompleted(event);
            } catch (Exception e) {
                logger.error("Error in event listener", e);
            }
        }
    }
    
    /**
     * Publishes a workflow failed event.
     * 
     * @param event The event to publish
     */
    public void publishWorkflowFailed(WorkflowFailedEvent event) {
        logger.debug("Publishing workflow failed event: {}", event.getWorkflowInstanceId());
        for (WorkflowEventListener listener : listeners) {
            try {
                listener.onWorkflowFailed(event);
            } catch (Exception e) {
                logger.error("Error in event listener", e);
            }
        }
    }
    
    /**
     * Publishes a workflow cancelled event.
     * 
     * @param event The event to publish
     */
    public void publishWorkflowCancelled(WorkflowCancelledEvent event) {
        logger.debug("Publishing workflow cancelled event: {}", event.getWorkflowInstanceId());
        for (WorkflowEventListener listener : listeners) {
            try {
                listener.onWorkflowCancelled(event);
            } catch (Exception e) {
                logger.error("Error in event listener", e);
            }
        }
    }
    
    /**
     * Publishes a node executed event.
     * 
     * @param event The event to publish
     */
    public void publishNodeExecuted(NodeExecutedEvent event) {
        logger.debug("Publishing node executed event: {} in workflow {}", 
                    event.getNodeId(), event.getWorkflowInstanceId());
        for (WorkflowEventListener listener : listeners) {
            try {
                listener.onNodeExecuted(event);
            } catch (Exception e) {
                logger.error("Error in event listener", e);
            }
        }
    }
    
    /**
     * Publishes a node failed event.
     * 
     * @param event The event to publish
     */
    public void publishNodeFailed(NodeFailedEvent event) {
        logger.debug("Publishing node failed event: {} in workflow {}", 
                    event.getNodeId(), event.getWorkflowInstanceId());
        for (WorkflowEventListener listener : listeners) {
            try {
                listener.onNodeFailed(event);
            } catch (Exception e) {
                logger.error("Error in event listener", e);
            }
        }
    }
    
    /**
     * Gets the number of registered listeners.
     * 
     * @return The number of listeners
     */
    public int getListenerCount() {
        return listeners.size();
    }
    
    /**
     * Clears all registered listeners.
     */
    public void clearListeners() {
        listeners.clear();
    }
}

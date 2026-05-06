package com.workflow.engine.persistence;

import com.workflow.engine.core.WorkflowInstance;
import com.workflow.engine.core.WorkflowState;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for storing and retrieving workflow instances.
 */
public interface WorkflowInstanceRepository {
    
    /**
     * Saves a workflow instance.
     * 
     * @param instance The workflow instance to save
     */
    void save(WorkflowInstance instance);
    
    /**
     * Finds a workflow instance by its ID.
     * 
     * @param instanceId The instance ID
     * @return Optional containing the instance if found
     */
    Optional<WorkflowInstance> findById(String instanceId);
    
    /**
     * Finds all instances of a workflow.
     * 
     * @param workflowId The workflow ID
     * @return List of workflow instances
     */
    List<WorkflowInstance> findByWorkflowId(String workflowId);
    
    /**
     * Finds instances by state.
     * 
     * @param state The workflow state
     * @return List of workflow instances in the given state
     */
    List<WorkflowInstance> findByState(WorkflowState state);
    
    /**
     * Finds instances by a context variable value.
     * 
     * @param variableName The variable name
     * @param variableValue The variable value
     * @return List of matching workflow instances
     */
    List<WorkflowInstance> findByContextVariable(String variableName, Object variableValue);
    
    /**
     * Updates a workflow instance.
     * 
     * @param instance The workflow instance to update
     */
    void update(WorkflowInstance instance);
    
    /**
     * Deletes a workflow instance.
     * 
     * @param instanceId The instance ID
     */
    void deleteById(String instanceId);
    
    /**
     * Finds all workflow instances.
     * 
     * @return List of all instances
     */
    List<WorkflowInstance> findAll();
}

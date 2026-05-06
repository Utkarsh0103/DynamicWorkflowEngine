package com.workflow.engine.persistence;

import com.workflow.engine.core.Workflow;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for storing and retrieving workflows.
 */
public interface WorkflowRepository {
    
    /**
     * Saves a workflow.
     * 
     * @param workflow The workflow to save
     */
    void save(Workflow workflow);
    
    /**
     * Finds a workflow by its ID.
     * 
     * @param workflowId The workflow ID
     * @return Optional containing the workflow if found
     */
    Optional<Workflow> findById(String workflowId);
    
    /**
     * Finds all workflows.
     * 
     * @return List of all workflows
     */
    List<Workflow> findAll();
    
    /**
     * Deletes a workflow by its ID.
     * 
     * @param workflowId The workflow ID
     */
    void deleteById(String workflowId);
    
    /**
     * Checks if a workflow exists.
     * 
     * @param workflowId The workflow ID
     * @return true if the workflow exists
     */
    boolean exists(String workflowId);
}

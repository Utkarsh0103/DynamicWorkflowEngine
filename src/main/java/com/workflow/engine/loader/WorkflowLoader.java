package com.workflow.engine.loader;

import com.workflow.engine.core.Workflow;
import com.workflow.engine.exception.InvalidWorkflowException;
import java.io.File;
import java.io.InputStream;

/**
 * Interface for loading workflows from various sources.
 */
public interface WorkflowLoader {
    
    /**
     * Loads a workflow from a file.
     * 
     * @param file The workflow file
     * @return The loaded workflow
     * @throws InvalidWorkflowException if the workflow is invalid
     */
    Workflow load(File file) throws InvalidWorkflowException;
    
    /**
     * Loads a workflow from an input stream.
     * 
     * @param inputStream The input stream
     * @return The loaded workflow
     * @throws InvalidWorkflowException if the workflow is invalid
     */
    Workflow load(InputStream inputStream) throws InvalidWorkflowException;
    
    /**
     * Loads a workflow from a string.
     * 
     * @param content The workflow content
     * @return The loaded workflow
     * @throws InvalidWorkflowException if the workflow is invalid
     */
    Workflow loadFromString(String content) throws InvalidWorkflowException;
}

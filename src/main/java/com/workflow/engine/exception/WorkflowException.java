package com.workflow.engine.exception;

/**
 * Base exception class for all workflow-related exceptions.
 */
public class WorkflowException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public WorkflowException(String message) {
        super(message);
    }
    
    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public WorkflowException(Throwable cause) {
        super(cause);
    }
}

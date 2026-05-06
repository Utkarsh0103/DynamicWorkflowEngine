package com.workflow.engine.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception thrown when a workflow definition is invalid.
 */
public class InvalidWorkflowException extends WorkflowException {
    
    private static final long serialVersionUID = 1L;
    private final List<String> validationErrors;
    
    public InvalidWorkflowException(String message) {
        super(message);
        this.validationErrors = new ArrayList<>();
    }
    
    public InvalidWorkflowException(String message, List<String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors != null ? validationErrors : new ArrayList<>();
    }
    
    public InvalidWorkflowException(String message, Throwable cause) {
        super(message, cause);
        this.validationErrors = new ArrayList<>();
    }
    
    public List<String> getValidationErrors() {
        return new ArrayList<>(validationErrors);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InvalidWorkflowException: ");
        sb.append(getMessage());
        if (!validationErrors.isEmpty()) {
            sb.append("\nValidation Errors:");
            for (String error : validationErrors) {
                sb.append("\n  - ").append(error);
            }
        }
        return sb.toString();
    }
}

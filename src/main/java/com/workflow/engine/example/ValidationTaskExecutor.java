package com.workflow.engine.example;

import com.workflow.engine.core.WorkflowContext;
import com.workflow.engine.executor.ExecutionResult;
import com.workflow.engine.executor.NodeExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example validator task executor.
 */
public class ValidationTaskExecutor implements NodeExecutor {
    
    private static final Logger logger = LoggerFactory.getLogger(ValidationTaskExecutor.class);
    
    @Override
    public ExecutionResult execute(WorkflowContext context) {
        logger.info("Executing ValidationTaskExecutor");
        
        String input = (String) context.getVariable("input");
        
        // Simple validation: check if input is not empty
        boolean isValid = input != null && !input.trim().isEmpty();
        
        context.setVariable("isValid", isValid);
        
        if (isValid) {
            logger.info("Validation passed for input: {}", input);
            return ExecutionResult.success("Validation successful");
        } else {
            logger.warn("Validation failed for input: {}", input);
            return ExecutionResult.success("Validation failed (but continuing workflow)");
        }
    }
}

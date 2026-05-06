package com.workflow.engine.example;

import com.workflow.engine.core.WorkflowContext;
import com.workflow.engine.executor.ExecutionResult;
import com.workflow.engine.executor.NodeExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example processing task executor.
 */
public class ProcessingTaskExecutor implements NodeExecutor {
    
    private static final Logger logger = LoggerFactory.getLogger(ProcessingTaskExecutor.class);
    
    @Override
    public ExecutionResult execute(WorkflowContext context) {
        logger.info("Executing ProcessingTaskExecutor");
        
        String input = (String) context.getVariable("input");
        String userId = (String) context.getVariable("userId");
        
        // Simulate some processing
        String processedOutput = String.format("Processed by user '%s': %s [COMPLETED]", 
                                              userId, input.toUpperCase());
        
        context.setVariable("processedOutput", processedOutput);
        context.setVariable("processingTimestamp", System.currentTimeMillis());
        
        logger.info("Processing completed: {}", processedOutput);
        
        return ExecutionResult.success("Processing completed successfully");
    }
}

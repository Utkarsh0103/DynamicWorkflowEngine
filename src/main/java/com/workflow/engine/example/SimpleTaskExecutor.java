package com.workflow.engine.example;

import com.workflow.engine.core.WorkflowContext;
import com.workflow.engine.executor.ExecutionResult;
import com.workflow.engine.executor.NodeExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example task executor that demonstrates basic task execution.
 */
public class SimpleTaskExecutor implements NodeExecutor {
    
    private static final Logger logger = LoggerFactory.getLogger(SimpleTaskExecutor.class);
    
    @Override
    public ExecutionResult execute(WorkflowContext context) {
        logger.info("Executing SimpleTaskExecutor");
        
        // Get input from context
        String input = (String) context.getVariable("input");
        logger.info("Input: {}", input);
        
        // Perform some work
        String output = "Processed: " + input;
        
        // Store output in context
        context.setVariable("output", output);
        
        return ExecutionResult.success("Task completed successfully");
    }
}

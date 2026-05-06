package com.workflow.engine.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for creating and managing node executors.
 */
public class ExecutorFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(ExecutorFactory.class);
    
    private final Map<String, NodeExecutor> executors;
    
    public ExecutorFactory() {
        this.executors = new ConcurrentHashMap<>();
    }
    
    /**
     * Registers an executor for a specific executor class name.
     * 
     * @param executorClassName The executor class name
     * @param executor The executor instance
     */
    public void registerExecutor(String executorClassName, NodeExecutor executor) {
        logger.debug("Registering executor: {}", executorClassName);
        executors.put(executorClassName, executor);
    }
    
    /**
     * Gets an executor by class name.
     * 
     * @param executorClassName The executor class name
     * @return The executor instance, or null if not found
     */
    public NodeExecutor getExecutor(String executorClassName) {
        NodeExecutor executor = executors.get(executorClassName);
        
        if (executor == null) {
            // Try to instantiate dynamically
            executor = createExecutorInstance(executorClassName);
            if (executor != null) {
                registerExecutor(executorClassName, executor);
            }
        }
        
        return executor;
    }
    
    /**
     * Checks if an executor is registered.
     * 
     * @param executorClassName The executor class name
     * @return true if the executor is registered
     */
    public boolean hasExecutor(String executorClassName) {
        return executors.containsKey(executorClassName);
    }
    
    /**
     * Clears all registered executors.
     */
    public void clear() {
        executors.clear();
    }
    
    /**
     * Attempts to create an executor instance using reflection.
     * 
     * @param executorClassName The executor class name
     * @return The executor instance, or null if creation fails
     */
    private NodeExecutor createExecutorInstance(String executorClassName) {
        try {
            Class<?> executorClass = Class.forName(executorClassName);
            if (NodeExecutor.class.isAssignableFrom(executorClass)) {
                return (NodeExecutor) executorClass.getDeclaredConstructor().newInstance();
            } else {
                logger.error("Class {} does not implement NodeExecutor", executorClassName);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Executor class not found: {}", executorClassName);
        } catch (Exception e) {
            logger.error("Failed to instantiate executor: {}", executorClassName, e);
        }
        return null;
    }
}

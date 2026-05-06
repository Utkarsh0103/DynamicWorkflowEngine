package com.workflow.engine.executor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the result of a node execution.
 */
public class ExecutionResult implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private boolean success;
    private String message;
    private Map<String, Object> data;
    private String nextNodeId; // Optional: explicit next node
    
    public ExecutionResult(boolean success) {
        this.success = success;
        this.data = new HashMap<>();
    }
    
    public ExecutionResult(boolean success, String message) {
        this(success);
        this.message = message;
    }
    
    /**
     * Creates a successful execution result.
     * 
     * @return ExecutionResult with success=true
     */
    public static ExecutionResult success() {
        return new ExecutionResult(true);
    }
    
    /**
     * Creates a successful execution result with a message.
     * 
     * @param message The success message
     * @return ExecutionResult with success=true
     */
    public static ExecutionResult success(String message) {
        return new ExecutionResult(true, message);
    }
    
    /**
     * Creates a failed execution result.
     * 
     * @param message The failure message
     * @return ExecutionResult with success=false
     */
    public static ExecutionResult failure(String message) {
        return new ExecutionResult(false, message);
    }
    
    /**
     * Creates a failed execution result with default message.
     * 
     * @return ExecutionResult with success=false
     */
    public static ExecutionResult failure() {
        return new ExecutionResult(false, "Execution failed");
    }
    
    /**
     * Adds data to the result.
     * 
     * @param key The data key
     * @param value The data value
     * @return This ExecutionResult for chaining
     */
    public ExecutionResult withData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
    
    /**
     * Sets the next node ID explicitly.
     * 
     * @param nextNodeId The next node ID
     * @return This ExecutionResult for chaining
     */
    public ExecutionResult withNextNode(String nextNodeId) {
        this.nextNodeId = nextNodeId;
        return this;
    }
    
    // Getters and setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    public String getNextNodeId() {
        return nextNodeId;
    }
    
    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }
    
    @Override
    public String toString() {
        return "ExecutionResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", nextNodeId='" + nextNodeId + '\'' +
                '}';
    }
}

package com.workflow.engine.core;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Context object that holds runtime data for a workflow instance.
 * Variables are stored in a thread-safe map and can be accessed by workflow nodes.
 */
public class WorkflowContext implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final Map<String, Object> variables;
    private final Map<String, Object> metadata;
    
    public WorkflowContext() {
        this.variables = new ConcurrentHashMap<>();
        this.metadata = new ConcurrentHashMap<>();
    }
    
    /**
     * Sets a variable in the context.
     * 
     * @param key The variable key
     * @param value The variable value
     */
    public void setVariable(String key, Object value) {
        variables.put(key, value);
    }
    
    /**
     * Gets a variable from the context.
     * 
     * @param key The variable key
     * @return The variable value, or null if not found
     */
    public Object getVariable(String key) {
        return variables.get(key);
    }
    
    /**
     * Gets a variable with a specific type.
     * 
     * @param key The variable key
     * @param type The expected type
     * @param <T> The type parameter
     * @return The typed variable value
     * @throws ClassCastException if the value cannot be cast to the specified type
     */
    @SuppressWarnings("unchecked")
    public <T> T getVariable(String key, Class<T> type) {
        Object value = variables.get(key);
        return value != null ? (T) value : null;
    }
    
    /**
     * Checks if a variable exists in the context.
     * 
     * @param key The variable key
     * @return true if the variable exists
     */
    public boolean hasVariable(String key) {
        return variables.containsKey(key);
    }
    
    /**
     * Removes a variable from the context.
     * 
     * @param key The variable key
     * @return The removed value, or null if not found
     */
    public Object removeVariable(String key) {
        return variables.remove(key);
    }
    
    /**
     * Gets all variables as an unmodifiable map.
     * 
     * @return All variables
     */
    public Map<String, Object> getAllVariables() {
        return Collections.unmodifiableMap(variables);
    }
    
    /**
     * Adds multiple variables to the context.
     * 
     * @param vars Map of variables to add
     */
    public void setVariables(Map<String, Object> vars) {
        if (vars != null) {
            variables.putAll(vars);
        }
    }
    
    /**
     * Clears all variables from the context.
     */
    public void clearVariables() {
        variables.clear();
    }
    
    /**
     * Sets metadata (non-workflow data).
     * 
     * @param key The metadata key
     * @param value The metadata value
     */
    public void setMetadata(String key, Object value) {
        metadata.put(key, value);
    }
    
    /**
     * Gets metadata.
     * 
     * @param key The metadata key
     * @return The metadata value
     */
    public Object getMetadata(String key) {
        return metadata.get(key);
    }
    
    /**
     * Gets all metadata.
     * 
     * @return All metadata
     */
    public Map<String, Object> getAllMetadata() {
        return Collections.unmodifiableMap(metadata);
    }
    
    /**
     * Adds an item to a list variable. Creates the list if it doesn't exist.
     * 
     * @param key The list variable key
     * @param item The item to add
     */
    @SuppressWarnings("unchecked")
    public void addToList(String key, Object item) {
        List<Object> list = (List<Object>) variables.get(key);
        if (list == null) {
            list = new ArrayList<>();
            variables.put(key, list);
        }
        list.add(item);
    }
    
    /**
     * Creates a copy of this context.
     * 
     * @return A new WorkflowContext with copied data
     */
    public WorkflowContext copy() {
        WorkflowContext newContext = new WorkflowContext();
        newContext.variables.putAll(this.variables);
        newContext.metadata.putAll(this.metadata);
        return newContext;
    }
    
    @Override
    public String toString() {
        return "WorkflowContext{" +
                "variables=" + variables.keySet() +
                ", metadata=" + metadata.keySet() +
                '}';
    }
}

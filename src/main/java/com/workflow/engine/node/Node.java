package com.workflow.engine.node;

import java.io.Serializable;
import java.util.*;

/**
 * Base class for all workflow nodes.
 */
public abstract class Node implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    protected String id;
    protected String name;
    protected NodeType type;
    protected List<String> transitions;
    protected Map<String, Object> config;
    
    public Node() {
        this.transitions = new ArrayList<>();
        this.config = new HashMap<>();
    }
    
    public Node(String id, NodeType type) {
        this();
        this.id = id;
        this.type = type;
    }
    
    /**
     * Adds a transition to another node.
     * 
     * @param targetNodeId The target node ID
     */
    public void addTransition(String targetNodeId) {
        if (targetNodeId != null && !transitions.contains(targetNodeId)) {
            transitions.add(targetNodeId);
        }
    }
    
    /**
     * Sets configuration for this node.
     * 
     * @param key The config key
     * @param value The config value
     */
    public void setConfig(String key, Object value) {
        config.put(key, value);
    }
    
    /**
     * Gets configuration value.
     * 
     * @param key The config key
     * @return The config value
     */
    public Object getConfig(String key) {
        return config.get(key);
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public NodeType getType() {
        return type;
    }
    
    public void setType(NodeType type) {
        this.type = type;
    }
    
    public List<String> getTransitions() {
        return Collections.unmodifiableList(transitions);
    }
    
    public void setTransitions(List<String> transitions) {
        this.transitions = transitions != null ? new ArrayList<>(transitions) : new ArrayList<>();
    }
    
    public Map<String, Object> getConfigMap() {
        return Collections.unmodifiableMap(config);
    }
    
    public void setConfigMap(Map<String, Object> config) {
        this.config = config != null ? new HashMap<>(config) : new HashMap<>();
    }
    
    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}

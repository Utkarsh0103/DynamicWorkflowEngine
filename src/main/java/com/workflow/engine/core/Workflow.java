package com.workflow.engine.core;

import com.workflow.engine.node.Node;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a workflow definition containing nodes and their relationships.
 */
public class Workflow implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String version;
    private String description;
    private Map<String, Node> nodes;
    private String startNodeId;
    private Set<String> endNodeIds;
    private Map<String, Object> metadata;
    
    public Workflow() {
        this.nodes = new LinkedHashMap<>();
        this.endNodeIds = new HashSet<>();
        this.metadata = new HashMap<>();
    }
    
    public Workflow(String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }
    
    /**
     * Adds a node to the workflow.
     * 
     * @param node The node to add
     */
    public void addNode(Node node) {
        if (node != null && node.getId() != null) {
            nodes.put(node.getId(), node);
        }
    }
    
    /**
     * Gets a node by its ID.
     * 
     * @param nodeId The node ID
     * @return The node, or null if not found
     */
    public Node getNode(String nodeId) {
        return nodes.get(nodeId);
    }
    
    /**
     * Gets all nodes in the workflow.
     * 
     * @return Unmodifiable map of all nodes
     */
    public Map<String, Node> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }
    
    /**
     * Gets the start node of the workflow.
     * 
     * @return The start node
     */
    public Node getStartNode() {
        return startNodeId != null ? nodes.get(startNodeId) : null;
    }
    
    /**
     * Checks if a node is an end node.
     * 
     * @param nodeId The node ID
     * @return true if the node is an end node
     */
    public boolean isEndNode(String nodeId) {
        return endNodeIds.contains(nodeId);
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
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getStartNodeId() {
        return startNodeId;
    }
    
    public void setStartNodeId(String startNodeId) {
        this.startNodeId = startNodeId;
    }
    
    public Set<String> getEndNodeIds() {
        return Collections.unmodifiableSet(endNodeIds);
    }
    
    public void setEndNodeIds(Set<String> endNodeIds) {
        this.endNodeIds = endNodeIds != null ? endNodeIds : new HashSet<>();
    }
    
    public void addEndNodeId(String nodeId) {
        this.endNodeIds.add(nodeId);
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public String toString() {
        return "Workflow{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", nodes=" + nodes.size() +
                '}';
    }
}

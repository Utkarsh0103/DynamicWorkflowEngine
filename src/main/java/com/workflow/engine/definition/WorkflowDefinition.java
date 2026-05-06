package com.workflow.engine.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON-serializable workflow definition.
 */
public class WorkflowDefinition {
    
    private String workflowId;
    private String name;
    private String version;
    private String description;
    private List<NodeDefinition> nodes;
    private Map<String, Object> metadata;
    
    public WorkflowDefinition() {
        this.nodes = new ArrayList<>();
        this.metadata = new HashMap<>();
    }
    
    // Getters and setters
    public String getWorkflowId() {
        return workflowId;
    }
    
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
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
    
    public List<NodeDefinition> getNodes() {
        return nodes;
    }
    
    public void setNodes(List<NodeDefinition> nodes) {
        this.nodes = nodes;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

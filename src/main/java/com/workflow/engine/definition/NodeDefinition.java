package com.workflow.engine.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON-serializable node definition.
 */
public class NodeDefinition {
    
    private String id;
    private String type;
    private String name;
    private String executor; // For TASK nodes
    private String condition; // For DECISION nodes
    private String waitFor; // For WAIT nodes
    private String action; // For WAIT nodes
    private Long timeoutMs; // For WAIT nodes
    private List<String> transitions;
    private Map<String, Object> transitionsMap; // For DECISION nodes (true/false)
    private List<BranchDefinition> branches; // For PARALLEL nodes
    private Integer expectedBranches; // For MERGE nodes
    private Map<String, Object> config;
    
    public NodeDefinition() {
        this.transitions = new ArrayList<>();
        this.config = new HashMap<>();
        this.branches = new ArrayList<>();
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getExecutor() {
        return executor;
    }
    
    public void setExecutor(String executor) {
        this.executor = executor;
    }
    
    public String getCondition() {
        return condition;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public String getWaitFor() {
        return waitFor;
    }
    
    public void setWaitFor(String waitFor) {
        this.waitFor = waitFor;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public Long getTimeoutMs() {
        return timeoutMs;
    }
    
    public void setTimeoutMs(Long timeoutMs) {
        this.timeoutMs = timeoutMs;
    }
    
    public List<String> getTransitions() {
        return transitions;
    }
    
    public void setTransitions(List<String> transitions) {
        this.transitions = transitions;
    }
    
    public Map<String, Object> getTransitionsMap() {
        return transitionsMap;
    }
    
    public void setTransitionsMap(Map<String, Object> transitionsMap) {
        this.transitionsMap = transitionsMap;
    }
    
    public List<BranchDefinition> getBranches() {
        return branches;
    }
    
    public void setBranches(List<BranchDefinition> branches) {
        this.branches = branches;
    }
    
    public Integer getExpectedBranches() {
        return expectedBranches;
    }
    
    public void setExpectedBranches(Integer expectedBranches) {
        this.expectedBranches = expectedBranches;
    }
    
    public Map<String, Object> getConfig() {
        return config;
    }
    
    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}

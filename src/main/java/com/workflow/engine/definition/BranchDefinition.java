package com.workflow.engine.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition of a parallel execution branch.
 */
public class BranchDefinition {
    
    private List<String> nodes;
    
    public BranchDefinition() {
        this.nodes = new ArrayList<>();
    }
    
    public List<String> getNodes() {
        return nodes;
    }
    
    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}

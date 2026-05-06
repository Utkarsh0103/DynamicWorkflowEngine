package com.workflow.engine.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a parallel node that executes multiple branches concurrently.
 */
public class ParallelNode extends Node {
    
    private static final long serialVersionUID = 1L;
    
    private List<List<String>> branches; // Each branch is a list of node IDs
    
    public ParallelNode() {
        super();
        this.type = NodeType.PARALLEL;
        this.branches = new ArrayList<>();
    }
    
    public ParallelNode(String id) {
        super(id, NodeType.PARALLEL);
        this.branches = new ArrayList<>();
    }
    
    /**
     * Adds a branch to the parallel execution.
     * 
     * @param nodeIds List of node IDs in the branch
     */
    public void addBranch(List<String> nodeIds) {
        if (nodeIds != null && !nodeIds.isEmpty()) {
            branches.add(new ArrayList<>(nodeIds));
        }
    }
    
    public List<List<String>> getBranches() {
        return branches;
    }
    
    public void setBranches(List<List<String>> branches) {
        this.branches = branches != null ? branches : new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "ParallelNode{" +
                "id='" + id + '\'' +
                ", branches=" + branches.size() +
                '}';
    }
}

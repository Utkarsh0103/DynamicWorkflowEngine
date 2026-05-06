package com.workflow.engine.node;

/**
 * Represents a merge node that joins multiple parallel execution paths.
 */
public class MergeNode extends Node {
    
    private static final long serialVersionUID = 1L;
    
    private int expectedBranches; // Number of branches to wait for
    
    public MergeNode() {
        super();
        this.type = NodeType.MERGE;
    }
    
    public MergeNode(String id) {
        super(id, NodeType.MERGE);
    }
    
    public MergeNode(String id, int expectedBranches) {
        super(id, NodeType.MERGE);
        this.expectedBranches = expectedBranches;
    }
    
    public int getExpectedBranches() {
        return expectedBranches;
    }
    
    public void setExpectedBranches(int expectedBranches) {
        this.expectedBranches = expectedBranches;
    }
    
    @Override
    public String toString() {
        return "MergeNode{" +
                "id='" + id + '\'' +
                ", expectedBranches=" + expectedBranches +
                '}';
    }
}

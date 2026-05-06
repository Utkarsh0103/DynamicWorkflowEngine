package com.workflow.engine.node;

/**
 * Represents the end node of a workflow.
 */
public class EndNode extends Node {
    
    private static final long serialVersionUID = 1L;
    
    public EndNode() {
        super();
        this.type = NodeType.END;
    }
    
    public EndNode(String id) {
        super(id, NodeType.END);
    }
}

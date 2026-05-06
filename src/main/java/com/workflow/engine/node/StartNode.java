package com.workflow.engine.node;

/**
 * Represents the start node of a workflow.
 */
public class StartNode extends Node {
    
    private static final long serialVersionUID = 1L;
    
    public StartNode() {
        super();
        this.type = NodeType.START;
    }
    
    public StartNode(String id) {
        super(id, NodeType.START);
    }
}

package com.workflow.engine.node;

/**
 * Represents a decision node that branches based on a condition.
 */
public class DecisionNode extends Node {
    
    private static final long serialVersionUID = 1L;
    
    private String condition;
    private String trueTransition;
    private String falseTransition;
    
    public DecisionNode() {
        super();
        this.type = NodeType.DECISION;
    }
    
    public DecisionNode(String id) {
        super(id, NodeType.DECISION);
    }
    
    public DecisionNode(String id, String condition) {
        super(id, NodeType.DECISION);
        this.condition = condition;
    }
    
    public String getCondition() {
        return condition;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public String getTrueTransition() {
        return trueTransition;
    }
    
    public void setTrueTransition(String trueTransition) {
        this.trueTransition = trueTransition;
    }
    
    public String getFalseTransition() {
        return falseTransition;
    }
    
    public void setFalseTransition(String falseTransition) {
        this.falseTransition = falseTransition;
    }
    
    @Override
    public String toString() {
        return "DecisionNode{" +
                "id='" + id + '\'' +
                ", condition='" + condition + '\'' +
                ", true->'" + trueTransition + '\'' +
                ", false->'" + falseTransition + '\'' +
                '}';
    }
}

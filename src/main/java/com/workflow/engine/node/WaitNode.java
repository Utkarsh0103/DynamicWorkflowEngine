package com.workflow.engine.node;

/**
 * Represents a wait node that pauses workflow execution until an external event.
 */
public class WaitNode extends Node {
    
    private static final long serialVersionUID = 1L;
    
    private String waitFor; // What to wait for (e.g., "USER_ACTION", "TIMER", "EVENT")
    private String action; // Specific action name
    private Long timeoutMs; // Optional timeout in milliseconds
    
    public WaitNode() {
        super();
        this.type = NodeType.WAIT;
    }
    
    public WaitNode(String id) {
        super(id, NodeType.WAIT);
    }
    
    public WaitNode(String id, String waitFor, String action) {
        super(id, NodeType.WAIT);
        this.waitFor = waitFor;
        this.action = action;
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
    
    @Override
    public String toString() {
        return "WaitNode{" +
                "id='" + id + '\'' +
                ", waitFor='" + waitFor + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}

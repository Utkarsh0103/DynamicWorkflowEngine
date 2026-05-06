package com.workflow.engine.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single entry in the workflow execution history.
 */
public class ExecutionHistory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String nodeId;
    private String nodeType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // SUCCESS, FAILURE, SKIPPED
    private String message;
    private Long durationMs;
    private Map<String, Object> data;
    
    public ExecutionHistory() {
        this.data = new HashMap<>();
    }
    
    public ExecutionHistory(String nodeId, String nodeType) {
        this();
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.startTime = LocalDateTime.now();
    }
    
    public void markSuccess(String message) {
        this.status = "SUCCESS";
        this.message = message;
        this.endTime = LocalDateTime.now();
        calculateDuration();
    }
    
    public void markFailure(String message) {
        this.status = "FAILURE";
        this.message = message;
        this.endTime = LocalDateTime.now();
        calculateDuration();
    }
    
    public void markSkipped(String message) {
        this.status = "SKIPPED";
        this.message = message;
        this.endTime = LocalDateTime.now();
        calculateDuration();
    }
    
    private void calculateDuration() {
        if (startTime != null && endTime != null) {
            this.durationMs = java.time.Duration.between(startTime, endTime).toMillis();
        }
    }
    
    public void addData(String key, Object value) {
        this.data.put(key, value);
    }
    
    // Getters and setters
    public String getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    
    public String getNodeType() {
        return nodeType;
    }
    
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Long getDurationMs() {
        return durationMs;
    }
    
    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "ExecutionHistory{" +
                "nodeId='" + nodeId + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", status='" + status + '\'' +
                ", durationMs=" + durationMs +
                ", message='" + message + '\'' +
                '}';
    }
}

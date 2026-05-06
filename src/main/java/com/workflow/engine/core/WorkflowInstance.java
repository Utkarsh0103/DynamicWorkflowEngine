package com.workflow.engine.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a running instance of a workflow.
 */
public class WorkflowInstance implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String workflowId;
    private WorkflowState state;
    private WorkflowContext context;
    private String currentNodeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMessage;
    private List<ExecutionHistory> executionHistory;
    private String waitingFor; // For WAIT nodes
    
    public WorkflowInstance() {
        this.state = WorkflowState.CREATED;
        this.context = new WorkflowContext();
        this.executionHistory = new ArrayList<>();
    }
    
    public WorkflowInstance(String id, String workflowId) {
        this();
        this.id = id;
        this.workflowId = workflowId;
    }
    
    /**
     * Starts the workflow instance.
     */
    public void start() {
        this.state = WorkflowState.RUNNING;
        this.startTime = LocalDateTime.now();
    }
    
    /**
     * Completes the workflow instance successfully.
     */
    public void complete() {
        this.state = WorkflowState.COMPLETED;
        this.endTime = LocalDateTime.now();
    }
    
    /**
     * Marks the workflow instance as failed.
     * 
     * @param errorMessage The error message
     */
    public void fail(String errorMessage) {
        this.state = WorkflowState.FAILED;
        this.errorMessage = errorMessage;
        this.endTime = LocalDateTime.now();
    }
    
    /**
     * Suspends the workflow instance.
     */
    public void suspend() {
        this.state = WorkflowState.SUSPENDED;
    }
    
    /**
     * Resumes a suspended workflow instance.
     */
    public void resume() {
        this.state = WorkflowState.RUNNING;
    }
    
    /**
     * Sets the workflow to waiting state.
     * 
     * @param waitingFor What the workflow is waiting for
     */
    public void waitFor(String waitingFor) {
        this.state = WorkflowState.WAITING;
        this.waitingFor = waitingFor;
    }
    
    /**
     * Cancels the workflow instance.
     */
    public void cancel() {
        this.state = WorkflowState.CANCELLED;
        this.endTime = LocalDateTime.now();
    }
    
    /**
     * Adds an execution history entry.
     * 
     * @param history The execution history entry
     */
    public void addExecutionHistory(ExecutionHistory history) {
        this.executionHistory.add(history);
    }
    
    /**
     * Gets the duration of the workflow execution in milliseconds.
     * 
     * @return Duration in milliseconds, or null if not completed
     */
    public Long getDurationMs() {
        if (startTime != null && endTime != null) {
            return java.time.Duration.between(startTime, endTime).toMillis();
        }
        return null;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getWorkflowId() {
        return workflowId;
    }
    
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
    
    public WorkflowState getState() {
        return state;
    }
    
    public void setState(WorkflowState state) {
        this.state = state;
    }
    
    public WorkflowContext getContext() {
        return context;
    }
    
    public void setContext(WorkflowContext context) {
        this.context = context;
    }
    
    public String getCurrentNodeId() {
        return currentNodeId;
    }
    
    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
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
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public List<ExecutionHistory> getExecutionHistory() {
        return Collections.unmodifiableList(executionHistory);
    }
    
    public void setExecutionHistory(List<ExecutionHistory> executionHistory) {
        this.executionHistory = executionHistory != null ? executionHistory : new ArrayList<>();
    }
    
    public String getWaitingFor() {
        return waitingFor;
    }
    
    public void setWaitingFor(String waitingFor) {
        this.waitingFor = waitingFor;
    }
    
    @Override
    public String toString() {
        return "WorkflowInstance{" +
                "id='" + id + '\'' +
                ", workflowId='" + workflowId + '\'' +
                ", state=" + state +
                ", currentNodeId='" + currentNodeId + '\'' +
                '}';
    }
}

package com.workflow.engine.persistence;

import com.workflow.engine.core.Workflow;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of WorkflowRepository.
 */
public class InMemoryWorkflowRepository implements WorkflowRepository {
    
    private final Map<String, Workflow> workflows;
    
    public InMemoryWorkflowRepository() {
        this.workflows = new ConcurrentHashMap<>();
    }
    
    @Override
    public void save(Workflow workflow) {
        if (workflow != null && workflow.getId() != null) {
            workflows.put(workflow.getId(), workflow);
        }
    }
    
    @Override
    public Optional<Workflow> findById(String workflowId) {
        return Optional.ofNullable(workflows.get(workflowId));
    }
    
    @Override
    public List<Workflow> findAll() {
        return new ArrayList<>(workflows.values());
    }
    
    @Override
    public void deleteById(String workflowId) {
        workflows.remove(workflowId);
    }
    
    @Override
    public boolean exists(String workflowId) {
        return workflows.containsKey(workflowId);
    }
}

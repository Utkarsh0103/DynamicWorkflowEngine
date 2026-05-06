package com.workflow.engine.persistence;

import com.workflow.engine.core.WorkflowInstance;
import com.workflow.engine.core.WorkflowState;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of WorkflowInstanceRepository.
 */
public class InMemoryWorkflowInstanceRepository implements WorkflowInstanceRepository {
    
    private final Map<String, WorkflowInstance> instances;
    
    public InMemoryWorkflowInstanceRepository() {
        this.instances = new ConcurrentHashMap<>();
    }
    
    @Override
    public void save(WorkflowInstance instance) {
        if (instance != null && instance.getId() != null) {
            instances.put(instance.getId(), instance);
        }
    }
    
    @Override
    public Optional<WorkflowInstance> findById(String instanceId) {
        return Optional.ofNullable(instances.get(instanceId));
    }
    
    @Override
    public List<WorkflowInstance> findByWorkflowId(String workflowId) {
        return instances.values().stream()
                .filter(instance -> workflowId.equals(instance.getWorkflowId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<WorkflowInstance> findByState(WorkflowState state) {
        return instances.values().stream()
                .filter(instance -> state == instance.getState())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<WorkflowInstance> findByContextVariable(String variableName, Object variableValue) {
        return instances.values().stream()
                .filter(instance -> {
                    Object value = instance.getContext().getVariable(variableName);
                    return value != null && value.equals(variableValue);
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public void update(WorkflowInstance instance) {
        save(instance); // In-memory update is same as save
    }
    
    @Override
    public void deleteById(String instanceId) {
        instances.remove(instanceId);
    }
    
    @Override
    public List<WorkflowInstance> findAll() {
        return new ArrayList<>(instances.values());
    }
}

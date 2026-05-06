package com.workflow.engine.definition;

import com.workflow.engine.core.Workflow;
import com.workflow.engine.node.Node;
import com.workflow.engine.node.NodeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validates workflow definitions.
 */
public class WorkflowValidator {
    
    /**
     * Validates a workflow.
     * 
     * @param workflow The workflow to validate
     * @return List of validation errors (empty if valid)
     */
    public List<String> validate(Workflow workflow) {
        List<String> errors = new ArrayList<>();
        
        if (workflow == null) {
            errors.add("Workflow is null");
            return errors;
        }
        
        // Check workflow ID
        if (workflow.getId() == null || workflow.getId().trim().isEmpty()) {
            errors.add("Workflow ID is required");
        }
        
        // Check for start node
        if (workflow.getStartNode() == null) {
            errors.add("Workflow must have a start node");
        }
        
        // Check for at least one end node
        if (workflow.getEndNodeIds().isEmpty()) {
            errors.add("Workflow must have at least one end node");
        }
        
        // Check for nodes
        if (workflow.getNodes().isEmpty()) {
            errors.add("Workflow must have at least one node");
        }
        
        // Validate each node
        Set<String> nodeIds = new HashSet<>();
        for (Node node : workflow.getNodes().values()) {
            // Check for duplicate IDs
            if (nodeIds.contains(node.getId())) {
                errors.add("Duplicate node ID: " + node.getId());
            }
            nodeIds.add(node.getId());
            
            // Validate node
            errors.addAll(validateNode(node, workflow));
        }
        
        // Check for unreachable nodes
        errors.addAll(checkReachability(workflow));
        
        return errors;
    }
    
    /**
     * Validates a single node.
     */
    private List<String> validateNode(Node node, Workflow workflow) {
        List<String> errors = new ArrayList<>();
        
        if (node.getId() == null || node.getId().trim().isEmpty()) {
            errors.add("Node ID is required");
        }
        
        if (node.getType() == null) {
            errors.add("Node type is required for node: " + node.getId());
        }
        
        // Check transitions point to valid nodes
        for (String targetId : node.getTransitions()) {
            if (workflow.getNode(targetId) == null) {
                errors.add("Node " + node.getId() + " has transition to non-existent node: " + targetId);
            }
        }
        
        // Type-specific validation
        if (node.getType() == NodeType.END && !node.getTransitions().isEmpty()) {
            errors.add("End node cannot have transitions: " + node.getId());
        }
        
        return errors;
    }
    
    /**
     * Checks if all nodes are reachable from the start node.
     */
    private List<String> checkReachability(Workflow workflow) {
        List<String> errors = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> allNodeIds = workflow.getNodes().keySet();
        
        if (workflow.getStartNode() != null) {
            traverse(workflow, workflow.getStartNode().getId(), visited);
        }
        
        // Find unreachable nodes
        for (String nodeId : allNodeIds) {
            if (!visited.contains(nodeId)) {
                errors.add("Unreachable node: " + nodeId);
            }
        }
        
        return errors;
    }
    
    /**
     * Traverses the workflow graph from a given node.
     */
    private void traverse(Workflow workflow, String nodeId, Set<String> visited) {
        if (nodeId == null || visited.contains(nodeId)) {
            return;
        }
        
        visited.add(nodeId);
        Node node = workflow.getNode(nodeId);
        
        if (node != null) {
            for (String targetId : node.getTransitions()) {
                traverse(workflow, targetId, visited);
            }
        }
    }
}

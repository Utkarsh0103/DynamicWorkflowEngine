package com.workflow.engine.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.workflow.engine.core.Workflow;
import com.workflow.engine.definition.*;
import com.workflow.engine.exception.InvalidWorkflowException;
import com.workflow.engine.node.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Loads workflows from JSON files.
 */
public class JsonWorkflowLoader implements WorkflowLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonWorkflowLoader.class);
    
    private final ObjectMapper objectMapper;
    private final WorkflowValidator validator;
    
    public JsonWorkflowLoader() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validator = new WorkflowValidator();
    }
    
    @Override
    public Workflow load(File file) throws InvalidWorkflowException {
        try {
            logger.info("Loading workflow from file: {}", file.getAbsolutePath());
            WorkflowDefinition definition = objectMapper.readValue(file, WorkflowDefinition.class);
            return buildWorkflow(definition);
        } catch (IOException e) {
            throw new InvalidWorkflowException("Failed to load workflow from file: " + file, e);
        }
    }
    
    @Override
    public Workflow load(InputStream inputStream) throws InvalidWorkflowException {
        try {
            logger.info("Loading workflow from input stream");
            WorkflowDefinition definition = objectMapper.readValue(inputStream, WorkflowDefinition.class);
            return buildWorkflow(definition);
        } catch (IOException e) {
            throw new InvalidWorkflowException("Failed to load workflow from stream", e);
        }
    }
    
    @Override
    public Workflow loadFromString(String content) throws InvalidWorkflowException {
        try {
            logger.info("Loading workflow from string");
            WorkflowDefinition definition = objectMapper.readValue(content, WorkflowDefinition.class);
            return buildWorkflow(definition);
        } catch (IOException e) {
            throw new InvalidWorkflowException("Failed to load workflow from string", e);
        }
    }
    
    /**
     * Builds a Workflow object from a WorkflowDefinition.
     */
    private Workflow buildWorkflow(WorkflowDefinition definition) throws InvalidWorkflowException {
        Workflow workflow = new Workflow();
        workflow.setId(definition.getWorkflowId());
        workflow.setName(definition.getName());
        workflow.setVersion(definition.getVersion());
        workflow.setDescription(definition.getDescription());
        workflow.setMetadata(definition.getMetadata());
        
        // Build nodes
        for (NodeDefinition nodeDef : definition.getNodes()) {
            Node node = buildNode(nodeDef);
            workflow.addNode(node);
            
            // Track start and end nodes
            if (node.getType() == NodeType.START) {
                workflow.setStartNodeId(node.getId());
            } else if (node.getType() == NodeType.END) {
                workflow.addEndNodeId(node.getId());
            }
        }
        
        // Validate workflow
        List<String> errors = validator.validate(workflow);
        if (!errors.isEmpty()) {
            throw new InvalidWorkflowException("Workflow validation failed", errors);
        }
        
        logger.info("Successfully loaded workflow: {} ({})", workflow.getName(), workflow.getId());
        return workflow;
    }
    
    /**
     * Builds a Node from a NodeDefinition.
     */
    private Node buildNode(NodeDefinition nodeDef) throws InvalidWorkflowException {
        NodeType type;
        try {
            type = NodeType.valueOf(nodeDef.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidWorkflowException("Invalid node type: " + nodeDef.getType());
        }
        
        Node node;
        
        switch (type) {
            case START:
                node = new StartNode(nodeDef.getId());
                break;
                
            case END:
                node = new EndNode(nodeDef.getId());
                break;
                
            case TASK:
                TaskNode taskNode = new TaskNode(nodeDef.getId());
                taskNode.setExecutorClassName(nodeDef.getExecutor());
                node = taskNode;
                break;
                
            case DECISION:
                DecisionNode decisionNode = new DecisionNode(nodeDef.getId());
                decisionNode.setCondition(nodeDef.getCondition());
                
                // Handle transitions map for decision nodes
                if (nodeDef.getTransitionsMap() != null) {
                    Object trueTransition = nodeDef.getTransitionsMap().get("true");
                    Object falseTransition = nodeDef.getTransitionsMap().get("false");
                    if (trueTransition != null) {
                        decisionNode.setTrueTransition(trueTransition.toString());
                    }
                    if (falseTransition != null) {
                        decisionNode.setFalseTransition(falseTransition.toString());
                    }
                }
                node = decisionNode;
                break;
                
            case WAIT:
                WaitNode waitNode = new WaitNode(nodeDef.getId());
                waitNode.setWaitFor(nodeDef.getWaitFor());
                waitNode.setAction(nodeDef.getAction());
                waitNode.setTimeoutMs(nodeDef.getTimeoutMs());
                node = waitNode;
                break;
                
            case PARALLEL:
                ParallelNode parallelNode = new ParallelNode(nodeDef.getId());
                if (nodeDef.getBranches() != null) {
                    for (BranchDefinition branchDef : nodeDef.getBranches()) {
                        parallelNode.addBranch(branchDef.getNodes());
                    }
                }
                node = parallelNode;
                break;
                
            case MERGE:
                MergeNode mergeNode = new MergeNode(nodeDef.getId());
                if (nodeDef.getExpectedBranches() != null) {
                    mergeNode.setExpectedBranches(nodeDef.getExpectedBranches());
                }
                node = mergeNode;
                break;
                
            default:
                throw new InvalidWorkflowException("Unsupported node type: " + type);
        }
        
        // Set common properties
        node.setName(nodeDef.getName());
        node.setTransitions(nodeDef.getTransitions());
        node.setConfigMap(nodeDef.getConfig());
        
        return node;
    }
}

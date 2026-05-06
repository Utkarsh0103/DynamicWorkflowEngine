package com.workflow.engine.node;

/**
 * Enumeration of node types in a workflow.
 */
public enum NodeType {
    START,
    END,
    TASK,
    DECISION,
    PARALLEL,
    MERGE,
    WAIT,
    SUBWORKFLOW
}

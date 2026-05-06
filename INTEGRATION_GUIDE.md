# Integration Guide: Dynamic Workflow Engine

## Overview

This guide explains how to integrate the Dynamic Workflow Engine into client applications, specifically for the BIA (Business Impact Analysis) approval workflow example.

## Installation

### Step 1: Build the Package

```bash
cd DynamicWorkflowEngine
mvn clean install
```

This will install the package to your local Maven repository.

### Step 2: Add Dependency to Client Project

In your client application's `pom.xml`:

```xml
<dependency>
    <groupId>com.workflow.engine</groupId>
    <artifactId>dynamic-workflow-engine</artifactId>
    <version>1.0.0</version>
</dependency>
```

## BIA Approval Workflow Implementation

### 1. Create Custom Task Executors

Create task executors for your specific business logic:

**CreateBIATask.java**
```java
package com.bia.tasks;

import com.workflow.engine.core.WorkflowContext;
import com.workflow.engine.executor.ExecutionResult;
import com.workflow.engine.executor.NodeExecutor;

public class CreateBIATask implements NodeExecutor {
    @Override
    public ExecutionResult execute(WorkflowContext context) {
        String biaName = (String) context.getVariable("bia.name");
        
        // Your business logic to create BIA
        BIA bia = biaService.createBIA(biaName);
        
        // Store BIA ID in context
        context.setVariable("bia.id", bia.getId());
        context.setVariable("bia.status", "DRAFT");
        
        return ExecutionResult.success("BIA created: " + bia.getId());
    }
}
```

**AssignApproversTask.java**
```java
public class AssignApproversTask implements NodeExecutor {
    @Override
    public ExecutionResult execute(WorkflowContext context) {
        String biaId = (String) context.getVariable("bia.id");
        Integer teamSequence = (Integer) context.getVariable("_node.config.teamSequence");
        
        List<Approver> approvers = approverService.assignApprovers(biaId, teamSequence);
        
        context.setVariable("approvers.team" + teamSequence, approvers);
        
        return ExecutionResult.success("Assigned " + approvers.size() + " approvers");
    }
}
```

### 2. Define Workflow in JSON

**bia-approval-workflow.json**
```json
{
  "workflowId": "bia-approval-process",
  "name": "BIA Approval Workflow",
  "version": "1.0",
  "nodes": [
    {
      "id": "start",
      "type": "START",
      "transitions": ["create-bia"]
    },
    {
      "id": "create-bia",
      "type": "TASK",
      "executor": "com.bia.tasks.CreateBIATask",
      "transitions": ["assign-team0"]
    },
    {
      "id": "assign-team0",
      "type": "TASK",
      "executor": "com.bia.tasks.AssignApproversTask",
      "config": {
        "teamSequence": 0
      },
      "transitions": ["notify-team0"]
    },
    {
      "id": "notify-team0",
      "type": "TASK",
      "executor": "com.bia.tasks.SendEmailTask",
      "transitions": ["wait-team0"]
    },
    {
      "id": "wait-team0",
      "type": "WAIT",
      "waitFor": "USER_ACTION",
      "action": "FILL_BIA_DATA",
      "transitions": ["validate-data"]
    },
    {
      "id": "validate-data",
      "type": "TASK",
      "executor": "com.bia.tasks.ValidateBIADataTask",
      "transitions": ["check-valid"]
    },
    {
      "id": "check-valid",
      "type": "DECISION",
      "condition": "${validation.success == true}",
      "transitionsMap": {
        "true": "assign-team1",
        "false": "notify-corrections"
      }
    },
    {
      "id": "assign-team1",
      "type": "TASK",
      "executor": "com.bia.tasks.AssignApproversTask",
      "config": {
        "teamSequence": 1
      },
      "transitions": ["wait-approval"]
    },
    {
      "id": "wait-approval",
      "type": "WAIT",
      "waitFor": "USER_ACTION",
      "action": "APPROVE_OR_REJECT",
      "transitions": ["approval-decision"]
    },
    {
      "id": "approval-decision",
      "type": "DECISION",
      "condition": "${approval.decision == 'APPROVED'}",
      "transitionsMap": {
        "true": "handle-approval",
        "false": "handle-rejection"
      }
    },
    {
      "id": "handle-approval",
      "type": "TASK",
      "executor": "com.bia.tasks.ApprovalTask",
      "transitions": ["end"]
    },
    {
      "id": "handle-rejection",
      "type": "TASK",
      "executor": "com.bia.tasks.RejectionTask",
      "transitions": ["end"]
    },
    {
      "id": "end",
      "type": "END"
    }
  ]
}
```

### 3. Spring Boot Integration

**WorkflowConfig.java**
```java
@Configuration
public class WorkflowConfig {
    
    @Bean
    public WorkflowEngine workflowEngine() {
        WorkflowEngine engine = new WorkflowEngine();
        
        // Register all task executors
        engine.registerExecutor("com.bia.tasks.CreateBIATask", createBIATask());
        engine.registerExecutor("com.bia.tasks.AssignApproversTask", assignApproversTask());
        engine.registerExecutor("com.bia.tasks.SendEmailTask", sendEmailTask());
        
        return engine;
    }
    
    @Bean
    public CreateBIATask createBIATask() {
        return new CreateBIATask();
    }
    
    // Other task beans...
}
```

## Benefits of This Approach

1. ✅ **Reduced API Surface** - Single workflow API instead of multiple endpoints
2. ✅ **Centralized Business Logic** - All process logic in JSON configuration
3. ✅ **Dynamic Changes** - Modify workflow without code changes
4. ✅ **Client Customization** - Different workflows for different clients
5. ✅ **Complete Audit Trail** - Automatic execution history
6. ✅ **State Management** - Automatic workflow state tracking
7. ✅ **Resume Capability** - Can resume workflows after crashes
8. ✅ **Parallel Execution** - Execute tasks in parallel
9. ✅ **Event Monitoring** - Built-in event system for monitoring

## Next Steps

1. Build and install the workflow engine package
2. Create your custom task executors
3. Define your workflow in JSON
4. Configure Spring Boot integration
5. Test with example workflows
6. Deploy to production

For more examples, see the `src/main/java/com/workflow/engine/example` directory.

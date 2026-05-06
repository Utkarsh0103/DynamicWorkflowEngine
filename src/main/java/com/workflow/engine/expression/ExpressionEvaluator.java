package com.workflow.engine.expression;

import com.workflow.engine.core.WorkflowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Evaluates expressions in workflow conditions using Spring Expression Language (SpEL).
 */
public class ExpressionEvaluator {
    
    private static final Logger logger = LoggerFactory.getLogger(ExpressionEvaluator.class);
    
    private final ExpressionParser parser;
    
    public ExpressionEvaluator() {
        this.parser = new SpelExpressionParser();
    }
    
    /**
     * Evaluates a boolean expression against a workflow context.
     * 
     * @param expressionString The expression string (e.g., "${status == 'APPROVED'}")
     * @param context The workflow context
     * @return The evaluated boolean result
     */
    public boolean evaluateBoolean(String expressionString, WorkflowContext context) {
        try {
            // Remove ${} wrapper if present
            String cleanExpression = cleanExpression(expressionString);
            
            // Create evaluation context from workflow context
            StandardEvaluationContext evalContext = new StandardEvaluationContext();
            
            // Add all workflow variables to the evaluation context
            for (String key : context.getAllVariables().keySet()) {
                evalContext.setVariable(key, context.getVariable(key));
            }
            
            // Parse and evaluate
            Expression expression = parser.parseExpression(cleanExpression);
            Object result = expression.getValue(evalContext);
            
            if (result instanceof Boolean) {
                return (Boolean) result;
            } else {
                logger.warn("Expression did not evaluate to boolean: {}", expressionString);
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Error evaluating expression: {}", expressionString, e);
            return false;
        }
    }
    
    /**
     * Evaluates an expression and returns the result as an object.
     * 
     * @param expressionString The expression string
     * @param context The workflow context
     * @return The evaluated result
     */
    public Object evaluate(String expressionString, WorkflowContext context) {
        try {
            String cleanExpression = cleanExpression(expressionString);
            
            StandardEvaluationContext evalContext = new StandardEvaluationContext();
            for (String key : context.getAllVariables().keySet()) {
                evalContext.setVariable(key, context.getVariable(key));
            }
            
            Expression expression = parser.parseExpression(cleanExpression);
            return expression.getValue(evalContext);
            
        } catch (Exception e) {
            logger.error("Error evaluating expression: {}", expressionString, e);
            return null;
        }
    }
    
    /**
     * Resolves variable references in a string (e.g., "${variable.name}").
     * 
     * @param text The text containing variable references
     * @param context The workflow context
     * @return The resolved text
     */
    public String resolveVariables(String text, WorkflowContext context) {
        if (text == null || !text.contains("${")) {
            return text;
        }
        
        String result = text;
        int startIdx = 0;
        
        while ((startIdx = result.indexOf("${", startIdx)) != -1) {
            int endIdx = result.indexOf("}", startIdx);
            if (endIdx == -1) {
                break;
            }
            
            String expression = result.substring(startIdx + 2, endIdx);
            Object value = evaluate(expression, context);
            
            String replacement = value != null ? value.toString() : "";
            result = result.substring(0, startIdx) + replacement + result.substring(endIdx + 1);
            
            startIdx += replacement.length();
        }
        
        return result;
    }
    
    /**
     * Removes ${} wrapper from expression string.
     * 
     * @param expressionString The expression string
     * @return Clean expression
     */
    private String cleanExpression(String expressionString) {
        if (expressionString == null) {
            return "";
        }
        
        String cleaned = expressionString.trim();
        if (cleaned.startsWith("${") && cleaned.endsWith("}")) {
            cleaned = cleaned.substring(2, cleaned.length() - 1);
        }
        
        return cleaned;
    }
}

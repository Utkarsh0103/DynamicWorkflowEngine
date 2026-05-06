package com.workflow.engine.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for generating unique identifiers.
 */
public class IdGenerator {
    
    private static final AtomicLong sequence = new AtomicLong(0);
    
    /**
     * Generates a UUID-based unique identifier.
     * 
     * @return A unique identifier string
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Generates a prefixed unique identifier.
     * 
     * @param prefix The prefix for the identifier
     * @return A prefixed unique identifier string
     */
    public static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString();
    }
    
    /**
     * Generates a sequential ID with prefix.
     * 
     * @param prefix The prefix for the identifier
     * @return A sequential identifier string
     */
    public static String generateSequentialId(String prefix) {
        return prefix + "-" + sequence.incrementAndGet();
    }
    
    /**
     * Generates a compact UUID (without hyphens).
     * 
     * @return A compact unique identifier string
     */
    public static String generateCompactId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

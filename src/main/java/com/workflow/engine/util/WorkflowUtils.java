package com.workflow.engine.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Utility class for workflow operations.
 */
public class WorkflowUtils {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    /**
     * Checks if a string is null or empty.
     * 
     * @param str The string to check
     * @return true if the string is null or empty
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Checks if a string is not null and not empty.
     * 
     * @param str The string to check
     * @return true if the string is not null and not empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * Formats a LocalDateTime to ISO string.
     * 
     * @param dateTime The LocalDateTime to format
     * @return Formatted string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }
    
    /**
     * Parses an ISO string to LocalDateTime.
     * 
     * @param dateTimeStr The string to parse
     * @return LocalDateTime instance
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, FORMATTER) : null;
    }
    
    /**
     * Deep copy of a map (shallow for values).
     * 
     * @param original The original map
     * @param <K> Key type
     * @param <V> Value type
     * @return A new map with same entries
     */
    public static <K, V> Map<K, V> copyMap(Map<K, V> original) {
        return original != null ? new java.util.HashMap<>(original) : new java.util.HashMap<>();
    }
    
    /**
     * Safe toString for any object.
     * 
     * @param obj The object
     * @return String representation or "null"
     */
    public static String safeToString(Object obj) {
        return obj != null ? obj.toString() : "null";
    }
}

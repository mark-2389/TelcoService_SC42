package it.polimi.db2.telcoservice_sc42.utils;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class that holds the names of the session's attributes.
 * This class should be used for general purpose program wide attributes only.
 */
public class SessionAttributeRegistry {
    public static final String username = "username";
    public static final String employeeId = "id";
    public static final String error = "error";

    private static List<String> fields = null;


    /**
     * Idk, it should return all the values of static attributes.
     * @return A list of values of the static attributes.
     */
    public static List<String> getAllFields() {
        if ( fields == null ) {
            fields = Arrays.stream(SessionAttributeRegistry.class.getDeclaredFields())
                    .filter(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers()))
                    .map(f -> {
                        try {
                            return (String) f.get(String.class);
                        } catch (IllegalAccessException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return fields;
    }

    private static final Map<String, String> runtimeAttributes = new HashMap<>();

    public static void addRuntimeAttribute(String name, String value) {
        runtimeAttributes.put(name, value);
    }

    public static String getRuntimeAttribute(String name) {
        if ( name == null ) return null;
        return runtimeAttributes.get(name);
    }
}

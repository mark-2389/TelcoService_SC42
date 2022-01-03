package it.polimi.db2.telcoservice_sc42.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that holds the names of the session's attributes.
 * This class should be used for general purpose program wide attributes only.
 */
public class SessionAttributeRegistry {
    public static final String username = "username";
    public static final String employeeId = "id";


    public static final String selectedOptionals = "optionals";
    public static final String error = "error";

    /**
     * Employee's home attribute that saves all the available services.
     */
    public static final String allServices = "services";

    /**
     * Employee's home attribute that saves all the available optional products.
     */
    public static final String allOptionals = "optionals";

    /**
     * Employee's home attribute that saves all added validities.
     */
    public static final String validities = "validities";



    private static final Map<String, String> runtimeAttributes = new HashMap<>();

    public static void addRuntimeAttribute(String name, String value) {
        runtimeAttributes.put(name, value);
    }

    public static String getRuntimeAttribute(String name) {
        if ( name == null ) return null;
        return runtimeAttributes.get(name);
    }
}

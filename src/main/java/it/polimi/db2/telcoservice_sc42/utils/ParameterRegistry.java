package it.polimi.db2.telcoservice_sc42.utils;

import java.util.HashMap;
import java.util.Map;

public class ParameterRegistry {
    public static final String optional = "optional";
    public static final String optionalsCheckbox = "optionals";
    public static final String validityPeriod = "validity_period";
    public static final String validityFee = "validity_monthly_fee";

    private static final Map<String, String> runtimes = new HashMap<>();

    public static void addRuntimeParameter(String name, String value) {
        runtimes.put(name, value);
    }

    public static String getRuntimeParameter(String name) {
        if ( name == null ) return null;
        return runtimes.get(name);
    }
}

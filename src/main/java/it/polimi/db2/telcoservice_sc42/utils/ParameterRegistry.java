package it.polimi.db2.telcoservice_sc42.utils;

import java.util.HashMap;
import java.util.Map;

public class ParameterRegistry {
    public static final String optional = "optional";
    public static final String optionalsCheckbox = "optionals";
    public static final String validityPeriod = "validity_period";
    public static final String validityFee = "validity_monthly_fee";
    public static final String allAverageOptionalProduct = "averages";
    public static final String bestOptionalProduct = "best";
    public static final String allPurchasesPerPackage = "purchases";
    public static final String allPurchasesPerPackageValidity = "purchasesValidity";
    public static final String allValuesPerPackageOptionalProduct = "valuesPackageOptional";
    public static final String allValuesPerPackageWithoutOp = "values";
    public static final String insolventUsers = "insolvents";
    public static final String suspendedOrders = "orders";
    public static final String activeAlerts = "alerts";


    private static final Map<String, String> runtimes = new HashMap<>();

    public static void addRuntimeParameter(String name, String value) {
        runtimes.put(name, value);
    }

    public static String getRuntimeParameter(String name) {
        if ( name == null ) return null;
        return runtimes.get(name);
    }
}

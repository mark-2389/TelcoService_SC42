package it.polimi.db2.telcoservice_sc42.utils;

public class EmployeeSessionRegistry extends SessionAttributeRegistry {

    /**
     * A list of valid OptionalProducts the employee can choose among when creating a ServicePackage (used in home).
     * Type: List<OptionalProduct>
     */
    public static final String allOptionalsAttribute = "optionals";

    /**
     * A list of valid Services the employee can choose among when creating a ServicePackage (used in home).
     * Type: List<Service>
     */
    public static final String allServicesAttribute = "services";

    /**
     * A list of Validities the employee can choose among when creating a ServicePackage (used in home).
     * Type: List<Validity>
     */
    public static final String validities = "validities";

    // The following hold the values needed for employee's sales report.
    public static final String allAverageOptionalProduct = "averages";
    public static final String bestOptionalProduct = "best";
    public static final String allPurchasesPerPackage = "purchases";
    public static final String allPurchasesPerPackageValidity = "purchasesValidity";
    public static final String allValuesPerPackageOptionalProduct = "valuesPackageOptional";
    public static final String allValuesPerPackageWithoutOp = "values";
    public static final String insolventUsers = "insolvents";
    public static final String suspendedOrders = "orders";
    public static final String activeAlerts = "alerts";


}

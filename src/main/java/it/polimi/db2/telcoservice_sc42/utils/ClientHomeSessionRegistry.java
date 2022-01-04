package it.polimi.db2.telcoservice_sc42.utils;

public class ClientHomeSessionRegistry extends SessionAttributeRegistry {

    /**
     * Holds the list of all valid ServicePackages (i.e. the ServicePackages the client can buy).
     * Type: List<ServicePackage>
     */
    public static final String packages = "packages";

    /**
     * Holds the list of all rejected Orders.
     * Type: List<Order>
     */
    public static final String rejected = "rejected";


}

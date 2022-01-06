package it.polimi.db2.telcoservice_sc42.utils;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> getAllFields(){
        List<String> superFields = SessionAttributeRegistry.getAllFields();
        System.out.println(superFields);

        return Arrays.stream(ClientHomeSessionRegistry.class.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers()))
                .map(f -> {
                    try {
                        return (String) f.get(String.class);
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                })
                .filter(f -> f != null && !superFields.contains(f))
                .collect(Collectors.toList());
    }
}

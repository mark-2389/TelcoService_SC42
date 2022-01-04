package it.polimi.db2.telcoservice_sc42.utils;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuySessionRegistry extends SessionAttributeRegistry {

    /**
     * Holds the id of the package the user wants to buy.
     * Type:  Integer
     */
    public static String selectedPackage = "selectedPackage";

    /**
     * Holds the name of the package the user wants to buy. 
     * Type:  String
     */
    public static String selectedPackageName = "selectedPackage_name";

    /**
     * Holds the id of the validity associated to the package the user wants to buy.
     * Type:  String
     */
    public static String chosenValidity = "chosen_validity";

    /**
     * Holds the number of months of the validity associated to the package the user wants to buy.
     * Type:  Integer
     */
    public static String chosenValidityMonths = "chosen_validity_months";

    /**
     * Holds the fee of the validity associated to the package the user wants to buy.
     * Type:  BigDecimal
     */
    public static String chosenValidityFee = "chosen_validity_fee";


    /**
     * Holds the string representation of the subscription date associated with the package the user wants to buy.
     * The string representation is obtained from sql.Date.
     * Type:  String
     */
    public static String chosenSubscription = "chosen_subscription";

    /**
     * Holds the list of ids of the OptionalProducts associated with the package the user wants to buy.
     * Type:  String[]
     */
    public static String chosenOptionals = "chosen_optionals";

    /**
     * Holds the sum of the costs of the OptionalProducts the user wants to buy.
     * Type:  BigDecimal
     */
    public static String totalOptionalsFee = "total_optionals_fee";

    /**
     * Holds the string representation (i.e. toString()) OptionalProducts the user wants to buy.
     * Type:  List<String>
     */
    public static String chosenOptionalsDescriptions = "chosen_optionals_desc";

    /**
     * Holds a message that describes if the payment of an order happened successfully.
     * Type:  String
     */
    public static String paymentMsg = "payment_msg";

    public static List<String> getAllFields() {
        List<String> superFields = SessionAttributeRegistry.getAllFields();
        System.out.println(superFields);

        return Arrays.stream(BuySessionRegistry.class.getDeclaredFields())
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

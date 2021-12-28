package it.polimi.db2.telcoservice_sc42.utils;

import java.math.BigDecimal;

/**
 * The SafeParser class offers static methods that try to parse a numeric value and if some exception is thrown, null is
 * returned instead of throwing the exception to the caller.
 */
public class SafeParser {
    /**
     * Parse the string and return a Float if the string contains a valid float or null otherwise.
     * @param number the string to convert
     * @return a Float if the string contains a valid float, null otherwise.
     */
    public static Float safeParseFloat(String number) {
        float value;

        if ( number == null ) return null;

        try {
            value = Float.parseFloat(number);
        } catch (NumberFormatException e) {
            return null;
        }

        return value;
    }

    /**
     * Parse the string and return an Integer if the string contains a valid integer or null otherwise.
     * @param number the string to convert
     * @return a Integer if the string contains a valid integer, null otherwise.
     */
    public static Integer safeParseInteger(String number) {
        int value;

        if ( number == null ) return null;

        try {
            value = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return null;
        }

        return value;
    }

    /**
     * Parse the string and return a BigDecimal if the string contains a valid decimal or null otherwise.
     * @param number the string to convert
     * @return a BigDecimal if the string contains a valid decimal, null otherwise.
     */
    public static BigDecimal safeParseBigDecimal(String number) {
        BigDecimal value;

        if ( number == null ) { return null; }

        try {
            value = new BigDecimal(number);
        } catch (NumberFormatException exception) {
            return null;
        }

        return value;
    }
}

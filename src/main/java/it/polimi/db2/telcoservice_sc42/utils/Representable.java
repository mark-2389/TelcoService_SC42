package it.polimi.db2.telcoservice_sc42.utils;

/**
 * A Representable is an object that can be represented to a Client or to an Employee.
 */
public interface Representable {
    String clientString();
    String employeeString();
}

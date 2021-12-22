package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.Employee;
import it.polimi.db2.telcoservice_sc42.exception.ClientNotFoundException;
import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
import jakarta.ejb.Stateless;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "TelcoService_EJB")
    EntityManager entityManager;

    public EmployeeService() {
    }

    /**
     * Check if the Employee with the specified credentials is present in the database.
     *
     * @param username the username of the Employee.
     * @param password the password of the Employee.
     * @return the specified Employee if it's found on the database.
     * @throws ClientNotFoundException  if the Employee with the specified credentials doesn't exist.
     * @throws NonUniqueClientException if more Employee with the specified credentials exist.
     */
    public Employee checkCredentials(String username, String password) throws ClientNotFoundException, NonUniqueClientException {
        List<Employee> employees;

        employees = entityManager.createNamedQuery("Employee.withCredentials", Employee.class)
                                 .setParameter(1, username).setParameter(2, password)
                                 .getResultList();

        if ( employees.isEmpty() ) {
            // TODO: consider changing exception to EmployeeNotFoundException
            throw new ClientNotFoundException();
        }

        if ( employees.size() > 1 ) {
            throw new NonUniqueClientException();
        }

        return employees.get(0);
    }
}

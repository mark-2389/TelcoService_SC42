package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.withCredentials", query = "SELECT e FROM Employee e  WHERE e.id = ?1 and e.password = ?2")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USERNAME", nullable = false)
    private String id;

    @Column(name = "PASSWORD", nullable = false, length = 31)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return id;
    }

    public void setUsername(String id) {
        this.id = id;
    }
}
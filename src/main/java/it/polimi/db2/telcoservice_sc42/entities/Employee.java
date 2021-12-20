package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "employee")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
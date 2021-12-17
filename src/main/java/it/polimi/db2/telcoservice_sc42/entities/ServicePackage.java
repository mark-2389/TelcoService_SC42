package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import java.sql.Date;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity(name = "service_package")
public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @OneToMany(mappedBy = "servicePackage", fetch=FetchType.LAZY)
    private List<Order> orders;

    @ManyToMany(mappedBy = "packages")
    private Collection<Service> services;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

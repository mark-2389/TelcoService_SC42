package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity(name = "service_package")
public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @OneToMany(mappedBy = "servicePackage", fetch=FetchType.LAZY)
    private List<Order> orders;

    @ManyToMany(mappedBy = "packages")
    private Collection<Service> services;

    @OneToMany(mappedBy = "servicePackage", fetch = FetchType.EAGER)
    private List<Validity> validities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<Validity> getValidities() {
        return validities;
    }

    public void setValidities(List<Validity> validities) {
        this.validities = validities;
    }
}

package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "optional_product")
@NamedQuery(name = "OptionalProduct.all", query = "SELECT p FROM OptionalProduct p")
@NamedQuery(name = "OptionalProduct.valid", query = "SELECT p FROM OptionalProduct p WHERE ( p.expirationDate >= current_date or p.expirationDate = NULL )")
public class OptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "monthly_fee")
    private float monthlyFee;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date")
    private Date expirationDate;


    @ManyToMany
    @JoinTable
            (name = "order_optional_composition",
                    joinColumns = @JoinColumn(name="optional_product_id"),
                    inverseJoinColumns = @JoinColumn(name="order_id"))
    private List<Order> orders;

    @ManyToMany
    @JoinTable
    	(name = "optional_product_composition",
    	joinColumns = @JoinColumn(name="optional_product_id"),
    	inverseJoinColumns = @JoinColumn(name="package_id"))
    private List<ServicePackage> packages;


    @ManyToMany
    @JoinTable
            (name = "optional_schedule",
                    joinColumns = @JoinColumn(name="optional_product_id"),
                    inverseJoinColumns = @JoinColumn(name="username"))
    private List<Client> clients;

    public OptionalProduct() {
        this("");
    }

    public OptionalProduct(String name) {
        this.name = name;
        this.monthlyFee = 0.0F;
        this.expirationDate = null;
    }

    public OptionalProduct(String name, Float fee, Date expirationDate) {
        this(name);
        this.monthlyFee = fee;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + expirationDate + " - " + monthlyFee;
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof OptionalProduct) ) {
            return false;
        }

        return Objects.equals(this.id, ((OptionalProduct) other).id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<ServicePackage> getPackages() {
        return packages;
    }

    public void setPackages(List<ServicePackage> packages) {
        this.packages = packages;
    }

    public void addPackage(ServicePackage servicePackage) {
        getPackages().add(servicePackage);
    }

    public void removePackage(ServicePackage servicePackage){
        getPackages().remove(servicePackage);
    }
}
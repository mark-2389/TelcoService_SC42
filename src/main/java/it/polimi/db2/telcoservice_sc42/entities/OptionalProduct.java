package it.polimi.db2.telcoservice_sc42.entities;

import it.polimi.db2.telcoservice_sc42.utils.Representable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "optional_product")
@NamedQuery(name = "OptionalProduct.all", query = "SELECT p FROM OptionalProduct p")
@NamedQuery(name = "OptionalProduct.valid", query = "SELECT p FROM OptionalProduct p WHERE p.expirationDate = null OR p.expirationDate >= current_date ")
public class OptionalProduct implements Serializable, Representable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "monthly_fee", precision = 2)
    private BigDecimal monthlyFee;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date")
    private Date expirationDate;


    @ManyToMany(mappedBy = "optionals", fetch=FetchType.LAZY)
    private List<Order> orders;

    @ManyToMany(mappedBy = "products", fetch=FetchType.LAZY)
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
        this.monthlyFee = BigDecimal.ZERO;
        this.expirationDate = null;
    }

    public OptionalProduct(String name, BigDecimal fee, Date expirationDate) {
        this(name);
        this.monthlyFee = fee.setScale(2, RoundingMode.HALF_UP);
        this.expirationDate = expirationDate;
    }

    public String clientString() {
        // maybe the id isn't needed
        return name + " - " + monthlyFee + " €/month";
    }

    public String employeeString() {
        return this.toString();
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

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
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
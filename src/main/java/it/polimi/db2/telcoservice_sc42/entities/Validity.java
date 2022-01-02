package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@IdClass(ValidityPrimaryKey.class)
public class Validity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Id
    @ManyToOne
    @JoinColumn (name = "PACKAGE_ID")
    private ServicePackage servicePackage;

    private Integer period;

    @Column(name = "MONTHLY_FEE", precision = 2)
    private BigDecimal monthlyFee;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @OneToMany(mappedBy="validity", fetch=FetchType.LAZY)
    private List<Order> orders;

    public Validity() {
    }

    public Validity(ServicePackage servicePackage, int period, BigDecimal monthlyFee, Date expirationDate ){
        this.setServicePackage(servicePackage);
        this.period = period;
        this.monthlyFee = monthlyFee;
        this.expirationDate = expirationDate;
    }

    public Validity(ServicePackage servicePackage, int period, BigDecimal monthlyFee ){
        this.setServicePackage(servicePackage);
        this.period = period;
        this.monthlyFee = monthlyFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
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

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Validity{" +
                "id=" + id +
                ", servicePackage=" + servicePackage.getName() +
                ", period=" + period +
                ", monthlyFee=" + monthlyFee +
                ", expirationDate=" + expirationDate +
                '}';
    }
}

package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@IdClass(ValidityPrimaryKey.class)
class Validity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Id
    private Integer packageId;
    private Integer period;

    @Column(name = "monthly_fee")
    private Float monthlyFee;

    @Column(name = "expiration_date")
    private String expirationDate;

    @OneToMany(mappedBy="validity", fetch=FetchType.LAZY)
    private List<Order> validities;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getServiceId() {
        return packageId;
    }
    public void setServiceId(int serviceId) {
        this.packageId = serviceId;
    }
    public int getPeriod() {
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
    public Float getMonthlyFee() {
        return monthlyFee;
    }
    public void setMonthlyFee(Float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}

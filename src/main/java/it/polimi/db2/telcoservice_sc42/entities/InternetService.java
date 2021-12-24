package it.polimi.db2.telcoservice_sc42.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "internet_service")
public class InternetService extends Service {
    private static final long serialVersionUID = 1L;

    @Column(name = "is_mobile")
    private boolean isMobile;

    @Column(name = "gb")
    private Integer gigaByte;

    @Column(name = "gb_fee", precision = 2)
    private BigDecimal gigaByteFee;

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public Integer getGigaByte() {
        return gigaByte;
    }

    public void setGigaByte(Integer gigaByte) {
        this.gigaByte = gigaByte;
    }

    public BigDecimal getGigaByteFee() {
        return gigaByteFee;
    }

    public void setGigaByteFee(BigDecimal gigaByteFee) {
        this.gigaByteFee = gigaByteFee;
    }

}
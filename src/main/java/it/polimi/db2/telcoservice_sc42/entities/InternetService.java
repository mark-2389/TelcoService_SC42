package it.polimi.db2.telcoservice_sc42.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

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

    /**
     * By default isMobile is assigned true.
     *
     * ALERT: This is a convenience constructor, consider using the complete constructor.
     */
    public InternetService() {
        this.isMobile = true;
    }

    public InternetService(Date expirationDate, Integer gb, BigDecimal gbFee, boolean isMobile) {
        super(isMobile ? ServiceType.MOBILE_INTERNET : ServiceType.FIXED_INTERNET,  expirationDate);
        this.gigaByte = gb;
        this.gigaByteFee = gbFee;
    }

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
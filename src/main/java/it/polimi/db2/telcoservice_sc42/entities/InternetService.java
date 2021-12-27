package it.polimi.db2.telcoservice_sc42.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "internet_service")
public class InternetService extends Service {
    private static final long serialVersionUID = 1L;

    @Column(name = "is_mobile")
    private boolean isMobile;

    @Column(name = "gb")
    private Integer gigaByte;

    @Column(name = "gb_fee")
    private float gigaByteFee;

    /**
     * By default isMobile is assigned true.
     *
     * ALERT: This is a convenience constructor, consider using the complete constructor.
     */
    public InternetService() {
        this.isMobile = true;
    }

    public InternetService(Date expirationDate, Integer gb, float gbFee, boolean isMobile) {
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

    public int getGigaByte() {
        return gigaByte;
    }

    public void setGigaByte(int gigaByte) {
        this.gigaByte = gigaByte;
    }

    public float getGigaByteFee() {
        return gigaByteFee;
    }

    public void setGigaByteFee(float gigaByteFee) {
        this.gigaByteFee = gigaByteFee;
    }

}
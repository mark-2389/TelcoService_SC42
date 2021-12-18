package it.polimi.db2.telcoservice_sc42.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "InternetService")
public class InternetService extends Service {
    private static final long serialVersionUID = 1L;

    private boolean isMobile;

    @Column(name = "gb")
    private Integer gigaByte;

    @Column(name = "gbfee")
    private float gigaByteFee;

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
package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "mobile_phone_service")
public class MobilePhoneService extends Service {
    private static final long serialVersionUID = 1L;

    private Integer minutes;
    private Integer sms;

    @Column(name = "minutes_fee")
    private float minutesFee;

    @Column(name = "sms_fee")
    private float smsFee;

    public MobilePhoneService() {
        // by default the empty constructor should set numeric values to 0 when not specified (please check)
    }

    public MobilePhoneService(Date expirationDate, Integer minutes, float minutesFee, Integer sms, float smsFee) {
        super(ServiceType.MOBILE_PHONE, expirationDate);
        this.minutes = minutes;
        this.minutesFee = minutesFee;
        this.sms = sms;
        this.smsFee = smsFee;
    }


    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    public int getSms() {
        return sms;
    }
    public void setSms(int sms) {
        this.sms = sms;
    }
    public float getMinutesFee() {
        return minutesFee;
    }
    public void setMinutesFee(float minutesFee) {
        this.minutesFee = minutesFee;
    }
    public float getSmsFee() {
        return smsFee;
    }
    public void setSmsFee(float smsFee) {
        this.smsFee = smsFee;
    }
}
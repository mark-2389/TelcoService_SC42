package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "FixedPhoneService")
public class FixedPhoneService extends Service {
    private static final long serialVersionUID = 1L;

    private Integer minutes;
    private Integer sms;
    private float minutesFee;
    private float smsFee;


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
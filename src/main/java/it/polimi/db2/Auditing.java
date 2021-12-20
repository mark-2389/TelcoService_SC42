package it.polimi.db2;

import it.polimi.db2.telcoservice_sc42.entities.Client;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
@IdClass(AuditingPK.class)
public class Auditing implements Serializable {
    //todo check for JPA annotations
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ManyToOne
    @JoinColumn (name = "username")
    private Client username;

    @ManyToOne
    @JoinColumn (name = "email")
    private Client email;

    @Basic
    @Column(name = "AMOUNT", nullable = false, precision = 2)
    private BigDecimal amount;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REJECTION_DATE", nullable = false)
    private Date rejectionDate;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REJECTION_TIME", nullable = false)
    private Time rejectionTime;

    @Basic
    @Column(name = "IS_ACTIVE", nullable = true)
    private Byte isActive;

    public Client getUsername() {
        return username;
    }

    public void setUsername(Client username) {
        this.username = username;
    }

    public Client getEmail() {
        return email;
    }

    public void setEmail(Client email) {
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Date rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Time getRejectionTime() {
        return rejectionTime;
    }

    public void setRejectionTime(Time rejectionTime) {
        this.rejectionTime = rejectionTime;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auditing auditing = (Auditing) o;

        if (username != null ? !username.equals(auditing.username) : auditing.username != null) return false;
        if (email != null ? !email.equals(auditing.email) : auditing.email != null) return false;
        if (amount != null ? !amount.equals(auditing.amount) : auditing.amount != null) return false;
        if (rejectionDate != null ? !rejectionDate.equals(auditing.rejectionDate) : auditing.rejectionDate != null)
            return false;
        if (rejectionTime != null ? !rejectionTime.equals(auditing.rejectionTime) : auditing.rejectionTime != null)
            return false;
        if (isActive != null ? !isActive.equals(auditing.isActive) : auditing.isActive != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (rejectionDate != null ? rejectionDate.hashCode() : 0);
        result = 31 * result + (rejectionTime != null ? rejectionTime.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }
}

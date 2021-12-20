package it.polimi.db2;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class AuditingPK implements Serializable {
    @Column(name = "USERNAME", nullable = false, length = 255)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String username;

    @Column(name = "REJECTION_DATE", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Date rejectionDate;

    @Column(name = "REJECTION_TIME", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Time rejectionTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditingPK that = (AuditingPK) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (rejectionDate != null ? !rejectionDate.equals(that.rejectionDate) : that.rejectionDate != null)
            return false;
        if (rejectionTime != null ? !rejectionTime.equals(that.rejectionTime) : that.rejectionTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (rejectionDate != null ? rejectionDate.hashCode() : 0);
        result = 31 * result + (rejectionTime != null ? rejectionTime.hashCode() : 0);
        return result;
    }
}

package it.polimi.db2.telcoservice_sc42.entities;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * A Validity that can be used to detach a Validity from a ServicePackage
 */
public class IndependentValidityPeriod {
    private final int period;
    private final BigDecimal fee;
    private final Date expirationDate;
    public static String idSeparator = "#";

    public IndependentValidityPeriod(int period, float fee, Date expirationDate) {
        this.period = period;
        this.fee = new BigDecimal(fee);
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return period + "months - " + fee + "€/month";
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IndependentValidityPeriod) ) {
            return false;
        }

        IndependentValidityPeriod o = (IndependentValidityPeriod) other;

        return this.fee.equals(o.fee) && this.period == o.period;
    }


    public Validity getValidityWith(ServicePackage p) {
        return new Validity(p, this.period, this.fee, expirationDate);
    }

    public String getId() {
        return period + idSeparator + fee + idSeparator + expirationDate;
    }

    public float getFee() {
        return fee.floatValue();
    }

    public int getPeriod() {
        return period;
    }
}

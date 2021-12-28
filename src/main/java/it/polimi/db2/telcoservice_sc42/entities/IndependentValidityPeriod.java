package it.polimi.db2.telcoservice_sc42.entities;

import java.math.BigDecimal;

/**
 * A Validity that can be used to detach a Validity from a ServicePackage
 */
public class IndependentValidityPeriod {
    private final int period;
    private final BigDecimal fee;

    public IndependentValidityPeriod(int period, float fee) {
        this.period = period;
        this.fee = new BigDecimal(fee);
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
        return new Validity(p, this.period, this.fee);
    }

    public String getId() {
        return period + "-" + fee;
    }

    public float getFee() {
        return fee.floatValue();
    }

    public int getPeriod() {
        return period;
    }
}

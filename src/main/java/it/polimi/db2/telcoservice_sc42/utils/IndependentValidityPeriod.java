package it.polimi.db2.telcoservice_sc42.utils;

import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.entities.Validity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

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

    /**
     * Cut a the decimal part of a number.
     *
     * For instance if number is 3.56742 and decimals is 2 then "3.56" is returned.
     * @param number the number to be cut.
     * @param decimals the number of decimals to be kept.
     * @return a cut version of the input.
     */
    private String cutDecimal(BigDecimal number, int decimals) {
        String s = number.toString();
        List<String> parts = Arrays.asList(s.split("\\."));

        if ( parts.size() < 2 ) {
            return s;
        }

        return parts.get(0) + "." + parts.get(1).substring(0, decimals);
    }

    @Override
    public String toString() {
        return period + "months - " + cutDecimal(fee, 2) + "€/month";
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

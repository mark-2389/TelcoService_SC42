package it.polimi.db2.telcoservice_sc42.primaryKeys;

import java.io.Serializable;

public class OrderOptionalSchedulePrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"unused"})
    private Integer order;

    @SuppressWarnings({"unused"})
    private Integer optional;

    public OrderOptionalSchedulePrimaryKey() { }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

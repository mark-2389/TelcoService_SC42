package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;

public class ValidityPrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private Integer id;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private Integer servicePackage;

    public ValidityPrimaryKey() { }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}


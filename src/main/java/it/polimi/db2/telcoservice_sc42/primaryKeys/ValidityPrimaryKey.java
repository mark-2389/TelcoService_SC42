package it.polimi.db2.telcoservice_sc42.primaryKeys;

import java.io.Serializable;

public class ValidityPrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"unused"})
    private Integer id;

    @SuppressWarnings({"unused"})
    private Integer servicePackage;

    public ValidityPrimaryKey() { }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}


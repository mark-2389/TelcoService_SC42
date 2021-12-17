package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;

public class ValidityPrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private Integer id;

    @SuppressWarnings("unused")
    private Integer packageId;

    public ValidityPrimaryKey(int id, int packageId) {
        this.id = id;
        this.packageId = packageId;
    }

    public ValidityPrimaryKey() {

    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

}


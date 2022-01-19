package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;

public class OrderServiceSchedulePrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"unused"})
    private String client;

    @SuppressWarnings({"unused"})
    private Integer order;

    @SuppressWarnings({"unused"})
    private Integer service;

    public OrderServiceSchedulePrimaryKey() { }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

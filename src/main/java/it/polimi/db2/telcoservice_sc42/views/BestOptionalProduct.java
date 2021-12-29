package it.polimi.db2.telcoservice_sc42.views;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "best_optional_product")
@NamedQuery(name = "BestOptionalProduct.get", query = "SELECT op FROM BestOptionalProduct op")
@NamedQuery(name = "BestOptionalProduct.getNamed", query = "SELECT op.optionalProductId, o.name, op.valueOfSales FROM BestOptionalProduct op JOIN OptionalProduct o ON op.optionalProductId = o.id")
public class BestOptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Integer optionalProductId;

    @Column(name = "value_of_sales")
    private Integer valueOfSales;

    public Integer getOptionalProductId() {
        return optionalProductId;
    }

    public Integer getValueOfSales() {
        return valueOfSales;
    }

    @Override
    public String toString() {
        return "optionalProductId: " + optionalProductId +
                ", valueOfSales: " + valueOfSales;
    }
}

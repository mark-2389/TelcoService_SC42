package it.polimi.db2.telcoservice_sc42.views;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "average_opproducts_per_servpackage")
@NamedQuery(name = "AverageOptionalProductsPerPackage.all", query = "SELECT aop FROM AverageOptionalProductsPerPackage aop")
public class AverageOptionalProductsPerPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id")
    private Integer packageId;

    @Column(name = "average_products")
    private Integer averageProducts;

    public Integer getPackageId() {
        return packageId;
    }

    public Integer getAverageProducts() {
        return averageProducts;
    }
}

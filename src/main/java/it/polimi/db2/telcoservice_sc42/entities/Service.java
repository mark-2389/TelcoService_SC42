package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "service")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(
        name="TYPE",
        discriminatorType = DiscriminatorType.STRING
)
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(columnDefinition = "ENUM('FIXED_PHONE', 'MOBILE_PHONE', 'FIXED_INTERNET', 'MOBILE_INTERNET')")
    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date")
    private Date expirationDate;

    @ManyToMany
    @JoinTable
            (name = "service_composition",
                    joinColumns = @JoinColumn(name="SERVICE_ID"),
                    inverseJoinColumns = @JoinColumn(name="PACKAGE_ID"))
    private List<ServicePackage> packages;

    @ManyToMany
    @JoinTable
            (name = "service_schedule",
                    joinColumns = @JoinColumn(name="SERVICE_ID"),
                    inverseJoinColumns = @JoinColumn(name="USERNAME"))
    private List<Client> clients;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<ServicePackage> getPackages() {
        return packages;
    }

    public void setPackages(List<ServicePackage> packages) {
        this.packages = packages;
    }


    public void addPackage(ServicePackage servicePackage) {
        getPackages().add(servicePackage);
    }

    public void remove(ServicePackage servicePackage) {
        getPackages().remove(servicePackage);
    }
}

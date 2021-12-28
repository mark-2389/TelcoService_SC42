package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
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
// @DiscriminatorFormula(
//         "CASE WHEN `type` = 'FIXED_PHONE' THEN 'FIXED_PHONE' " +
//              "WHEN `type` = 'MOBILE_PHONE' THEN 'MOBILE_PHONE' " +
//              "WHEN `type` = 'MOBILE_INTERNET' OR type = 'FIXED_INTERNET' THEN 'INTERNET' end"
// )
@DiscriminatorValue("FIXED_PHONE")
@NamedQuery(name = "Service.valid", query = "SELECT s FROM Service s WHERE ( s.expirationDate = NULL OR s.expirationDate >= current_date )")
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "ENUM('FIXED_PHONE', 'MOBILE_PHONE', 'FIXED_INTERNET', 'MOBILE_INTERNET')")
    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @ManyToMany
    @JoinTable(
            name = "service_composition",
                    joinColumns = @JoinColumn(name="SERVICE_ID"),
                    inverseJoinColumns = @JoinColumn(name="PACKAGE_ID"))
    private List<ServicePackage> packages;

    @ManyToMany
    @JoinTable(
            name = "service_schedule",
                    joinColumns = @JoinColumn(name="SERVICE_ID"),
                    inverseJoinColumns = @JoinColumn(name="USERNAME"))
    private List<Client> clients;

    public Service() {

    }

    public Service(ServiceType type, Date expirationDate) {
        this.type = type;
        this.expirationDate = expirationDate;
    }

    public String toString() {
        return this.type.description() + " - " + this.expirationDate;
    }

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

    public void removePackage(ServicePackage servicePackage) {
        getPackages().remove(servicePackage);
    }
}

package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.*;

@Entity
@Table(name = "service")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(
        name="type",
        discriminatorType = DiscriminatorType.STRING
)
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String type;

    @ManyToMany
    @JoinTable
            (name = "servicecomposition",
                    joinColumns = @JoinColumn(name="serviceid"),
                    inverseJoinColumns = @JoinColumn(name="packageid"))
    private Collection<ServicePackage> packages;

    @ManyToMany
    @JoinTable
            (name = "serviceschedule",
                    joinColumns = @JoinColumn(name="serviceid"),
                    inverseJoinColumns = @JoinColumn(name="username"))
    private Collection<Client> clients;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<Client> getClients() {
        return clients;
    }


}

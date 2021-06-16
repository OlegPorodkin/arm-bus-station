package ru.porodkin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Counterpart.
 */
@Entity
@Table(name = "counterpart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Counterpart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "tin")
    private String tin;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "egis_otb_rf")
    private String egis_otb_rf;

    @Column(name = "tax_system")
    private String taxSystem;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "counterpart")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "driver", "counterpart", "route" }, allowSetters = true)
    private Set<Bus> buses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Counterpart id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Counterpart uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public Counterpart name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Counterpart shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTin() {
        return this.tin;
    }

    public Counterpart tin(String tin) {
        this.tin = tin;
        return this;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getOgrn() {
        return this.ogrn;
    }

    public Counterpart ogrn(String ogrn) {
        this.ogrn = ogrn;
        return this;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getEgis_otb_rf() {
        return this.egis_otb_rf;
    }

    public Counterpart egis_otb_rf(String egis_otb_rf) {
        this.egis_otb_rf = egis_otb_rf;
        return this;
    }

    public void setEgis_otb_rf(String egis_otb_rf) {
        this.egis_otb_rf = egis_otb_rf;
    }

    public String getTaxSystem() {
        return this.taxSystem;
    }

    public Counterpart taxSystem(String taxSystem) {
        this.taxSystem = taxSystem;
        return this;
    }

    public void setTaxSystem(String taxSystem) {
        this.taxSystem = taxSystem;
    }

    public String getAddress() {
        return this.address;
    }

    public Counterpart address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return this.description;
    }

    public Counterpart description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return this.country;
    }

    public Counterpart country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Bus> getBuses() {
        return this.buses;
    }

    public Counterpart buses(Set<Bus> buses) {
        this.setBuses(buses);
        return this;
    }

    public Counterpart addBus(Bus bus) {
        this.buses.add(bus);
        bus.setCounterpart(this);
        return this;
    }

    public Counterpart removeBus(Bus bus) {
        this.buses.remove(bus);
        bus.setCounterpart(null);
        return this;
    }

    public void setBuses(Set<Bus> buses) {
        if (this.buses != null) {
            this.buses.forEach(i -> i.setCounterpart(null));
        }
        if (buses != null) {
            buses.forEach(i -> i.setCounterpart(this));
        }
        this.buses = buses;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Counterpart)) {
            return false;
        }
        return id != null && id.equals(((Counterpart) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Counterpart{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", tin='" + getTin() + "'" +
            ", ogrn='" + getOgrn() + "'" +
            ", egis_otb_rf='" + getEgis_otb_rf() + "'" +
            ", taxSystem='" + getTaxSystem() + "'" +
            ", address='" + getAddress() + "'" +
            ", description='" + getDescription() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}

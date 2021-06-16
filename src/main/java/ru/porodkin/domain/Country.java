package ru.porodkin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "countryOfLocation" }, allowSetters = true)
    private Set<Region> regions = new HashSet<>();

    @OneToMany(mappedBy = "countryOfLocation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "countryOfLocation" }, allowSetters = true)
    private Set<Region> countryOfLocations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Country uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public Country name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Region> getRegions() {
        return this.regions;
    }

    public Country regions(Set<Region> regions) {
        this.setRegions(regions);
        return this;
    }

    public Country addRegions(Region region) {
        this.regions.add(region);
        region.setCountry(this);
        return this;
    }

    public Country removeRegions(Region region) {
        this.regions.remove(region);
        region.setCountry(null);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        if (this.regions != null) {
            this.regions.forEach(i -> i.setCountry(null));
        }
        if (regions != null) {
            regions.forEach(i -> i.setCountry(this));
        }
        this.regions = regions;
    }

    public Set<Region> getCountryOfLocations() {
        return this.countryOfLocations;
    }

    public Country countryOfLocations(Set<Region> regions) {
        this.setCountryOfLocations(regions);
        return this;
    }

    public Country addCountryOfLocations(Region region) {
        this.countryOfLocations.add(region);
        region.setCountryOfLocation(this);
        return this;
    }

    public Country removeCountryOfLocations(Region region) {
        this.countryOfLocations.remove(region);
        region.setCountryOfLocation(null);
        return this;
    }

    public void setCountryOfLocations(Set<Region> regions) {
        if (this.countryOfLocations != null) {
            this.countryOfLocations.forEach(i -> i.setCountryOfLocation(null));
        }
        if (regions != null) {
            regions.forEach(i -> i.setCountryOfLocation(this));
        }
        this.countryOfLocations = regions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}

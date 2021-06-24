package ru.porodkin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Station.
 */
@Entity
@Table(name = "station")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "okato")
    private Integer okato;

    @Column(name = "street")
    private String street;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "distance")
    private Integer distance;

    @JsonIgnoreProperties(value = { "nextStation", "typeObject", "region" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Station nextStation;

    @ManyToOne
    private TypeObject typeObject;

    @ManyToOne
    @JsonIgnoreProperties(value = { "country", "countryOfLocation" }, allowSetters = true)
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Station uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public Station name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Station description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOkato() {
        return this.okato;
    }

    public Station okato(Integer okato) {
        this.okato = okato;
        return this;
    }

    public void setOkato(Integer okato) {
        this.okato = okato;
    }

    public String getStreet() {
        return this.street;
    }

    public Station street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public Station longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public Station latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Integer getDistance() {
        return this.distance;
    }

    public Station distance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Station getNextStation() {
        return this.nextStation;
    }

    public Station nextStation(Station station) {
        this.setNextStation(station);
        return this;
    }

    public void setNextStation(Station station) {
        this.nextStation = station;
    }

    public TypeObject getTypeObject() {
        return this.typeObject;
    }

    public Station typeObject(TypeObject typeObject) {
        this.setTypeObject(typeObject);
        return this;
    }

    public void setTypeObject(TypeObject typeObject) {
        this.typeObject = typeObject;
    }

    public Region getRegion() {
        return this.region;
    }

    public Station region(Region region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        return id != null && id.equals(((Station) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Station{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", okato=" + getOkato() +
            ", street='" + getStreet() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", distance=" + getDistance() +
            "}";
    }
}

package ru.porodkin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bus.
 */
@Entity
@Table(name = "bus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "number")
    private String number;

    @Column(name = "description")
    private String description;

    @Column(name = "passenger_places")
    private Integer passengerPlaces;

    @ManyToOne
    @JsonIgnoreProperties(value = { "buses" }, allowSetters = true)
    private Driver driver;

    @ManyToOne
    @JsonIgnoreProperties(value = { "buses" }, allowSetters = true)
    private Counterpart counterpart;

    @JsonIgnoreProperties(value = { "bus" }, allowSetters = true)
    @OneToOne(mappedBy = "bus")
    private Route route;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bus id(Long id) {
        this.id = id;
        return this;
    }

    public String getModel() {
        return this.model;
    }

    public Bus model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return this.number;
    }

    public Bus number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    public Bus description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPassengerPlaces() {
        return this.passengerPlaces;
    }

    public Bus passengerPlaces(Integer passengerPlaces) {
        this.passengerPlaces = passengerPlaces;
        return this;
    }

    public void setPassengerPlaces(Integer passengerPlaces) {
        this.passengerPlaces = passengerPlaces;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public Bus driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Counterpart getCounterpart() {
        return this.counterpart;
    }

    public Bus counterpart(Counterpart counterpart) {
        this.setCounterpart(counterpart);
        return this;
    }

    public void setCounterpart(Counterpart counterpart) {
        this.counterpart = counterpart;
    }

    public Route getRoute() {
        return this.route;
    }

    public Bus route(Route route) {
        this.setRoute(route);
        return this;
    }

    public void setRoute(Route route) {
        if (this.route != null) {
            this.route.setBus(null);
        }
        if (route != null) {
            route.setBus(this);
        }
        this.route = route;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bus)) {
            return false;
        }
        return id != null && id.equals(((Bus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bus{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", number='" + getNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", passengerPlaces=" + getPassengerPlaces() +
            "}";
    }
}

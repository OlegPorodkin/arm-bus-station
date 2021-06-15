package ru.porodkin.domain;

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

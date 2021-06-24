package ru.porodkin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "place")
    private Integer place;

    @Column(name = "serial")
    private Integer serial;

    @Column(name = "number")
    private Integer number;

    @Column(name = "type")
    private String type;

    @Column(name = "date_departure")
    private LocalDate dateDeparture;

    @Column(name = "price")
    private Double price;

    @JsonIgnoreProperties(value = { "route", "passport", "ticket" }, allowSetters = true)
    @OneToOne(mappedBy = "ticket")
    private Passenger passenger;

    @JsonIgnoreProperties(value = { "nextStation", "typeObject", "region" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Station departure;

    @JsonIgnoreProperties(value = { "nextStation", "typeObject", "region" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Station destination;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Ticket uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPlace() {
        return this.place;
    }

    public Ticket place(Integer place) {
        this.place = place;
        return this;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getSerial() {
        return this.serial;
    }

    public Ticket serial(Integer serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getNumber() {
        return this.number;
    }

    public Ticket number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return this.type;
    }

    public Ticket type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateDeparture() {
        return this.dateDeparture;
    }

    public Ticket dateDeparture(LocalDate dateDeparture) {
        this.dateDeparture = dateDeparture;
        return this;
    }

    public void setDateDeparture(LocalDate dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public Double getPrice() {
        return this.price;
    }

    public Ticket price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Passenger getPassenger() {
        return this.passenger;
    }

    public Ticket passenger(Passenger passenger) {
        this.setPassenger(passenger);
        return this;
    }

    public void setPassenger(Passenger passenger) {
        if (this.passenger != null) {
            this.passenger.setTicket(null);
        }
        if (passenger != null) {
            passenger.setTicket(this);
        }
        this.passenger = passenger;
    }

    public Station getDeparture() {
        return this.departure;
    }

    public Ticket departure(Station station) {
        this.setDeparture(station);
        return this;
    }

    public void setDeparture(Station station) {
        this.departure = station;
    }

    public Station getDestination() {
        return this.destination;
    }

    public Ticket destination(Station station) {
        this.setDestination(station);
        return this;
    }

    public void setDestination(Station station) {
        this.destination = station;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id != null && id.equals(((Ticket) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", place=" + getPlace() +
            ", serial=" + getSerial() +
            ", number=" + getNumber() +
            ", type='" + getType() + "'" +
            ", dateDeparture='" + getDateDeparture() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}

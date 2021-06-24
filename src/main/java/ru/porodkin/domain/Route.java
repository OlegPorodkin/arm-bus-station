package ru.porodkin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Route.
 */
@Entity
@Table(name = "route")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "route.all", includeAllAttributes = true)
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "planned_arrival")
    private ZonedDateTime plannedArrival;

    @Column(name = "planned_departure")
    private ZonedDateTime plannedDeparture;

    @Column(name = "actual_arrival")
    private ZonedDateTime actualArrival;

    @Column(name = "actual_departure")
    private ZonedDateTime actualDeparture;

    @Column(name = "time_registration")
    private Instant timeRegistration;

    @Column(name = "platform")
    private String platform;

    @Column(name = "rout_status")
    private String routStatus;

    @Column(name = "description")
    private String description;

    //    @JsonIgnoreProperties(value = { "driver", "counterpart", "route" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    //    @JsonIgnoreProperties(value = { "nextStation", "typeObject", "region" }, allowSetters = true)
    private Station station;

    @OneToMany(mappedBy = "route")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    //    @JsonIgnoreProperties(value = { "route", "passport", "ticket" }, allowSetters = true)
    private Set<Passenger> passengers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Route id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Route uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getPlannedArrival() {
        return this.plannedArrival;
    }

    public Route plannedArrival(ZonedDateTime plannedArrival) {
        this.plannedArrival = plannedArrival;
        return this;
    }

    public void setPlannedArrival(ZonedDateTime plannedArrival) {
        this.plannedArrival = plannedArrival;
    }

    public ZonedDateTime getPlannedDeparture() {
        return this.plannedDeparture;
    }

    public Route plannedDeparture(ZonedDateTime plannedDeparture) {
        this.plannedDeparture = plannedDeparture;
        return this;
    }

    public void setPlannedDeparture(ZonedDateTime plannedDeparture) {
        this.plannedDeparture = plannedDeparture;
    }

    public ZonedDateTime getActualArrival() {
        return this.actualArrival;
    }

    public Route actualArrival(ZonedDateTime actualArrival) {
        this.actualArrival = actualArrival;
        return this;
    }

    public void setActualArrival(ZonedDateTime actualArrival) {
        this.actualArrival = actualArrival;
    }

    public ZonedDateTime getActualDeparture() {
        return this.actualDeparture;
    }

    public Route actualDeparture(ZonedDateTime actualDeparture) {
        this.actualDeparture = actualDeparture;
        return this;
    }

    public void setActualDeparture(ZonedDateTime actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public Instant getTimeRegistration() {
        return this.timeRegistration;
    }

    public Route timeRegistration(Instant timeRegistration) {
        this.timeRegistration = timeRegistration;
        return this;
    }

    public void setTimeRegistration(Instant timeRegistration) {
        this.timeRegistration = timeRegistration;
    }

    public String getPlatform() {
        return this.platform;
    }

    public Route platform(String platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRoutStatus() {
        return this.routStatus;
    }

    public Route routStatus(String routStatus) {
        this.routStatus = routStatus;
        return this;
    }

    public void setRoutStatus(String routStatus) {
        this.routStatus = routStatus;
    }

    public String getDescription() {
        return this.description;
    }

    public Route description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bus getBus() {
        return this.bus;
    }

    public Route bus(Bus bus) {
        this.setBus(bus);
        return this;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Station getStation() {
        return this.station;
    }

    public Route station(Station station) {
        this.setStation(station);
        return this;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Set<Passenger> getPassengers() {
        return this.passengers;
    }

    public Route passengers(Set<Passenger> passengers) {
        this.setPassengers(passengers);
        return this;
    }

    public Route addPassengers(Passenger passenger) {
        this.passengers.add(passenger);
        passenger.setRoute(this);
        return this;
    }

    public Route removePassengers(Passenger passenger) {
        this.passengers.remove(passenger);
        passenger.setRoute(null);
        return this;
    }

    public void setPassengers(Set<Passenger> passengers) {
        if (this.passengers != null) {
            this.passengers.forEach(i -> i.setRoute(null));
        }
        if (passengers != null) {
            passengers.forEach(i -> i.setRoute(this));
        }
        this.passengers = passengers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Route)) {
            return false;
        }
        return id != null && id.equals(((Route) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Route{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", plannedArrival='" + getPlannedArrival() + "'" +
            ", plannedDeparture='" + getPlannedDeparture() + "'" +
            ", actualArrival='" + getActualArrival() + "'" +
            ", actualDeparture='" + getActualDeparture() + "'" +
            ", timeRegistration='" + getTimeRegistration() + "'" +
            ", platform='" + getPlatform() + "'" +
            ", routStatus='" + getRoutStatus() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

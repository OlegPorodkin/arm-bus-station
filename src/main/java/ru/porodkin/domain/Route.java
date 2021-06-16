package ru.porodkin.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Route.
 */
@Entity
@Table(name = "route")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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

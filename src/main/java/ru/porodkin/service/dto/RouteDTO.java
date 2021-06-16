package ru.porodkin.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link ru.porodkin.domain.Route} entity.
 */
public class RouteDTO implements Serializable {

    private Long id;

    private String uuid;

    private ZonedDateTime plannedArrival;

    private ZonedDateTime plannedDeparture;

    private ZonedDateTime actualArrival;

    private ZonedDateTime actualDeparture;

    private Instant timeRegistration;

    private String platform;

    private String routStatus;

    private String description;

    private BusDTO bus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getPlannedArrival() {
        return plannedArrival;
    }

    public void setPlannedArrival(ZonedDateTime plannedArrival) {
        this.plannedArrival = plannedArrival;
    }

    public ZonedDateTime getPlannedDeparture() {
        return plannedDeparture;
    }

    public void setPlannedDeparture(ZonedDateTime plannedDeparture) {
        this.plannedDeparture = plannedDeparture;
    }

    public ZonedDateTime getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(ZonedDateTime actualArrival) {
        this.actualArrival = actualArrival;
    }

    public ZonedDateTime getActualDeparture() {
        return actualDeparture;
    }

    public void setActualDeparture(ZonedDateTime actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public Instant getTimeRegistration() {
        return timeRegistration;
    }

    public void setTimeRegistration(Instant timeRegistration) {
        this.timeRegistration = timeRegistration;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRoutStatus() {
        return routStatus;
    }

    public void setRoutStatus(String routStatus) {
        this.routStatus = routStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BusDTO getBus() {
        return bus;
    }

    public void setBus(BusDTO bus) {
        this.bus = bus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RouteDTO)) {
            return false;
        }

        RouteDTO routeDTO = (RouteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, routeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RouteDTO{" +
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
            ", bus=" + getBus() +
            "}";
    }
}

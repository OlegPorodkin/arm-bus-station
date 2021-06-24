package ru.porodkin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link ru.porodkin.domain.Ticket} entity.
 */
public class TicketDTO implements Serializable {

    private Long id;

    private String uuid;

    private Integer place;

    private Integer serial;

    private Integer number;

    private String type;

    private LocalDate dateDeparture;

    private Double price;

    private StationDTO departure;

    private StationDTO destination;

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

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(LocalDate dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public StationDTO getDeparture() {
        return departure;
    }

    public void setDeparture(StationDTO departure) {
        this.departure = departure;
    }

    public StationDTO getDestination() {
        return destination;
    }

    public void setDestination(StationDTO destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketDTO)) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", place=" + getPlace() +
            ", serial=" + getSerial() +
            ", number=" + getNumber() +
            ", type='" + getType() + "'" +
            ", dateDeparture='" + getDateDeparture() + "'" +
            ", price=" + getPrice() +
            ", departure=" + getDeparture() +
            ", destination=" + getDestination() +
            "}";
    }
}

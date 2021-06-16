package ru.porodkin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ru.porodkin.domain.Bus} entity.
 */
public class BusDTO implements Serializable {

    private Long id;

    private String model;

    private String number;

    private String description;

    private Integer passengerPlaces;

    private DriverDTO driver;

    private CounterpartDTO counterpart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPassengerPlaces() {
        return passengerPlaces;
    }

    public void setPassengerPlaces(Integer passengerPlaces) {
        this.passengerPlaces = passengerPlaces;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverDTO driver) {
        this.driver = driver;
    }

    public CounterpartDTO getCounterpart() {
        return counterpart;
    }

    public void setCounterpart(CounterpartDTO counterpart) {
        this.counterpart = counterpart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusDTO)) {
            return false;
        }

        BusDTO busDTO = (BusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, busDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusDTO{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", number='" + getNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", passengerPlaces=" + getPassengerPlaces() +
            ", driver=" + getDriver() +
            ", counterpart=" + getCounterpart() +
            "}";
    }
}

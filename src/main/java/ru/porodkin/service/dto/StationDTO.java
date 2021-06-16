package ru.porodkin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ru.porodkin.domain.Station} entity.
 */
public class StationDTO implements Serializable {

    private Long id;

    private String uuid;

    private String name;

    private String description;

    private Integer okato;

    private String street;

    private Float longitude;

    private Float latitude;

    private Integer distance;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOkato() {
        return okato;
    }

    public void setOkato(Integer okato) {
        this.okato = okato;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StationDTO)) {
            return false;
        }

        StationDTO stationDTO = (StationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StationDTO{" +
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

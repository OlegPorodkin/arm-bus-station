package ru.porodkin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link ru.porodkin.domain.Passport} entity.
 */
public class PassportDTO implements Serializable {

    private Long id;

    private String uuid;

    private Integer serial;

    private Integer number;

    private LocalDate dateOfIssue;

    private String whoIssued;

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

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getWhoIssued() {
        return whoIssued;
    }

    public void setWhoIssued(String whoIssued) {
        this.whoIssued = whoIssued;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassportDTO)) {
            return false;
        }

        PassportDTO passportDTO = (PassportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, passportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PassportDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", serial=" + getSerial() +
            ", number=" + getNumber() +
            ", dateOfIssue='" + getDateOfIssue() + "'" +
            ", whoIssued='" + getWhoIssued() + "'" +
            "}";
    }
}

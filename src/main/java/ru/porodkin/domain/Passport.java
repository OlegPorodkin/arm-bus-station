package ru.porodkin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Passport.
 */
@Entity
@Table(name = "passport")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Passport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "serial")
    private Integer serial;

    @Column(name = "number")
    private Integer number;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "who_issued")
    private String whoIssued;

    @JsonIgnoreProperties(value = { "route", "passport", "ticket" }, allowSetters = true)
    @OneToOne(mappedBy = "passport")
    private Passenger passenger;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Passport id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Passport uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getSerial() {
        return this.serial;
    }

    public Passport serial(Integer serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getNumber() {
        return this.number;
    }

    public Passport number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDate getDateOfIssue() {
        return this.dateOfIssue;
    }

    public Passport dateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getWhoIssued() {
        return this.whoIssued;
    }

    public Passport whoIssued(String whoIssued) {
        this.whoIssued = whoIssued;
        return this;
    }

    public void setWhoIssued(String whoIssued) {
        this.whoIssued = whoIssued;
    }

    public Passenger getPassenger() {
        return this.passenger;
    }

    public Passport passenger(Passenger passenger) {
        this.setPassenger(passenger);
        return this;
    }

    public void setPassenger(Passenger passenger) {
        if (this.passenger != null) {
            this.passenger.setPassport(null);
        }
        if (passenger != null) {
            passenger.setPassport(this);
        }
        this.passenger = passenger;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Passport)) {
            return false;
        }
        return id != null && id.equals(((Passport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Passport{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", serial=" + getSerial() +
            ", number=" + getNumber() +
            ", dateOfIssue='" + getDateOfIssue() + "'" +
            ", whoIssued='" + getWhoIssued() + "'" +
            "}";
    }
}

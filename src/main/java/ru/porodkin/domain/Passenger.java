package ru.porodkin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Passenger.
 */
@Entity
@Table(name = "passenger")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "passenger.all", includeAllAttributes = true)
public class Passenger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "boarding_status")
    private String boardingStatus;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone")
    private String phone;

    @Column(name = "citizenship")
    private String citizenship;

    @ManyToOne
    //    @JsonIgnoreProperties(value = { "bus", "station", "passengers" }, allowSetters = true)
    private Route route;

    //    @JsonIgnoreProperties(value = { "passenger" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Passport passport;

    //    @JsonIgnoreProperties(value = { "passenger", "departure", "destination" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Ticket ticket;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Passenger id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Passenger uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Passenger firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Passenger lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public Passenger patronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Passenger birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getBoardingStatus() {
        return this.boardingStatus;
    }

    public Passenger boardingStatus(String boardingStatus) {
        this.boardingStatus = boardingStatus;
        return this;
    }

    public void setBoardingStatus(String boardingStatus) {
        this.boardingStatus = boardingStatus;
    }

    public String getSex() {
        return this.sex;
    }

    public Passenger sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return this.phone;
    }

    public Passenger phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCitizenship() {
        return this.citizenship;
    }

    public Passenger citizenship(String citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Route getRoute() {
        return this.route;
    }

    public Passenger route(Route route) {
        this.setRoute(route);
        return this;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Passport getPassport() {
        return this.passport;
    }

    public Passenger passport(Passport passport) {
        this.setPassport(passport);
        return this;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public Passenger ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Passenger)) {
            return false;
        }
        return id != null && id.equals(((Passenger) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Passenger{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", patronymic='" + getPatronymic() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", boardingStatus='" + getBoardingStatus() + "'" +
            ", sex='" + getSex() + "'" +
            ", phone='" + getPhone() + "'" +
            ", citizenship='" + getCitizenship() + "'" +
            "}";
    }
}

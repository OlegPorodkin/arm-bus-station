package ru.porodkin.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ru.porodkin.domain.Bus} entity. This class is used
 * in {@link ru.porodkin.web.rest.BusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /buses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter model;

    private StringFilter number;

    private StringFilter description;

    private IntegerFilter passengerPlaces;

    private LongFilter driverId;

    private LongFilter counterpartId;

    private LongFilter routeId;

    public BusCriteria() {}

    public BusCriteria(BusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.model = other.model == null ? null : other.model.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.passengerPlaces = other.passengerPlaces == null ? null : other.passengerPlaces.copy();
        this.driverId = other.driverId == null ? null : other.driverId.copy();
        this.counterpartId = other.counterpartId == null ? null : other.counterpartId.copy();
        this.routeId = other.routeId == null ? null : other.routeId.copy();
    }

    @Override
    public BusCriteria copy() {
        return new BusCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getModel() {
        return model;
    }

    public StringFilter model() {
        if (model == null) {
            model = new StringFilter();
        }
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public StringFilter getNumber() {
        return number;
    }

    public StringFilter number() {
        if (number == null) {
            number = new StringFilter();
        }
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getPassengerPlaces() {
        return passengerPlaces;
    }

    public IntegerFilter passengerPlaces() {
        if (passengerPlaces == null) {
            passengerPlaces = new IntegerFilter();
        }
        return passengerPlaces;
    }

    public void setPassengerPlaces(IntegerFilter passengerPlaces) {
        this.passengerPlaces = passengerPlaces;
    }

    public LongFilter getDriverId() {
        return driverId;
    }

    public LongFilter driverId() {
        if (driverId == null) {
            driverId = new LongFilter();
        }
        return driverId;
    }

    public void setDriverId(LongFilter driverId) {
        this.driverId = driverId;
    }

    public LongFilter getCounterpartId() {
        return counterpartId;
    }

    public LongFilter counterpartId() {
        if (counterpartId == null) {
            counterpartId = new LongFilter();
        }
        return counterpartId;
    }

    public void setCounterpartId(LongFilter counterpartId) {
        this.counterpartId = counterpartId;
    }

    public LongFilter getRouteId() {
        return routeId;
    }

    public LongFilter routeId() {
        if (routeId == null) {
            routeId = new LongFilter();
        }
        return routeId;
    }

    public void setRouteId(LongFilter routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BusCriteria that = (BusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(model, that.model) &&
            Objects.equals(number, that.number) &&
            Objects.equals(description, that.description) &&
            Objects.equals(passengerPlaces, that.passengerPlaces) &&
            Objects.equals(driverId, that.driverId) &&
            Objects.equals(counterpartId, that.counterpartId) &&
            Objects.equals(routeId, that.routeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, number, description, passengerPlaces, driverId, counterpartId, routeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (model != null ? "model=" + model + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (passengerPlaces != null ? "passengerPlaces=" + passengerPlaces + ", " : "") +
            (driverId != null ? "driverId=" + driverId + ", " : "") +
            (counterpartId != null ? "counterpartId=" + counterpartId + ", " : "") +
            (routeId != null ? "routeId=" + routeId + ", " : "") +
            "}";
    }
}

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

    public BusCriteria() {}

    public BusCriteria(BusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.model = other.model == null ? null : other.model.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.passengerPlaces = other.passengerPlaces == null ? null : other.passengerPlaces.copy();
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
            Objects.equals(passengerPlaces, that.passengerPlaces)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, number, description, passengerPlaces);
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
            "}";
    }
}

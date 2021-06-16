package ru.porodkin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ru.porodkin.domain.Counterpart} entity.
 */
public class CounterpartDTO implements Serializable {

    private Long id;

    private String uuid;

    private String name;

    private String shortName;

    private String tin;

    private String ogrn;

    private String egis_otb_rf;

    private String taxSystem;

    private String address;

    private String description;

    private String country;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getEgis_otb_rf() {
        return egis_otb_rf;
    }

    public void setEgis_otb_rf(String egis_otb_rf) {
        this.egis_otb_rf = egis_otb_rf;
    }

    public String getTaxSystem() {
        return taxSystem;
    }

    public void setTaxSystem(String taxSystem) {
        this.taxSystem = taxSystem;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CounterpartDTO)) {
            return false;
        }

        CounterpartDTO counterpartDTO = (CounterpartDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, counterpartDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CounterpartDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", tin='" + getTin() + "'" +
            ", ogrn='" + getOgrn() + "'" +
            ", egis_otb_rf='" + getEgis_otb_rf() + "'" +
            ", taxSystem='" + getTaxSystem() + "'" +
            ", address='" + getAddress() + "'" +
            ", description='" + getDescription() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}

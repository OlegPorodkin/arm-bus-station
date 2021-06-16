package ru.porodkin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ru.porodkin.domain.TypeObject} entity.
 */
public class TypeObjectDTO implements Serializable {

    private Long id;

    private String uuid;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeObjectDTO)) {
            return false;
        }

        TypeObjectDTO typeObjectDTO = (TypeObjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeObjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeObjectDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}

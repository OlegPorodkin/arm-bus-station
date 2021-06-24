package ru.porodkin.domain;

public enum TypeOfRegion {
    REGION("Область"),
    KRAY("Край");

    private final String description;

    TypeOfRegion(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

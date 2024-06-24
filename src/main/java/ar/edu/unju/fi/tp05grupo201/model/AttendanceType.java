package ar.edu.unju.fi.tp05grupo201.model;

import lombok.Getter;

@Getter
public enum AttendanceType {
    VIRTUAL ("Virtual"),
    IN_PERSON ("Presencial");

    private final String value;

    AttendanceType(String value) {
        this.value = value;
    }
}

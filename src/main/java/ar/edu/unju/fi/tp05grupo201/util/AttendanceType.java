package ar.edu.unju.fi.tp05grupo201.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttendanceType {
    VIRTUAL ("Virtual"),
    IN_PERSON ("Presencial");

    private final String value;

    // AttendanceType(final String value) {
    //     this.value = value;
    // }

    // public String getValue() {
    //     return value;
    // }
}

package ar.edu.unju.fi.tp05grupo201.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Component
public class CareerDto {

    private long id;

    @Column(name = "code")
    @NotBlank(message = "{career.code.not-blank}")
    @Pattern(
        regexp = "^$|[0-9]+",
        message = "{career.code.pattern}"
    )
    private String code;

    @Column(name = "name")
    @NotBlank(message = "{career.name.not-blank}")
    @Pattern(
        regexp = "^$|[a-z A-Z]+",
        message = "{career.name.pattern}"
    )
    private String name;

    @Column(name = "duration")
    @NotNull(message = "{career.duration.not-null}")
    @Positive(message = "{career.duration.only-positive}")
    private int duration;

    private List<StudentDto> students = new ArrayList<>();

    private List<SubjectDto> subjects = new ArrayList<>();

    private boolean state = true;
}
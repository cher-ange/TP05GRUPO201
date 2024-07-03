package ar.edu.unju.fi.tp05grupo201.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Component
public class SubjectDto {

    private long id;

    @NotBlank(message = "{subject.code.not-blank}")
    @Pattern(
            regexp = "^$|[0-9]+",
            message = "{subject.code.pattern}"
    )
    private String code;

    @NotBlank(message = "{subject.name.not-blank}")
    @Pattern(
            regexp = "^$|[a-z A-Z]+",
            message = "{subject.name.pattern}"
    )
    private String name;

    @NotBlank(message = "{subject.course.not-blank}")
    @Pattern(
            regexp = "^$|[a-z A-Z 0-9]+",
            message = "{subject.course.pattern}"
    )
    private String course;

    @NotNull(message = "{subject.duration-in-hours.not-null}")
    @Positive(message = "{subject.duration-in-hours.only-positive}")
    private int durationInHours;

    private String attendanceType;

    private Set<StudentDto> students = new HashSet<>();
    
    private TeacherDto teacher;

    private CareerDto career;

    private boolean state = true;
}

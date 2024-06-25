package ar.edu.unju.fi.tp05grupo201.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class TeacherDto {

    private long id;

    @NotBlank(message = "{teacher.file.not-blank}")
    @Pattern(
        regexp = "^$|[0-9]+",
        message = "{teacher.file.pattern}"
    )
    private String file;

    @NotBlank(message = "{teacher.name.not-blank}")
    @Pattern(
        regexp = "^$|[a-z A-Z]+",
        message = "{teacher.name.pattern}"
    )
    private String name;

    @NotBlank(message = "{teacher.last-name.not-blank}")
    @Pattern(
        regexp = "^$|[a-z A-Z]+",
        message = "{teacher.last-name.pattern}"
    )
    private String lastName;

    @NotBlank(message = "{teacher.email.not-blank}")
    @Pattern(
        regexp = "^$|[a-zA-Z0-9_]+@[a-zA-Z]+[.][a-z]{2,3}",
        message = "{student.email.pattern}"
    )
    private String email;

    @NotBlank(message = "{teacher.phone.not-blank}")
    @Pattern(
        regexp = "^$|[0-9]+",
        message = "{teacher.phone.pattern}"
    )
    @Size(min = 10, message = "{teacher.phone.size}")
    private String phone;

    private boolean state = true;
}

package ar.edu.unju.fi.tp05grupo201.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class StudentDto {
    @NotNull
	private Long id;

    @NotBlank(message = "{student.person-id.not-blank}")
    @Pattern(
            regexp = "^$|[0-9]+",
            message = "{student.person-id.pattern}"
    )
    @Size(min = 7, message = "{student.person-id.size}")
    private String personId;

    @NotBlank(message = "{student.name.not-blank}")
    @Pattern(
            regexp = "^$|[a-z A-Z]+",
            message = "{student.name.pattern}"
    )
    private String name;

    @NotBlank(message = "{student.last-name.not-blank}")
    @Pattern(
            regexp = "^$|[a-z A-Z]+",
            message = "{student.last-name.pattern}"
    )
    private String lastName;

    @NotBlank(message = "{student.email.not-blank}")
    @Pattern(
            regexp = "^$|[a-zA-Z0-9_]+@[a-zA-Z]+[.][a-z]{2,3}",
            message = "{student.email.pattern}"
    )
    private String email;

    @NotBlank(message = "{student.phone.not-blank}")
    @Pattern(
            regexp = "^$|[0-9]+",
            message = "{student.phone.pattern}"
    )
    @Size(min = 10, message = "{student.phone.size}")
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{student.birthdate.not-null}")
    @Past(message = "{student.birthdate.past}")
    private LocalDate birthdate;

    @NotBlank(message = "{student.address.not-blank}")
    private String address;

    @NotBlank(message = "{student.university-record.not-blank}")
    private String universityId;

    private boolean state = true;

    private CareerDto career;
}

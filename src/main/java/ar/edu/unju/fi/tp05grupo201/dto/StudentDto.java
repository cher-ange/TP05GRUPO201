package ar.edu.unju.fi.tp05grupo201.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class StudentDto {
	
	private Long id;
    private String personId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String address;
    private String universityId;
    private Boolean state;
}

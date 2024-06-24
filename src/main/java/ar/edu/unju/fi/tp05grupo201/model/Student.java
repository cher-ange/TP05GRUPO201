package ar.edu.unju.fi.tp05grupo201.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Component
@Entity
public class Student {
	
    @Id
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

package ar.edu.unju.fi.tp05grupo201.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Entity(name = "Student")
@Table(name = "student")
public class Student {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private String address;
    private String universityId;
    private Boolean state;

    @ManyToOne(targetEntity = Career.class)
    private Career career;
}

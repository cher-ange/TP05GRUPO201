package ar.edu.unju.fi.tp05grupo201.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(targetEntity = Subject.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects = new ArrayList<>();


}

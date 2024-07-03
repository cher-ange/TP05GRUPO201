package ar.edu.unju.fi.tp05grupo201.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Student")
@Table(name = "student")
public class Student {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "person_id")
    private String personId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "address")
    private String address;

    @Column(name = "university_record")
    private String universityRecord;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();

   @ManyToOne(fetch = FetchType.LAZY)
   private Career career;

    @Column(name = "state")
    private Boolean state = true;

    // Synchronize relationships
    public void addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.getStudents().add(this);
    }

    public void removeSubject(Subject subject) {
        subject.getStudents().remove(this);
        this.subjects.remove(subject);
    }
}

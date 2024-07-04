package ar.edu.unju.fi.tp05grupo201.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import ar.edu.unju.fi.tp05grupo201.util.AttendanceType;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Subject")
@Table(name = "subject")
@Component
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "course")
    private String course;

    @Column(name = "duration_in_hours")
    private Integer durationInHours;

    @Column(name = "attendance_type")
    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;

    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

   @ManyToOne(fetch = FetchType.LAZY)
   private Career career;

    @Column(name = "state")
    private Boolean state = true;
}

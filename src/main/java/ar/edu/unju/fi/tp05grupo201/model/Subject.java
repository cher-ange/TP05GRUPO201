package ar.edu.unju.fi.tp05grupo201.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Subject")
@Table(name = "subject")
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
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(mappedBy = "subjects")
    private Set<Career> careers = new HashSet<>();

    @Column(name = "state")
    private Boolean state = true;
}

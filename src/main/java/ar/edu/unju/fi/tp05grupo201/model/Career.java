package ar.edu.unju.fi.tp05grupo201.model;



import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Career")
@Table(name = "career")
@Component
public class Career {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
        name = "career_subject",
        joinColumns = @JoinColumn(name = "career_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "career", fetch = FetchType.LAZY, targetEntity = Student.class)
    private List<Student> students;


    @Column(name = "state")
    private Boolean state = true;

    // Syncronize relantionships
    public void addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.getCareers().add(this);
    }

    public void removeSubject(Subject subject) {
        subject.getCareers().remove(this);
        this.subjects.remove(subject);
    }
}
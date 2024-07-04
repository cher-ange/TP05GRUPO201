package ar.edu.unju.fi.tp05grupo201.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
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

    @OneToMany(
          mappedBy = "career",
          cascade = CascadeType.ALL
    )
    private List<Student> students = new ArrayList<>();
 
    @OneToMany(
          mappedBy = "career",
          cascade = CascadeType.ALL
    )
    private List<Subject> subjects = new ArrayList<>();

    @Column(name = "state")
    private Boolean state = true;

    // Synchronize relationships
    public void addStudent(Student student) {
       this.students.add(student);
       student.setCareer(this);
    }

    public void removeStudent(Student student) {
       student.setCareer(null);
       this.students.remove(student);
    }

    public void addSubject(Subject subject) {
      this.subjects.add(subject);
      subject.setCareer(this);
   }

   public void removeSubject(Subject subject) {
      subject.setCareer(null);
      this.subjects.remove(subject);
   }
}
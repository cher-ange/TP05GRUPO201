package ar.edu.unju.fi.tp05grupo201.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp05grupo201.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByPersonId(String personId);
    List<Student> findStudentsByState(boolean state);
}

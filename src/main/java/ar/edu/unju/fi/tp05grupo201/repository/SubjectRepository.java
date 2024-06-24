package ar.edu.unju.fi.tp05grupo201.repository;

import ar.edu.unju.fi.tp05grupo201.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findSubjectByCode(String code);

    List<Subject> findSubjectsByState(boolean state);
}

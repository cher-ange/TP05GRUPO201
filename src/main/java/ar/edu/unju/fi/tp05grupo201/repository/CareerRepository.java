package ar.edu.unju.fi.tp05grupo201.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unju.fi.tp05grupo201.model.Career;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    Optional<Career> findCareerByCode(String code);
    
    List<Career> findCareersByState(boolean state);

}
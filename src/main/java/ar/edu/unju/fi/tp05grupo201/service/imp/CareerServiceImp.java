package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.model.Career;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import ar.edu.unju.fi.tp05grupo201.model.Subject;
import ar.edu.unju.fi.tp05grupo201.repository.CareerRepository;
import ar.edu.unju.fi.tp05grupo201.repository.SubjectRepository;
import ar.edu.unju.fi.tp05grupo201.service.ICareerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Career service implementation
 */
@Service
@AllArgsConstructor
@Slf4j
public class CareerServiceImp implements ICareerService {

    /**
     * Dependencies
     */
    private final CareerRepository careerRepository;
    private final SubjectRepository subjectRepository;
    private final Career career;

    /**
     * Create a career
     * @return Career
     */
    @Override
    public Career createCareer() {
        log.info("Career created");
        return career;
    }

    /**
     * Add a career
     */
    @Override
    public void addCareer(Career career) {
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(career.getCode());

        if (optionalCareer.isPresent()) {
            log.info("Updating career");
            career.setId(optionalCareer.get().getId());
            career.setSubjects(optionalCareer.get().getSubjects());
        }

        log.info("Adding career");
        careerRepository.save(career);
    }

    /**
     * Get a career by id
     * @return Career
     */
    @Override
    public Career getCareerById(long id) {
        Optional<Career> optionalCareer = careerRepository.findById(id);
        
        if (optionalCareer.isEmpty()) {
            log.error("Career with id {} wasn't found", id);
            throw new NoSuchElementException(
                "Career with id " + id + "wasn't found"
            );
        }

        return optionalCareer.get();
    }

    /**
     * Get a career by code
     * @return Career
     */
    @Override
    public Career getCareerByCode(String code) {
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(code);

        if (optionalCareer.isEmpty()) {
            log.error("Career with code {} wasn't found", code);
            throw new NoSuchElementException(
                "Career with code: " + code + " wasn't found"
            );
        }

        return optionalCareer.get();
    }

    /**
     * Delete a career by code
     */
    @Override
    public void deleteCareer(long careerId) {
        Optional<Career> optionalCareer = careerRepository.findById(careerId);

        if (optionalCareer.isEmpty()) {
            log.error("Career with id {} wasn't found", careerId);
            throw new IllegalArgumentException(
                "Career with id " + careerId + " wasn't found"
            );
        }

        List<Student> listOfStudents = optionalCareer.get().getStudents();
        
        for (int i = 0; i < listOfStudents.size(); i++) {
            optionalCareer.get().removeStudent(listOfStudents.get(i));
        }

        optionalCareer.get().setState(false);
        careerRepository.save(optionalCareer.get());
    }

    /**
     * Get a list of careers by state
     */
    @Override
    public List<Career> getCareersByState(boolean state) {
        log.info("Retrieving careers");
        return careerRepository.findCareersByState(state);
    }

    /**
     * Add a subject to a career
     */
    @Override
    public void addSubjectToCareer(long careerId, long subjectId) {
        Optional<Career> optionalCareer = careerRepository.findById(careerId);
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);

        if (optionalCareer.isEmpty()) {
            log.error("Career with id {} wasn't found", careerId);
            throw new NoSuchElementException("Career with id " + careerId + " wasn't found");
        }

        if (optionalSubject.isEmpty()) {
            log.error("Subject with id {} wasn't found", subjectId);
            throw new NoSuchElementException("Subject with id " + subjectId + " wasn't found");
        }

        log.info(
            "Subject {} added to career {}",
            optionalSubject.get().getName(),
            optionalCareer.get().getName());

        optionalCareer.get().addSubject(optionalSubject.get());
        careerRepository.save(optionalCareer.get());
    }

    /**
     * Delete a subject from a career
     */
    @Override
    public void deleteSubjectFromCareer(long careerId, long subjectId) {
        Optional<Career> optionalCareer = careerRepository.findById(careerId);
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);

        if (optionalCareer.isEmpty()) {
            log.error("Career with id {} wasn't found", careerId);
            throw new NoSuchElementException(
                "Career with id " + careerId + " wasn't found"
            );
        }

        if (optionalSubject.isEmpty()) {
            log.error("Subject with id {} wasn't found", subjectId);
            throw new NoSuchElementException(
                "Subject with id " + subjectId + " wasn't found"
            );
        }

        optionalCareer.get().removeSubject(optionalSubject.get());
        careerRepository.save(optionalCareer.get());
    }
}

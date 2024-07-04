package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.mapper.CareerMapper;
import ar.edu.unju.fi.tp05grupo201.mapper.StudentMapper;
import ar.edu.unju.fi.tp05grupo201.mapper.SubjectMapper;
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
    private final CareerMapper careerMapper;
    private final SubjectMapper subjectMapper;
    private final StudentMapper studentMapper;
    private final CareerDto career;

    /**
     * Create a career
     * @return Career
     */
    @Override
    public CareerDto createCareer() {
        log.info("Career created");
        return careerDto;
    }

    /**
     * Add a career
     */
    @Override
    public void addCareer(CareerDto career) {
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(career.getCode());

        if (optionalCareer.isPresent()) {
            log.info("Updating career with id {}", career.getId());
            career.setId(optionalCareer.get().getId());

            career.setSubjects(
                optionalCareer.get().getSubjects().stream().map(subjectMapper::toDto).collect(Collectors.toList())
            );
            career.setStudents(
                optionalCareer.get().getStudents().stream().map(studentMapper::toDto).collect(Collectors.toList())
            );

        }

        log.info("Adding career {}", career);
        // Career dummy = careerMapper.toEntity(career);

        // dummy.setStudents(
        //     career.getStudents()
        //             .stream()
        //             .map(studentMapper::toEntity)
        //             .collect(Collectors.toList()));
        // dummy.setSubjects(
        //     career.getSubjects()
        //         .stream()
        //         .map(subjectMapper::toEntity)
        //         .collect(Collectors.toList()));
        
        // careerRepository.save(dummy);
        careerRepository.save(careerMapper.toEntity(career));
    }

    /**
     * Get a career by id
     * @return Career
     */
    @Override
    public CareerDto getCareerById(long id) {
        Optional<Career> optionalCareer = careerRepository.findById(id);

        if (optionalCareer.isEmpty()) {
            throw new NoSuchElementException(
                    "GET: Career with id " + id + "wasn't found"
            );
        }

        return careerMapper.toDto(optionalCareer.get());
    }

    /**
     * Get a career by code
     * @return Career
     */
    @Override
    public CareerDto getCareerByCode(String code) {
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(code);

        if (optionalCareer.isEmpty()) {
            throw new NoSuchElementException(
                    "GET: Career with code: " + code + " wasn't found"
            );
        }

        return careerMapper.toDto(optionalCareer.get());
    }

    /**
     * Get a list of careers by state
     */
    @Override
    public List<CareerDto> getCareersByState(boolean state) {
        log.info("Retrieving careers");

        List<CareerDto> listOfCareerDtos = new ArrayList<>();

        for (Career career : careerRepository.findCareersByState(state)) {
            CareerDto dummy = careerMapper.toDto(career);

            dummy.setStudents(
                career.getStudents()
                    .stream()
                    .map(studentMapper::toDto)
                    .collect(Collectors.toList()));
            dummy.setSubjects(
                career.getSubjects()
                    .stream()
                    .map(subjectMapper::toDto)
                    .collect(Collectors.toList()));

            listOfCareerDtos.add(dummy);
        }

        return listOfCareerDtos;
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

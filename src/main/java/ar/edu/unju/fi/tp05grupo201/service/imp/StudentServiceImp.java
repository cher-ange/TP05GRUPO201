package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.mapper.CareerMapper;
import ar.edu.unju.fi.tp05grupo201.mapper.StudentMapper;
import ar.edu.unju.fi.tp05grupo201.mapper.SubjectMapper;
import ar.edu.unju.fi.tp05grupo201.model.Career;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import ar.edu.unju.fi.tp05grupo201.model.Subject;
import ar.edu.unju.fi.tp05grupo201.repository.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.repository.CareerRepository;
import ar.edu.unju.fi.tp05grupo201.repository.StudentRepository;
import ar.edu.unju.fi.tp05grupo201.service.IStudentService;
import lombok.AllArgsConstructor;

/**
 * Student service implementation
 */
@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImp implements IStudentService {

    /**
     * Dependencies
     */
    private final StudentRepository studentRepository;
    private final CareerRepository careerRepository;
    private final StudentMapper studentMapper;
    private final SubjectMapper subjectMapper;
    private final CareerMapper careerMapper;
    private final StudentDto studentDto;
    private final SubjectRepository subjectRepository;

    /**
     * Create a student
     * @return Student
     */
    @Override
    public StudentDto createStudent() {
        return studentDto;
    }

    /**
     * Add a student
     * @return Student
     */
    @Override
    public void addStudent(StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findStudentByPersonId(studentDto.getPersonId());

        if (optionalStudent.isPresent()) {
            studentDto.setId(optionalStudent.get().getId());
            studentDto.setCareer(careerMapper.toDto(optionalStudent.get().getCareer()));
            studentDto.setSubjects(
                optionalStudent.get().getSubjects()
                    .stream()
                    .map(subjectMapper::toDto)
                    .collect(Collectors.toSet())
            );
       }

        studentRepository.save(studentMapper.toEntity(studentDto));
    }

    /**
     * Get a student by id
     * @return Student
     */
    @Override
    public StudentDto getStudentById(long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            log.error("Student with id {} wasn't found", studentId);
            throw new NoSuchElementException(
                    "Student with id " + studentId + " wasn't found"
            );
        }

        return studentMapper.toDto(optionalStudent.get());
    }

    /**
     * Get a student by person-id
     * @return Student
     */
    @Override
    public StudentDto getStudentByPersonId(String personId) {
        Optional<Student> optionalStudent = studentRepository.findStudentByPersonId(personId);

        if (optionalStudent.isEmpty()) {
            log.error("Student with person-id {} wasn't found", personId);
            throw new NoSuchElementException(
                    "Student with person-id " + personId + " wasn't found"
            );
        }

        return studentMapper.toDto(optionalStudent.get());
    }

    /**
     * Get a list of students by state
     */
    @Override
    public List<StudentDto> getStudentsByState(boolean state) {
        return studentRepository.findStudentsByState(state).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList()
        );
    }

    /**
     * Delete a student by person-id
     */
    @Override
    public void deleteStudent(long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            log.error("Student with student-id {} wasn't found", studentId);
            throw new NoSuchElementException(
                    "Student with student-id " + studentId + " wasn't found"
            );
        }

        optionalStudent.get().setState(false);
        studentRepository.save(optionalStudent.get());
    }

    @Override
    public void deleteSubjectFromStudent(long studentId, long subjectId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);

        if (optionalSubject.isEmpty()) {
            throw new NoSuchElementException(
                    "Subject with id " + subjectId + " wasn't found"
            );
        }

        if (optionalStudent.isEmpty()) {
            throw new NoSuchElementException(
                    "Student with student-id " + studentId + " wasn't found"
            );
        }

        optionalStudent.get().getSubjects().remove(optionalSubject.get());
        studentRepository.save(optionalStudent.get());
    }

    @Override
    public void deleteCareerFromStudent(long studentId, long careerId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Career> optionalCareer = careerRepository.findById(careerId);

        if (optionalStudent.isEmpty()) {
            throw new NoSuchElementException(
                    "Student with id " + studentId + " wasn't found"
            );
        }

        if (optionalCareer.isEmpty()) {
            throw new NoSuchElementException(
                    "Career with id " + careerId + " wasn't found"
            );
        }

        optionalStudent.get().setCareer(null);
        studentRepository.save(optionalStudent.get());
    }

    @Override
    public void addSubjectToStudent(long studentId, long subjectId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);

        if (optionalSubject.isPresent() && optionalStudent.isPresent()) {
            optionalStudent.get().addSubject(optionalSubject.get());

            log.info(
                    "Subject {} added to student {}",
                    optionalSubject.get().getName(),
                    optionalStudent.get().getName()
            );
            studentRepository.save(optionalStudent.get());
        }
    }

    @Override
    public void addCareerToStudent(long studentId, long careerId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Career> optionalCareer = careerRepository.findById(careerId);

        if (optionalCareer.isPresent() && optionalStudent.isPresent()) {
            optionalCareer.get().addStudent(optionalStudent.get());
            
            careerRepository.save(optionalCareer.get());
        }
    }
}

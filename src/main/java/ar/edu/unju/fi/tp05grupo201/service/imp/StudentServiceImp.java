package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import ar.edu.unju.fi.tp05grupo201.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.mapper.StudentMapperImpl;
import ar.edu.unju.fi.tp05grupo201.model.Student;
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
    private final StudentMapperImpl studentMapper;
    private final StudentDto studentDto;

    /**
     * Create a student
     * @return Student
     */
    @Override
    public StudentDto createStudent() {
        log.info("CREATE: Creando un Alumno");
        return studentDto;
    }

    /**
     * Add a student
     * @return Student
     */
    @Override
    public void addStudent(StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findStudentByPersonId(studentDto.getPersonId());

        /**
         * Condition to re-enable a Student that was 'deleted'
         */
        if (optionalStudent.isPresent()) {
            log.info("ADD old: {}", studentDto);
            studentDto.setId(optionalStudent.get().getId());
            studentDto.setState(true);
        }

        log.info("ADD new: {}", studentDto);
        studentRepository.save(studentMapper.toEntity(studentDto));
    }

    /**
     * Get a student by id
     * @return Student
     */
    @Override
    public StudentDto getStudentById(long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isEmpty()) {
            throw new NoSuchElementException(
                    "Student with id " + id + " wasn't found"
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
            throw new NoSuchElementException(
                    "Student with person-id " + personId + " wasn't found"
            );
        }

        return studentMapper.toDto(optionalStudent.get());
    }

    /**
     * Delete a student by person-id
     */
    @Override
    public void deleteStudent(String personId) {
        Optional<Student> optionalStudent = studentRepository.findStudentByPersonId(personId);

        if (optionalStudent.isEmpty()) {
            throw new NoSuchElementException(
                    "Student with person id " + personId + " wasn't found"
            );
        }

        optionalStudent.get().setState(false);
        studentRepository.save(optionalStudent.get());
    }

    /**
     * Get a list of students by state
     */
    @Override
    public List<StudentDto> getStudentsByState(boolean state) {
        return studentMapper.toDtos(studentRepository.findStudentsByState(state));
    }
}

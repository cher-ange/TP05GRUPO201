package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.model.Subject;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;
import ar.edu.unju.fi.tp05grupo201.repository.SubjectRepository;
import ar.edu.unju.fi.tp05grupo201.repository.TeacherRepository;
import ar.edu.unju.fi.tp05grupo201.service.ITeacherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Teacher service implementation
 */
@Service
@AllArgsConstructor
@Slf4j
public class TeacherServiceImp implements ITeacherService {
    
    /**
     * Dependencies
     */
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final Teacher teacher;

    /**
     * Create a teacher
     * @return Teacher
     */
    @Override
    public Teacher createTeacher() {
        log.info("Teacher created");
        return teacher;
    }

    /**
     * Add a teacher
     */
    @Override
    public void addTeacher(Teacher teacher) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByFile(teacher.getFile());

        if (optionalTeacher.isPresent()) {
            log.info("Updating teacher with id " + teacher.getId());
            teacher.setId(optionalTeacher.get().getId());
            teacher.setState(true);
        }

        log.info("Adding teacher " + teacher);
        teacherRepository.save(teacher);
    }

    /**
     * Get a teacher by id
     * @return Teacher
     */
    @Override
    public Teacher getTeacherById(long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalTeacher.isEmpty()) {
            throw new NoSuchElementException(
                "Teacher with id " + teacherId + " wasn't found"
            );
        }

        return optionalTeacher.get();
    }

    /**
     * Get a teacher by file
     * @return Teacher
     */
    @Override
    public Teacher getTeacherByFile(String file) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByFile(file);

        if (optionalTeacher.isEmpty()) {
            throw new NoSuchElementException(
                "Teacher with file " + file + " wasn't found"
            );
        }

        return optionalTeacher.get();
    }

    /**
     * Get a list of teachers by state
     */
    @Override
    public List<Teacher> getTeachersByState(boolean state) {
        return teacherRepository.findTeachersByState(state);
    }

    /**
     * Delete a teacher by id
     */
    @Override
    public void deleteTeacher(long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalTeacher.isEmpty()) {
            throw new IllegalArgumentException(
                "Teacher with id " + teacherId + " wasn't found"
            );
        }

        for (Subject subject : subjectRepository.findSubjectsByState(true)) {
            if (subject.getTeacher() != null) {
                if (subject.getTeacher().getId().equals(optionalTeacher.get().getId())) {
                    subject.setTeacher(null);
                    subjectRepository.save(subject);
                }
            }
        }

        optionalTeacher.get().setState(false);
        teacherRepository.save(optionalTeacher.get());
    }
}

package ar.edu.unju.fi.tp05grupo201.service.imp;

import ar.edu.unju.fi.tp05grupo201.model.Subject;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import ar.edu.unju.fi.tp05grupo201.repository.SubjectRepository;
import ar.edu.unju.fi.tp05grupo201.repository.TeacherRepository;
import ar.edu.unju.fi.tp05grupo201.service.ISubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Subject service implementation
 */
@Service
@AllArgsConstructor
@Slf4j
public class SubjectServiceImp implements ISubjectService {

    /**
     * Dependencies
     */
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final Subject subject;

    /**
     * Create a subject
     * @return Subject
     */
    @Override
    public Subject createSubject() {
        log.info("Subject created");
        return subject;
    }

    /**
     * Add a subject
     */
    @Override
    public void addSubject(Subject subject) {
        Optional<Subject> optionalSubject = subjectRepository.findSubjectByCode(subject.getCode());

        if (optionalSubject.isPresent()) {
            log.info("Updating subject with id {}" + subject.getId());
            subject.setId(optionalSubject.get().getId());
            subject.setTeacher(optionalSubject.get().getTeacher());
        }

        log.info("Adding subject");
        subjectRepository.save(subject);
    }

    /**
     * Get a subject by id
     * @return Subject
     */
    @Override
    public Subject getSubjectById(long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);

        if (optionalSubject.isEmpty()) {
            throw new IllegalArgumentException("Subject with id " + id + "wasn't found");
        }

        return optionalSubject.get();
    }

    /**
     * Get a subject by code
     * @return Subject
     */
    @Override
    public Subject getSubjectByCode(String code) {
        Optional<Subject> optionalSubject = subjectRepository.findSubjectByCode(code);

        if (optionalSubject.isEmpty()) {
            log.error("Subject with code {} wasn't found", code);
            throw new IllegalArgumentException(
                "Subject with code " + code + " wasn't found"
            );
        }

        return optionalSubject.get();
    }

    /**
     * Get a list of subjects by state
     */
    @Override
    public List<Subject> getSubjectsByState(boolean state) {
        log.info("Retrieving subjects");
        return subjectRepository.findSubjectsByState(state);
    }

    /**
     * Delete a subject by code
     */
    @Override
    public void deleteSubject(long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);

        if (optionalSubject.isEmpty()) {
            throw new NoSuchElementException(
                    "DELETE: Subject with id " + id + " wasn't found"
            );
        } else {
            List<Student> listOfStudents = optionalSubject.get().getStudents().stream().collect(Collectors.toList());

            for (int i = 0; i < listOfStudents.size(); i++) {
                listOfStudents.get(i).removeSubject(optionalSubject.get());
            }

            optionalSubject.get().setState(false);
            subjectRepository.save(optionalSubject.get());
        }
    }

    @Override
    public void addTeacherToSubject(long subjectId, long teacherId) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalSubject.isPresent() && optionalTeacher.isPresent()) {
            optionalSubject.get().setTeacher(optionalTeacher.get());

            subjectRepository.save(optionalSubject.get());
        }
    }

    @Override
    public void deleteTeacherFromSubject(long subjectId, long teacherId) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalSubject.isEmpty()) {
            log.error("Subject with id {} wasn't found", subjectId);
            throw new NoSuchElementException(
                "Subject with id " + subjectId + " wasn't found"
            );
        }

        if (optionalTeacher.isEmpty()) {
            log.error("Teacher with id {} wasn't found", teacherId);
            throw new NoSuchElementException(
                "Teacher with id " + teacherId + " wasn't found"
            );
        }

        optionalSubject.get().setTeacher(null);
        subjectRepository.save(optionalSubject.get());
    }
}

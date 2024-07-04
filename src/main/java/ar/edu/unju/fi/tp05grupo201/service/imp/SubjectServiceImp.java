package ar.edu.unju.fi.tp05grupo201.service.imp;

import ar.edu.unju.fi.tp05grupo201.model.Subject;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;
import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;
import ar.edu.unju.fi.tp05grupo201.mapper.CareerMapper;
import ar.edu.unju.fi.tp05grupo201.mapper.StudentMapper;
import ar.edu.unju.fi.tp05grupo201.mapper.SubjectMapper;
import ar.edu.unju.fi.tp05grupo201.mapper.TeacherMapper;
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
    private final SubjectMapper subjectMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final CareerMapper careerMapper;
    private final SubjectDto subject;

    /**
     * Create a subject
     * @return Subject
     */
    @Override
    public SubjectDto createSubject() {
        log.info("Subject created");
        return subject;
    }

    /**
     * Add a subject
     */
    @Override
    public void addSubject(SubjectDto subject) {
        Optional<Subject> optionalSubject = subjectRepository.findSubjectByCode(subject.getCode());

        if (optionalSubject.isPresent()) {
            log.info("Updating subject with id {}", subject.getId());
            subject.setId(optionalSubject.get().getId());
            subject.setCareer(careerMapper.toDto(optionalSubject.get().getCareer()));
            subject.setTeacher(teacherMapper.toDto(optionalSubject.get().getTeacher()));
            subject.setStudents(
                optionalSubject.get().getStudents()
                    .stream()
                    .map(studentMapper::toDto)
                    .collect(Collectors.toSet()));
        }

        log.info("Adding subject");

        Subject dummy = subjectMapper.toEntity(subject);

        dummy.setCareer(careerMapper.toEntity(subject.getCareer()));
        dummy.setTeacher(teacherMapper.toEntity(subject.getTeacher()));
        dummy.setStudents(
            subject.getStudents()
                .stream()
                .map(studentMapper::toEntity)
                .collect(Collectors.toSet()));

        subjectRepository.save(dummy);
    }

    /**
     * Get a subject by id
     * @return Subject
     */
    @Override
    public SubjectDto getSubjectById(long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);

        if (optionalSubject.isEmpty()) {
            throw new IllegalArgumentException("Subject with id " + id + "wasn't found");
        }

        return subjectMapper.toDto(optionalSubject.get());
    }

    /**
     * Get a subject by code
     * @return Subject
     */
    @Override
    public SubjectDto getSubjectByCode(String code) {
        Optional<Subject> optionalSubject = subjectRepository.findSubjectByCode(code);

        if (optionalSubject.isEmpty()) {
            log.error("Subject with code {} wasn't found", code);
            throw new IllegalArgumentException(
                "Subject with code " + code + " wasn't found"
            );
        }

        return subjectMapper.toDto(optionalSubject.get());
    }

    /**
     * Get a list of subjects by state
     */
    @Override
    public List<SubjectDto> getSubjectsByState(boolean state) {
        log.info("Retrieving subjects");

        List<SubjectDto> listOfSubjectDtos = new ArrayList<>();
        

        for (Subject subject : subjectRepository.findSubjectsByState(true)) {
            SubjectDto dummy = subjectMapper.toDto(subject);

            dummy.setCareer(careerMapper.toDto(subject.getCareer()));
            dummy.setTeacher(teacherMapper.toDto(subject.getTeacher()));
            dummy.setStudents(subject.getStudents().stream().map(studentMapper::toDto).collect(Collectors.toSet()));

            listOfSubjectDtos.add(dummy);
        }

        return listOfSubjectDtos;
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

    /**
     * Add a teacher to a subject
     */
    @Override
    public void addTeacherToSubject(long subjectId, long teacherId) {
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

        optionalSubject.get().setTeacher(optionalTeacher.get());
        subjectRepository.save(optionalSubject.get());
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

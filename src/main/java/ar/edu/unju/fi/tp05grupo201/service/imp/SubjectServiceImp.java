package ar.edu.unju.fi.tp05grupo201.service.imp;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;
import ar.edu.unju.fi.tp05grupo201.model.Subject;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;
import ar.edu.unju.fi.tp05grupo201.mapper.SubjectMapper;
import ar.edu.unju.fi.tp05grupo201.mapper.TeacherMapper;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import ar.edu.unju.fi.tp05grupo201.repository.SubjectRepository;
import ar.edu.unju.fi.tp05grupo201.repository.TeacherRepository;
import ar.edu.unju.fi.tp05grupo201.service.ISubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Subject service implementation
 */
@Service
@AllArgsConstructor
public class SubjectServiceImp implements ISubjectService {

    /**
     * Dependencies
     */
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectMapper subjectMapper;
    private final TeacherMapper teacherMapper;
    private final SubjectDto subjectDto;

    /**
     * Create a subject
     * @return Subject
     */
    @Override
    public SubjectDto createSubject() {
        return subjectDto;
    }

    /**
     * Add a subject
     */
    @Override
    public void addSubject(SubjectDto subjectDto) {
        Optional<Subject> optionalSubject = subjectRepository.findSubjectByCode(subjectDto.getCode());

        if (optionalSubject.isPresent()) {
            subjectDto.setId(optionalSubject.get().getId());
            subjectDto.setTeacher(teacherMapper.toDto(optionalSubject.get().getTeacher()));
        }

        subjectRepository.save(subjectMapper.toEntity(subjectDto));
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
            throw new IllegalArgumentException(
                    "GET: Subject with code " + code + " wasn't found"
            );
        }

        return subjectMapper.toDto(optionalSubject.get());
    }

    /**
     * Get a list of subjects by state
     */
    @Override
    public List<SubjectDto> getSubjectsByState(boolean state) {
        return subjectRepository.findSubjectsByState(state).stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Delete a subject by code
     */
    @Override
    public void deleteSubject(String code) {
        Optional<Subject> optionalSubject = subjectRepository.findSubjectByCode(code);

        if (optionalSubject.isEmpty()) {
            throw new NoSuchElementException(
                    "DELETE: Subject with code " + code + " wasn't found"
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
            throw new NoSuchElementException(
                "Subject with id " + subjectId + " wasn't found"
            );
        }

        if (optionalTeacher.isEmpty()) {
            throw new NoSuchElementException(
                "Teacher with id " + teacherId + " wasn't found"
            );
        }

        optionalSubject.get().setTeacher(null);
        subjectRepository.save(optionalSubject.get());
    }
}

package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.unju.fi.tp05grupo201.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.dto.TeacherDto;
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
    private final TeacherMapper teacherMapper;
    private final TeacherDto teacherDto;

    /**
     * Create a teacher
     * @return Teacher
     */
    @Override
    public TeacherDto createTeacher() {
        log.info("Teacher created");
        return teacherDto;
    }

    /**
     * Add a teacher
     */
    @Override
    public void addTeacher(TeacherDto teacherDto) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByFile(teacherDto.getFile());

        if (optionalTeacher.isPresent()) {
            log.info("Updating teacher with id " + teacherDto.getId());
            teacherDto.setId(optionalTeacher.get().getId());
            teacherDto.setState(true);
        }

        log.info("Adding teacher " + teacherDto);
        teacherRepository.save(teacherMapper.toEntity(teacherDto));
    }

    /**
     * Get a teacher by id
     * @return Teacher
     */
    @Override
    public TeacherDto getTeacherById(long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalTeacher.isEmpty()) {
            throw new NoSuchElementException(
                "Teacher with id " + teacherId + " wasn't found"
            );
        }

        return teacherMapper.toDto(optionalTeacher.get());
    }

    /**
     * Get a teacher by file
     * @return Teacher
     */
    @Override
    public TeacherDto getTeacherByFile(String file) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByFile(file);

        if (optionalTeacher.isEmpty()) {
            
            throw new NoSuchElementException(
                "Teacher with file " + file + " wasn't found"
            );
        }

        return teacherMapper.toDto(optionalTeacher.get());
    }

    /**
     * Get a list of teachers by state
     */
    @Override
    public List<TeacherDto> getTeachersByState(boolean state) {
        return teacherRepository.findTeachersByState(true).stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
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

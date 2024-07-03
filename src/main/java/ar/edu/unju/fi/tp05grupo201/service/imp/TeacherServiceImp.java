package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.unju.fi.tp05grupo201.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.dto.TeacherDto;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;
import ar.edu.unju.fi.tp05grupo201.repository.TeacherRepository;
import ar.edu.unju.fi.tp05grupo201.service.ITeacherService;
import lombok.AllArgsConstructor;

/**
 * Teacher service implementation
 */
@Service
@AllArgsConstructor
public class TeacherServiceImp implements ITeacherService {
    
    /**
     * Dependencies
     */
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final TeacherDto teacherDto;

    /**
     * Create a teacher
     * @return Teacher
     */
    @Override
    public TeacherDto createTeacher() {
        return teacherDto;
    }

    /**
     * Add a teacher
     */
    @Override
    public void addTeacher(TeacherDto teacherDto) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByFile(teacherDto.getFile());

        /**
         * Condition to re-enable a Teacher that was 'deleted'
         */
        if (optionalTeacher.isPresent()) {
            teacherDto.setId(optionalTeacher.get().getId());
            teacherDto.setState(true);
        }

        teacherRepository.save(teacherMapper.toEntity(teacherDto));
    }

    /**
     * Get a teacher by id
     * @return Teacher
     */
    @Override
    public TeacherDto getTeacherById(long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if (optionalTeacher.isEmpty()) {
            throw new NoSuchElementException(
                "GET: Teacher with id " + id + " wasn't found"
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
                "GET: Teacher with file " + file + " wasn't found"
            );
        }

        return teacherMapper.toDto(optionalTeacher.get());
    }

    /**
     * Delete a teacher by file
     */
    @Override
    public void deleteTeacher(String file) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByFile(file);

        if (optionalTeacher.isEmpty()) {
            throw new IllegalArgumentException(
                "DELETE: Teacher with file " + file + " wasn't found"
            );
        }
        /**
         * Remove the relationship that has with the subject
         */
//        for (Subject subject : optionalTeacher.get().getSubjects()) {
//            optionalTeacher.get().removeSubject(subject);
//        }

        optionalTeacher.get().setState(false);
        teacherRepository.save(optionalTeacher.get());
    }

    /**
     * Get a list of teachers by state
     */
    @Override
    public List<TeacherDto> getTeachersByState(boolean state) {
//        return teacherMapper.toDtos(teacherRepository.findTeachersByState(state).stream().collect(Collectors.toSet()));
        return teacherRepository.findTeachersByState(true).stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
    }
}

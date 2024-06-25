package ar.edu.unju.fi.tp05grupo201.service.imp;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;
import ar.edu.unju.fi.tp05grupo201.mapper.SubjectMapper;
import ar.edu.unju.fi.tp05grupo201.model.Career;
import ar.edu.unju.fi.tp05grupo201.model.Subject;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;
import ar.edu.unju.fi.tp05grupo201.repository.CareerRepository;
import ar.edu.unju.fi.tp05grupo201.repository.SubjectRepository;
import ar.edu.unju.fi.tp05grupo201.repository.TeacherRepository;
import ar.edu.unju.fi.tp05grupo201.service.ISubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    private final CareerRepository careerRepository;
    private final SubjectMapper subjectMapper;
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

        /**
         * Condition to re-enable a Subject that was 'deleted'
         */
        if (optionalSubject.isPresent()) {
            subjectDto.setId(optionalSubject.get().getId());
            subjectDto.setState(true);
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
//            List<Teacher> listOfTeachers = optionalSubject.get().getTeachers().stream().collect(Collectors.toList());
//            List<Career> listOfCareers = optionalSubject.get().getCareers().stream().collect(Collectors.toList());

            /**
             * Remove the relationship these entities have with the subject
             */
//            for (int i = 0; i < listOfTeachers.size(); i++) {
//                listOfTeachers.get(i).removeSubject(optionalSubject.get());
//            }
//
//            for (int i = 0; i < listOfCareers.size(); i++) {
//                listOfCareers.get(i).removeSubject(optionalSubject.get());
//            }

            optionalSubject.get().setState(false);
            subjectRepository.save(optionalSubject.get());
        }
    }

    /**
     * Get a list of subjects by state
     */
    @Override
    public List<SubjectDto> getSubjectsByState(boolean state) {
        return subjectMapper.toDtos(subjectRepository.findSubjectsByState(state));
    }

    /**
     * Assign a career to a subject
     */
    @Override
    public void addCareerToSubject(String subjectCode, String careerCode) {
        Optional<Subject> optionalSubject = subjectRepository.findSubjectByCode(subjectCode);
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(careerCode);

//        if (optionalSubject.isPresent()) {
//            optionalCareer.get().addSubject(optionalSubject.get());
//            careerRepository.save(optionalCareer.get());
//        }
    }

    /**
     * Assign a teacher to a subject
     */
    @Override
    public void addTeacherToSubject(String subjectCode, String teacherFile) {
        Optional<Subject> optionalSubject = subjectRepository.findSubjectByCode(subjectCode);
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByFile(teacherFile);

//        if (optionalSubject.isPresent()) {
//            optionalTeacher.get().addSubject(optionalSubject.get());
//            teacherRepository.save(optionalTeacher.get());
//        }
    }

    @Override
    public void deleteCareerFromSubject(String careerCode) {
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(careerCode);

        if (optionalCareer.isEmpty()) {
            throw new IllegalArgumentException(
                    "DELETE: Career with code " + careerCode + " wasn't found"
            );
        }

//        for (Subject subject : optionalCareer.get().getSubjects()) {
//            optionalCareer.get().removeSubject(subject);
//        }

        careerRepository.save(optionalCareer.get());
    }

    @Override
    public void deleteTeacherFromSubject(String teacherFile) {
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByFile(teacherFile);

        if (optionalTeacher.isEmpty()) {
            throw new IllegalArgumentException(
                    "DELETE: Teacher with file " + teacherFile + " wasn't found"
            );
        }
        /**
         * Remove the relationship that has with the subject
         */
//        for (Subject subject : optionalTeacher.get().getSubjects()) {
//            optionalTeacher.get().removeSubject(subject);
//        }

        teacherRepository.save(optionalTeacher.get());
    }
}

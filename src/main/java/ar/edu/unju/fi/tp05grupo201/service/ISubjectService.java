package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;
import java.util.List;

public interface ISubjectService {

    SubjectDto createSubject();

    SubjectDto getSubjectById(long id);

    SubjectDto getSubjectByCode(String code);

    List<SubjectDto> getSubjectsByState(boolean state);

    void addSubject(SubjectDto subject);

    void addTeacherToSubject(long subjectId, long teacherId);

    void deleteSubject(long id);

    void deleteTeacherFromSubject(long subjectId, long teacherId);
}

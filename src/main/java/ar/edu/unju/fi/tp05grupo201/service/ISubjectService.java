package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;

import java.util.List;

public interface ISubjectService {

    SubjectDto createSubject();

    void addSubject(SubjectDto subjectDto);

    SubjectDto getSubjectById(long id);

    SubjectDto getSubjectByCode(String code);

    List<SubjectDto> getSubjectsByState(boolean state);

    void deleteSubject(String code);

    void addTeacherToSubject(long subjectId, long teacherId);

    void deleteTeacherFromSubject(long subjectId, long teacherId);
}

package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;

import java.util.List;

public interface ISubjectService {

    SubjectDto createSubject();

    void addSubject(SubjectDto subjectDto);

    SubjectDto getSubjectById(long id);

    SubjectDto getSubjectByCode(String code);

    void deleteSubject(String code);

    void deleteCareerFromSubject(String careerCode);

    void deleteTeacherFromSubject(String teacherFile);

    List<SubjectDto> getSubjectsByState(boolean state);

    void addCareerToSubject(String subjectCode, String careerCode);

    void addTeacherToSubject(String subjectCode, String teacherFile);

    void save(SubjectDto subjectDto);
}

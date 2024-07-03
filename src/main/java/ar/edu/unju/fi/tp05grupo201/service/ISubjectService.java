package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;

import java.util.List;

public interface ISubjectService {

    SubjectDto createSubject();

    void addSubject(SubjectDto subjectDto);

    SubjectDto getSubjectById(long id);

    SubjectDto getSubjectByCode(String code);

    void deleteSubject(String code);

    List<SubjectDto> getSubjectsByState(boolean state);
}

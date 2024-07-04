package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.model.Subject;

import java.util.List;

public interface ISubjectService {

    Subject createSubject();

    Subject getSubjectById(long id);

    Subject getSubjectByCode(String code);

    List<Subject> getSubjectsByState(boolean state);

    void addSubject(Subject subject);

    void addTeacherToSubject(long subjectId, long teacherId);

    void deleteSubject(long id);

    void deleteTeacherFromSubject(long subjectId, long teacherId);
}

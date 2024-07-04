package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.model.Student;

import java.util.List;

public interface IStudentService {

    Student createStudent();

    Student getStudentById(long studentId);

    Student getStudentByPersonId(String code);

    List<Student> getStudentsByState(boolean state);

    List<Student> getStudentsBySubject(long subjectId);

    List<Student> getStudentsByCareer(long careerId);

    void addStudent(Student student);
    
    void addSubjectToStudent(long studentId, long subjectId);
    
    void addCareerToStudent(long studentId, long careerId);

    void deleteStudent(long studentId);

    void deleteSubjectFromStudent(long studentId, long subjectId);

    void deleteCareerFromStudent(long studentId, long careerId);
}

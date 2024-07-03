package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;

import java.util.List;

public interface IStudentService {

    StudentDto createStudent();

    void addStudent(StudentDto studentDto);

    StudentDto getStudentById(long studentId);

    StudentDto getStudentByPersonId(String code);

    List<StudentDto> getStudentsByState(boolean state);

    void deleteStudent(long studentId);

    void deleteSubjectFromStudent(long studentId, long subjectId);

    void deleteCareerFromStudent(long studentId, long careerId);

    void addSubjectToStudent(long studentId, long subjectId);
    
    void addCareerToStudent(long studentId, long careerId);
}

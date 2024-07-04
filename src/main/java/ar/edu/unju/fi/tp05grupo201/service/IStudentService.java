package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;

import java.util.List;

public interface IStudentService {

    StudentDto createStudent();

    StudentDto getStudentById(long studentId);

    StudentDto getStudentByPersonId(String code);

    List<StudentDto> getStudentsByState(boolean state);

    void addStudent(StudentDto studentDto);
    
    void addSubjectToStudent(long studentId, long subjectId);
    
    void addCareerToStudent(long studentId, long careerId);

    void deleteStudent(long studentId);

    void deleteSubjectFromStudent(long studentId, long subjectId);

    void deleteCareerFromStudent(long studentId, long careerId);
}

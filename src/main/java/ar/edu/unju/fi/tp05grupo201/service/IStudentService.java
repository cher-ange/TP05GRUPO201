package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import java.util.List;

public interface IStudentService {

    StudentDto createStudent();

    StudentDto getStudentById(long studentId);

    StudentDto getStudentByPersonId(String code);

    List<StudentDto> getStudentsByState(boolean state);

    List<StudentDto> getStudentsBySubject(long subjectId);

    List<StudentDto> getStudentsByCareer(long careerId);

    void addStudent(StudentDto student);
    
    void addSubjectToStudent(long studentId, long subjectId);
    
    void addCareerToStudent(long studentId, long careerId);

    void deleteStudent(long studentId);

    void deleteSubjectFromStudent(long studentId, long subjectId);

    void deleteCareerFromStudent(long studentId, long careerId);
}

package ar.edu.unju.fi.tp05grupo201.service;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;

import java.util.List;

public interface IStudentService {

    StudentDto createStudent();

    void addStudent(StudentDto studentDto);

    StudentDto getStudentById(long id);

    StudentDto getStudentByPersonId(String code);

    void deleteStudent(String code);

    List<StudentDto> getStudentsByState(boolean state);
}

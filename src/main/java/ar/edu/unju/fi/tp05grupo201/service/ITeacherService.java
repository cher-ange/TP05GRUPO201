package ar.edu.unju.fi.tp05grupo201.service;

import java.util.List;

import ar.edu.unju.fi.tp05grupo201.dto.TeacherDto;

public interface ITeacherService {
    
    TeacherDto createTeacher();
    
    TeacherDto getTeacherById(long teacherId);
    
    TeacherDto getTeacherByFile(String file);

    List<TeacherDto> getTeachersByState(boolean state);

    void addTeacher(TeacherDto teacher);
    
    void deleteTeacher(long teacherId);
}

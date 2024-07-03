package ar.edu.unju.fi.tp05grupo201.service;

import java.util.List;

import ar.edu.unju.fi.tp05grupo201.dto.TeacherDto;

public interface ITeacherService {
    
    TeacherDto createTeacher();
    
    void addTeacher(TeacherDto teacherDto);
    
    TeacherDto getTeacherById(long teacherId);
    
    TeacherDto getTeacherByFile(String file);

    List<TeacherDto> getTeachersByState(boolean state);
    
    void deleteTeacher(long teacherId);
}

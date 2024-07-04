package ar.edu.unju.fi.tp05grupo201.service;

import java.util.List;

import ar.edu.unju.fi.tp05grupo201.model.Teacher;

public interface ITeacherService {
    
    Teacher createTeacher();
    
    Teacher getTeacherById(long teacherId);
    
    Teacher getTeacherByFile(String file);

    List<Teacher> getTeachersByState(boolean state);

    void addTeacher(Teacher teacher);
    
    void deleteTeacher(long teacherId);
}

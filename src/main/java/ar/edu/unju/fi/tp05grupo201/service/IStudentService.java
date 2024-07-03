package ar.edu.unju.fi.tp05grupo201.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.model.Student;

public interface IStudentService {

	public void saveStudent(StudentDto student);
	
	public List<Student> showStudents();
	
	public StudentDto findStudent(Long id);
	
	public void deleteStudent(Long id);
	
	public void modifyStudent(StudentDto student);
	
}

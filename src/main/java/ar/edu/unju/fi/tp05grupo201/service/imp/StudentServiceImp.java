package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.mapper.StudentMapper;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import ar.edu.unju.fi.tp05grupo201.repository.StudentRepository;
import ar.edu.unju.fi.tp05grupo201.service.IStudentService;

@Service
public class StudentServiceImp implements IStudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	StudentMapper studentMapper;
	
	@Override
	public void saveStudent(StudentDto studentDto) {
		// TODO Auto-generated method stub
		
		studentDto.setState(true);
		studentRepository.save(studentMapper.convertStudentDtoToStudent(studentDto));
	}

	@Override
	public List<Student> showStudents() {
		// TODO Auto-generated method stub
		return studentRepository.findStudentByState(true);
	}

	@Override
	public Student findStudent(Long id) {
		// TODO Auto-generated method stub
		List<Student> allStudents = studentRepository.findAll();
		for(Student s : allStudents ) {
			if(s.getId().equals(id)) {
				return s;
			}
		}
		return null;
	}

	@Override
	public void deleteStudent(Long id) {
		// TODO Auto-generated method stub
		List<Student> allStudents = studentRepository.findAll();
		for(int i=0; i<=allStudents.size(); i++) {
			Student student = allStudents.get(i);
			if(student.getId().equals(id)) {
				student.setState(false);
				studentRepository.save(student);
				break;
			}
		}
	}

	@Override
	public void modifyStudent(StudentDto studentDto) {
		// TODO Auto-generated method stub
		List<Student> allStudents = studentRepository.findAll();
		studentDto.setState(true);
		for(int i=0; i<=allStudents.size(); i++) {
			Student students = allStudents.get(i);
			if(students.getId().equals(studentDto.getId())) {
				allStudents.set(i, studentMapper.convertStudentDtoToStudent(studentDto));
				break;
			}
		}
	}

}

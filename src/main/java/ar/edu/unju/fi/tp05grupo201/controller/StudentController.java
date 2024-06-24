package ar.edu.unju.fi.tp05grupo201.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import ar.edu.unju.fi.tp05grupo201.service.IStudentService;

@Controller
public class StudentController {

	@Autowired
	StudentDto newStudentDto;
	
	@Autowired
	IStudentService studentService;
	
	@GetMapping("/formStudent")
	public ModelAndView getFormStudent() {
		ModelAndView modelView = new ModelAndView("student/student-form");
		newStudentDto.setState(true);
		modelView.addObject("newStudent",newStudentDto);
		modelView.addObject("band",false);
		
		return modelView;
	}
	
	@PostMapping("/saveStudent")
	public ModelAndView saveStudent(@ModelAttribute("newStudent") StudentDto studentForSave) {
		studentService.saveStudent(studentForSave);
		ModelAndView modelView = new ModelAndView("student/students");
		modelView.addObject("listStudents", studentService.showStudents());
		
		return modelView;
	}
	
	@GetMapping("/deleteStudent/{id}")
	public ModelAndView deleteStudent(@PathVariable(name ="id") Long id) {
		studentService.deleteStudent(id);
		
		ModelAndView modelView = new ModelAndView("student/students");
		modelView.addObject("listStudents", studentService.showStudents());
		
		return modelView;
	}
	
	@GetMapping("/modifyStudent/{id}")
	public ModelAndView editStudent(@PathVariable(name="id") Long id) {
		Student studentToModify = studentService.findStudent(id);
		
		ModelAndView modelView = new ModelAndView("student/student-form");
		modelView.addObject("newStudent", studentToModify);
		modelView.addObject("band", false);
		return modelView;
	}
	
	@PostMapping("/modifyStudent")
	public ModelAndView updateStudent(@ModelAttribute("newStudent") StudentDto modifiedStudent) {
		studentService.modifyStudent(modifiedStudent);
		
		ModelAndView modelView = new ModelAndView("student/students");
		modelView.addObject("listStudents", studentService.showStudents());
		
		return modelView;
	}
}

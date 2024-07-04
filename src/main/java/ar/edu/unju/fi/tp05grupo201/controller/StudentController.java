package ar.edu.unju.fi.tp05grupo201.controller;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.service.ICareerService;
import ar.edu.unju.fi.tp05grupo201.service.imp.StudentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import ar.edu.unju.fi.tp05grupo201.service.IStudentService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/student")
public class StudentController {

	@Autowired
	StudentDto newStudentDto;
	
	@Autowired
	StudentServiceImp studentService;

	@Autowired
	ICareerService careerService;

	@GetMapping(path = "/list")
	public ModelAndView getStudents() {
		ModelAndView modelAndView = new ModelAndView("student/students");

		modelAndView.addObject("listStudents", studentService.showStudents());
		modelAndView.addObject("careerSet", careerService.getCareersByState(true));

		return modelAndView;
	}

	@GetMapping(path = "/list/byCareer")
	public ModelAndView getStudentsByCareer(@RequestParam(name = "careerName") String careerName) {
		List<Student> students = studentService.showStudents()
				.stream().
				filter(student -> student.getCareer().getName().equals(careerName))
				.toList();
		ModelAndView modelAndView = new ModelAndView("student/students-by-career");
		modelAndView.addObject("listStudents", students);
		modelAndView.addObject("careerName", careerName);
		return modelAndView;
	}

	@GetMapping("/formStudent")
	public ModelAndView getFormStudent() {
		ModelAndView modelView = new ModelAndView("student/student-form");

		newStudentDto.setState(true);
		modelView.addObject("newStudent",newStudentDto);
		modelView.addObject("band",false);
		modelView.addObject("careerSet", careerService.getCareersByState(true));

		return modelView;
	}
	
	@PostMapping("/saveStudent")
	public ModelAndView saveStudent(@ModelAttribute("newStudent") StudentDto studentForSave) {
		CareerDto careerDto = careerService.getCareerByCode(studentForSave.getCareer().getCode());
		studentForSave.setCareer(careerDto);
		studentService.saveStudent(studentForSave);
		ModelAndView modelView = new ModelAndView("student/students");
		modelView.addObject("listStudents", studentService.showStudents());
		
		return modelView;
	}
	
	@GetMapping("/deleteStudent/{id}")
	public ModelAndView deleteStudent(@PathVariable(name ="id") Long id) {
		ModelAndView modelView = new ModelAndView("student/students");

		studentService.deleteStudent(id);
		modelView.addObject("listStudents", studentService.showStudents());
		
		return modelView;
	}
	
	@GetMapping("/modifyStudent/{id}")
	public ModelAndView editStudent(@PathVariable(name="id") Long id) {
		ModelAndView modelView = new ModelAndView("student/student-form");

		StudentDto studentToModify = studentService.findStudent(id);
		modelView.addObject("newStudent", studentToModify);
		modelView.addObject("band", false);

		return modelView;
	}
	
	@PostMapping("/modifyStudent")
	public ModelAndView updateStudent(@ModelAttribute("newStudent") StudentDto modifiedStudent) {
		ModelAndView modelView = new ModelAndView("student/students");

		studentService.modifyStudent(modifiedStudent);
		modelView.addObject("listStudents", studentService.showStudents());
		
		return modelView;
	}
}
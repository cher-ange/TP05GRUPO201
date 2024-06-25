package ar.edu.unju.fi.tp05grupo201.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.service.imp.StudentServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/student")
@AllArgsConstructor
@Slf4j
public class StudentController {

    /**
     * View/Endpoint PATH constants
     */
    private final String LIST_VIEWNAME = "student/students";
    private final String FORM_VIEWNAME = "student/student-form";
    private final String REDIRECT_TO_LIST_ENDPOINT = "redirect:/student/list";

    /**
     * Dependencies
     */
    private final StudentServiceImp studentService;

    /**
     * Endpoints
     */
    @GetMapping(path = "/list")
    public ModelAndView getStudents() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(LIST_VIEWNAME);
        modelAndView.addObject(
                "listOfStudents",
                studentService.getStudentsByState(true)
        );

        return modelAndView;
    }

    @GetMapping(path = "/add")
    public ModelAndView getAddStudentFormPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject(
                "allowEditing",
                false
        );
        modelAndView.addObject(
                "studentSubmitted",
                studentService.createStudent()
        );

        return modelAndView;
    }

    @PostMapping(path = "/save")
    public ModelAndView postSaveStudentFormPage(
            @Valid @ModelAttribute(name = "studentSubmitted") StudentDto studentDto,
            BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject("allowEditing", false);
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            studentService.addStudent(studentDto);
        }

        return modelAndView;
    }

    @GetMapping(path = "/update/{personId}")
    public ModelAndView getUpdateStudentFormPage(
            @PathVariable(value = "personId") String personId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject("allowEditing", true);
        modelAndView.addObject(
                "studentSubmitted",
                studentService.getStudentByPersonId(personId)
        );

        return modelAndView;
    }

    @PostMapping(path = "/update")
    public ModelAndView postUpdateStudentFormPage(
            @Valid @ModelAttribute(value = "studentSubmitted") StudentDto studentDto,
            BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject("allowEditing", true);
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            studentService.addStudent(studentDto);
        }

        return modelAndView;
    }

    @GetMapping(path = "/delete/{personId}")
    public ModelAndView getDeleteStudentPage(
            @PathVariable(value = "personId") String personId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        studentService.deleteStudent(personId);

        return modelAndView;
    }
}

package ar.edu.unju.fi.tp05grupo201.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.tp05grupo201.dto.TeacherDto;
import ar.edu.unju.fi.tp05grupo201.service.imp.TeacherServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path = "/teacher")
@AllArgsConstructor
public class TeacherController {

    /**
     * View/Endpoint PATH constants
     */
    private final String LIST_VIEWNAME = "teacher/teachers";
    private final String FORM_VIEWNAME = "teacher/teacher-form";
    private final String REDIRECT_TO_LIST_ENDPOINT = "redirect:/teacher/list";

    /**
     * Dependencies
     */
    private final TeacherServiceImp teacherService;

    @GetMapping(path = "/list")
    public ModelAndView getTeachers() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(LIST_VIEWNAME);
        modelAndView.addObject(
            "listOfTeachers",
            teacherService.getTeachersByState(true)
        );

        return modelAndView;
    }
    
    @GetMapping(path = "/add")
    public ModelAndView getAddTeacherFormPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject("allowEditing", false);
        modelAndView.addObject(
            "teacherSubmitted",
            teacherService.createTeacher()
        );

        return modelAndView;
    }
    
    @PostMapping(path = "/save")
    public ModelAndView postSaveTeacherFormPage(
        @Valid @ModelAttribute(name = "teacherSubmitted") TeacherDto teacherDto,
        BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject("allowEditing", false);
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            teacherService.addTeacher(teacherDto);
        }

        return modelAndView;
    }

    @GetMapping(path = "/update/{teacherId}")
    public ModelAndView getUpdateTeacherFormPage(
        @PathVariable(value = "teacherId") long teacherId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject("allowEditing", true);
        modelAndView.addObject(
            "teacherSubmitted",
            teacherService.getTeacherById(teacherId)
        );

        return modelAndView;
    }

    @PostMapping(path = "/update")
    public ModelAndView postUpdateTeacherFormPage(
        @Valid @ModelAttribute(value = "teacherSubmitted") TeacherDto teacherDto,
        BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject("allowEditing", true);
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            teacherService.addTeacher(teacherDto);
        }

        return modelAndView;
    }
    
    @GetMapping(path = "/delete/{teacherId}")
    public ModelAndView getDeleteTeacherPage(
        @PathVariable(value = "teacherId") long teacherId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        teacherService.deleteTeacher(teacherId);

        return modelAndView;
    }
}

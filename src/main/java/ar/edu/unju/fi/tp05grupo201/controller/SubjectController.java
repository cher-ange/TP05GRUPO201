package ar.edu.unju.fi.tp05grupo201.controller;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;
import ar.edu.unju.fi.tp05grupo201.service.imp.CareerServiceImp;
import ar.edu.unju.fi.tp05grupo201.service.imp.SubjectServiceImp;
import ar.edu.unju.fi.tp05grupo201.service.imp.TeacherServiceImp;
import ar.edu.unju.fi.tp05grupo201.util.AttendanceType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping(path = "/subject")
@AllArgsConstructor
public class SubjectController {

    /**
     * View/Endpoint PATH constants
     */
    private final String LIST_VIEWNAME = "subject/subjects";
    private final String FORM_VIEWNAME = "subject/subject-form";
    private final String ADD_TEACHER_FORM_VIEWNAME = "subject/add-teacher-to-subject-form";
    private final String REDIRECT_TO_LIST_ENDPOINT = "redirect:/subject/list";

    /**
     * Dependencies
     */
    private final SubjectServiceImp subjectService;
    private final TeacherServiceImp teacherService;
    private final CareerServiceImp careerService;

    @GetMapping(path = "/list")
    public ModelAndView getSubjects() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(LIST_VIEWNAME);
        modelAndView.addObject(
                "listOfSubjects",
                subjectService.getSubjectsByState(true)
        );

        return modelAndView;
    }

    @GetMapping(path = "/add")
    public ModelAndView getAddSubjectFormPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject(
                "allowEditing",
                false
        );
        modelAndView.addObject(
                "subjectSubmitted",
                subjectService.createSubject()
        );
        modelAndView.addObject(
                "listOfTeachers",
                teacherService.getTeachersByState(true)
        );
        modelAndView.addObject(
                "listOfCareers",
                careerService.getCareersByState(true)
        );
        modelAndView.addObject(
                "listOfAttendanceTypes",
                new ArrayList<>(Arrays.asList(AttendanceType.values()))
        );

        return modelAndView;
    }

    @PostMapping(path = "/save")
    public ModelAndView postSaveSubjectFormPage(
            @Valid @ModelAttribute(name = "subjectSubmitted") SubjectDto subjectDto,
            BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject("allowEditing", false);
            modelAndView.addObject(
                    "listOfTeachers",
                    teacherService.getTeachersByState(true)
            );
            modelAndView.addObject(
                    "listOfCareers",
                    careerService.getCareersByState(true)
            );
            modelAndView.addObject(
                    "listOfAttendanceTypes",
                    new ArrayList<>(Arrays.asList(AttendanceType.values()))
            );
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            subjectService.addSubject(subjectDto);
        }

        return modelAndView;
    }

    @GetMapping(path = "/update/{subjectId}")
    public ModelAndView getUpdateSubjectFormPage(
            @PathVariable(value = "subjectId") long subjectId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject(
                "allowEditing",
                true
        );
        modelAndView.addObject(
                "subjectSubmitted",
                subjectService.getSubjectById(subjectId)
        );
        modelAndView.addObject(
                "listOfTeachers",
                teacherService.getTeachersByState(true)
        );
        modelAndView.addObject(
                "listOfCareers",
                careerService.getCareersByState(true)
        );
        modelAndView.addObject(
                "listOfAttendanceTypes",
                new ArrayList<>(Arrays.asList(AttendanceType.values()))
        );

        return modelAndView;
    }

    @PostMapping(path = "/update")
    public ModelAndView postUpdateSubjectFormPage(
            @Valid @ModelAttribute(value = "subjectSubmitted") SubjectDto subjectDto,
            BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject(
                    "allowEditing",
                    true
            );
            modelAndView.addObject(
                    "listOfTeachers",
                    teacherService.getTeachersByState(true)
            );
            modelAndView.addObject(
                    "listOfCareers",
                    careerService.getCareersByState(true)
            );
            modelAndView.addObject(
                    "listOfAttendanceTypes",
                    new ArrayList<>(Arrays.asList(AttendanceType.values()))
            );
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            subjectService.addSubject(subjectDto);
        }

        return modelAndView;
    }

    @GetMapping(path = "/delete/{code}")
    public ModelAndView getDeleteSubjectPage(
            @PathVariable(value = "code") String code
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        subjectService.deleteSubject(code);

        return modelAndView;
    }

    /*----------------------------*
     | Add a Teacher to a Subject |
     *----------------------------*/
    @GetMapping(path = "/{subjectId}/teacher/add")
    public ModelAndView getAddTeacherPage(
            @PathVariable(value = "subjectId") long subjectId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ADD_TEACHER_FORM_VIEWNAME);

        modelAndView.addObject(
                "listOfTeachers",
                teacherService.getTeachersByState(true)
        );
        
        modelAndView.addObject(
                "subjectSubmitted",
                subjectService.getSubjectById(subjectId)
        );

        modelAndView.addObject(
                "allowEditing",
                false
        );

        return modelAndView;
    }

    @GetMapping(path = "/{subjectId}/teacher")
    public ModelAndView getSaveTeacherPage(
            @PathVariable(value = "subjectId") long subjectId,
            @RequestParam(value = "teacherId") long teacherId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        subjectService.addTeacherToSubject(subjectId, teacherId);

        return modelAndView;
    }

    /*-------------------------------*
     | Update a Teacher from Subject |
     *-------------------------------*/
    @GetMapping(path = "/{subjectId}/teacher/update")
    public ModelAndView getUpdateCareerPage(
            @PathVariable(value = "subjectId") long subjectId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ADD_TEACHER_FORM_VIEWNAME);
        
        modelAndView.addObject(
                "listOfTeachers",
                teacherService.getTeachersByState(true)
        );

        modelAndView.addObject(
                "subjectSubmitted",
                subjectService.getSubjectById(subjectId)
        );
        modelAndView.addObject(
                "allowEditing",
                true
        );

        return modelAndView;
    }

    /*---------------------------------*
     | Delete a Teacher from a Subject |
     *---------------------------------*/
    @GetMapping(path = "/{subjectId}/teacher/{teacherId}/delete")
    public ModelAndView getDeleteSubjectFromStudentPage(
            @PathVariable(value = "subjectId") long subjectId,
            @PathVariable(value = "teacherId") long teacherId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        subjectService.deleteTeacherFromSubject(subjectId, teacherId);

        return modelAndView;
    }
}

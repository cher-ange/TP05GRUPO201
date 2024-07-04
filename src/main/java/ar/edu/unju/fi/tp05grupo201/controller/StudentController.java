package ar.edu.unju.fi.tp05grupo201.controller;

import ar.edu.unju.fi.tp05grupo201.model.Student;
import ar.edu.unju.fi.tp05grupo201.service.imp.SubjectServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ar.edu.unju.fi.tp05grupo201.service.imp.CareerServiceImp;
import ar.edu.unju.fi.tp05grupo201.service.imp.StudentServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/student")
@AllArgsConstructor
public class StudentController {

    /**
     * View/Endpoint PATH constants
     */
    private final String LIST_VIEWNAME = "student/students";
    private final String FORM_VIEWNAME = "student/student-form";
    private final String ADD_SUBJECT_FORM_VIEWNAME = "student/add-subject-to-student";
    private final String ADD_CAREER_FORM_VIEWNAME = "student/add-career-to-student";
    private final String REDIRECT_TO_LIST_ENDPOINT = "redirect:/student/list";

    /**
     * Dependencies
     */
    private final StudentServiceImp studentService;
    private final SubjectServiceImp subjectService;
    private final CareerServiceImp careerService;

    /**
     * Endpoints
     */

    /**
     * Retrieve a list of students
     * @return
     */
    @GetMapping(path = "/list")
    public ModelAndView getStudents() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(LIST_VIEWNAME);
        modelAndView.addObject(
                "listOfStudents",
                studentService.getStudentsByState(true));

        return modelAndView;
    }

    /**
     * 
     * @param subjectName
     * @return
     */
    @GetMapping(path = "/list/bySubject")
    public ModelAndView getStudentsBySubject(@RequestParam(name = "subjectName") String subjectName) {
        // TODO
        ModelAndView modelAndView = new ModelAndView();
        
        return modelAndView;
    }

    /**
     * Add a student
     * @return
     */
    @GetMapping(path = "/add")
    public ModelAndView getAddStudentFormPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject(
                "allowEditing",
                false);
        modelAndView.addObject(
                "studentSubmitted",
                studentService.createStudent());

        return modelAndView;
    }

    /**
     * Save a student
     * @param student
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/save")
    public ModelAndView postSaveStudentFormPage(
            @Valid @ModelAttribute(name = "studentSubmitted") Student student,
            BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject(
                    "allowEditing",
                    false);
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            studentService.addStudent(student);
        }

        return modelAndView;
    }

    /**
     * Retrieve a student by id to be updated
     * @param studentId
     * @return
     */
    @GetMapping(path = "/update/{studentId}")
    public ModelAndView getUpdateStudentFormPage(
            @PathVariable(value = "studentId") long studentId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject("allowEditing", true);
        modelAndView.addObject(
                "studentSubmitted",
                studentService.getStudentById(studentId));

        return modelAndView;
    }

    /**
     * Update a student
     * @param student
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/update")
    public ModelAndView postUpdateStudentFormPage(
            @Valid @ModelAttribute(value = "studentSubmitted") Student student,
            BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject(
                    "allowEditing",
                    true);
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            studentService.addStudent(student);
        }

        return modelAndView;
    }

    /**
     * Delete (logically) a student
     * @param studentId
     * @return
     */
    @GetMapping(path = "/delete/{studentId}")
    public ModelAndView getDeleteStudentPage(
            @PathVariable(value = "studentId") long studentId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        studentService.deleteStudent(studentId);

        return modelAndView;
    }

    /**
     * Add a subject to a student retrieved by id
     * @param studentId
     * @return
     */
    @GetMapping(path = "/{studentId}/subjects/add")
    public ModelAndView getAddSubjectToStudentPage(
            @PathVariable(value = "studentId") long studentId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ADD_SUBJECT_FORM_VIEWNAME);
        modelAndView.addObject(
                "listOfSubjects",
                subjectService.getSubjectsByState(true));
        modelAndView.addObject(
                "studentSubmitted",
                studentService.getStudentById(studentId));
        modelAndView.addObject(
                "allowEditing",
                false);

        return modelAndView;
    }

    /**
     * Save a subject
     * @param studentId
     * @param subjectId
     * @return
     */
    @GetMapping(path = "/{studentId}/subjects")
    public ModelAndView getSaveSubjectPage(
            @PathVariable(value = "studentId") long studentId,
            @RequestParam(value = "subjectId") long subjectId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        studentService.addSubjectToStudent(studentId, subjectId);

        return modelAndView;
    }

    /**
     * Delete a subject from a student
     * @param studentId
     * @param subjectId
     * @return
     */
    @GetMapping(path = "/{studentId}/subjects/{subjectId}/delete")
    public ModelAndView getDeleteSubjectFromStudentPage(
            @PathVariable(value = "studentId") long studentId,
            @PathVariable(value = "subjectId") long subjectId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        studentService.deleteSubjectFromStudent(studentId, subjectId);

        return modelAndView;
    }

    /**
     * Add a career to a student
     * @param studentId
     * @return
     */
    @GetMapping(path = "/{studentId}/career/add")
    public ModelAndView getAddCareerToStudentPage(
            @PathVariable(value = "studentId") long studentId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ADD_CAREER_FORM_VIEWNAME);
        modelAndView.addObject(
                "listOfCareers",
                careerService.getCareersByState(true));
        modelAndView.addObject(
                "studentSubmitted",
                studentService.getStudentById(studentId));
        modelAndView.addObject(
                "allowEditing",
                false);

        return modelAndView;
    }

    /**
     * Save a career
     * @param studentId
     * @param careerId
     * @return
     */
    @GetMapping(path = "/{studentId}/career")
    public ModelAndView getSaveCareerPage(
            @PathVariable(value = "studentId") long studentId,
            @RequestParam(value = "careerId") long careerId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        studentService.addCareerToStudent(studentId, careerId);

        return modelAndView;
    }

    /**
     * Update a career from a student
     * @param studentId
     * @return
     */
    @GetMapping(path = "/{studentId}/career/update")
    public ModelAndView getUpdateCareerFromStudentPage(
            @PathVariable(value = "studentId") long studentId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ADD_CAREER_FORM_VIEWNAME);
        modelAndView.addObject(
                "listOfCareers",
                careerService.getCareersByState(true));
        modelAndView.addObject(
                "studentSubmitted",
                studentService.getStudentById(studentId));
        modelAndView.addObject(
                "allowEditing",
                true);

        return modelAndView;
    }

    /**
     * Delete a career from a student
     * @param studentId
     * @param careerId
     * @return
     */
    @GetMapping(path = "/{studentId}/career/{careerId}/delete")
    public ModelAndView getDeleteCareerFromStudentPage(
            @PathVariable(value = "studentId") long studentId,
            @PathVariable(value = "careerId") long careerId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        studentService.deleteCareerFromStudent(studentId, careerId);

        return modelAndView;
    }
}

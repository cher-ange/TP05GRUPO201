package ar.edu.unju.fi.tp05grupo201.controller;

import ar.edu.unju.fi.tp05grupo201.service.imp.CareerServiceImp;
import ar.edu.unju.fi.tp05grupo201.service.imp.SubjectServiceImp;
import ar.edu.unju.fi.tp05grupo201.service.imp.TeacherServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/subject")
@AllArgsConstructor
public class SubjectController {

    /**
     * View/Endpoint PATH constants
     */
    private final String LIST_VIEWNAME = "subject/subjects";
    private final String FORM_VIEWNAME = "subject/subject-form";
    private final String ADD_CAREER_FORM_VIEWNAME = "subject/add-career-to-subject-form";
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
        modelAndView.addObject("allowEditing", false);
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

    @GetMapping(path = "/update/{code}")
    public ModelAndView getUpdateSubjectFormPage(
            @PathVariable(value = "code") String code
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject("allowEditing", true);
        modelAndView.addObject(
                "subjectSubmitted",
                subjectService.getSubjectByCode(code)
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
            modelAndView.addObject("allowEditing", true);
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

    @GetMapping(path = "/{subjectCode}/careers/add")
    public ModelAndView getAddCareerPage(
            @PathVariable(value = "subjectCode") String code
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ADD_CAREER_FORM_VIEWNAME);
        modelAndView.addObject(
                "listOfCareers",
                careerService.getCareersByState(true)
        );
        modelAndView.addObject(
                "subjectSubmitted",
                subjectService.getSubjectByCode(code)
        );
        modelAndView.addObject(
                "allowEditing",
                false
        );

        return modelAndView;
    }

    @GetMapping(path = "/{subjectCode}/careers")
    public ModelAndView getAddCareerToSubjectPage(
            @PathVariable(value = "subjectCode") String subjectCode,
            @RequestParam(value = "careerCode") String careerCode
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        subjectService.addCareerToSubject(subjectCode, careerCode);

        return modelAndView;
    }

    @GetMapping(path = "/{subjectCode}/teachers/add")
    public ModelAndView getAddTeacherPage(
            @PathVariable(value = "subjectCode") String code
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ADD_TEACHER_FORM_VIEWNAME);
        modelAndView.addObject(
                "listOfTeachers",
                teacherService.getTeachersByState(true)
        );
        modelAndView.addObject(
                "subjectSubmitted",
                subjectService.getSubjectByCode(code)
        );
        modelAndView.addObject(
                "allowEditing",
                false
        );

        return modelAndView;
    }

    @GetMapping(path = "/{subjectCode}/teachers")
    public ModelAndView getAddTeacherToSubjectPage(
            @PathVariable(value = "subjectCode") String subjectCode,
            @RequestParam(value = "teacherFile") String teacherFile
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        subjectService.addTeacherToSubject(subjectCode, teacherFile);

        return modelAndView;
    }

    @GetMapping(path = "/careers/delete/{careerCode}")
    public ModelAndView getDeleteCareerFromSubject(
            @PathVariable(value = "careerCode") String careerCode
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        subjectService.deleteCareerFromSubject(careerCode);

        return modelAndView;
    }

    @GetMapping(path = "/teachers/delete/{teacherFile}")
    public ModelAndView getDeleteTeacherFromSubject(
            @PathVariable(value = "teacherFile") String teacherFile
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        subjectService.deleteTeacherFromSubject(teacherFile);

        return modelAndView;
    }
}

package ar.edu.unju.fi.tp05grupo201.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.service.imp.CareerServiceImp;
import ar.edu.unju.fi.tp05grupo201.service.imp.SubjectServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path = "/career")
@AllArgsConstructor
public class CareerController {

    /**
     * View/Endpoint PATH constants
     */
    private final String LIST_VIEWNAME = "career/careers";
    private final String FORM_VIEWNAME = "career/career-form";
    private final String ADD_SUBJECT_FORM_VIEWNAME = "career/add-subject-to-career";
    private final String REDIRECT_TO_LIST_ENDPOINT = "redirect:/career/list";
    
    /**
     * Dependencies
     */
    private final CareerServiceImp careerService;
    private final SubjectServiceImp subjectService;
    
    /**
     * Endpoints
     */
    @GetMapping(path = "/list")
    public ModelAndView getCareers() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(LIST_VIEWNAME);
        modelAndView.addObject(
            "listOfCareers",
            careerService.getCareersByState(true)
        );

        return modelAndView;
    }
    
    /**
     * Add a career
     * @return
     */
    @GetMapping(path = "/add")
    public ModelAndView getAddCareerFormPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject("allowEditing", false);
        modelAndView.addObject(
            "careerSubmitted",
            careerService.createCareer()
        );

        return modelAndView;
    }
    
    /**
     * Save a career
     * @param careerDto
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/save")
    public ModelAndView postSaveCareerFormPage(
        @Valid @ModelAttribute(name = "careerSubmitted") CareerDto careerDto,
        BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject("allowEditing", false);
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            careerService.addCareer(careerDto);
        }

        return modelAndView;
    }

    /**
     * Retrieve a career by id to update
     * @param code
     * @return
     */
    @GetMapping(path = "/update/{careerId}")
    public ModelAndView getUpdateCareerFormPage(
        @PathVariable(value = "careerId") long careerId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject("allowEditing", true);
        modelAndView.addObject(
            "careerSubmitted",
            careerService.getCareerById(careerId)
        );

        return modelAndView;
    }

    /**
     * Update a career
     * @param careerDto
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/update")
    public ModelAndView postUpdateCareerFormPage(
        @Valid @ModelAttribute(value = "careerSubmitted") CareerDto careerDto,
        BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(FORM_VIEWNAME);
            modelAndView.addObject("allowEditing", true);
        } else {
            modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
            careerService.addCareer(careerDto);
        }

        return modelAndView;
    }
    
    /**
     * Delete (logically) a career 
     * @param code
     * @return
     */
    @GetMapping(path = "/delete/{code}")
    public ModelAndView getDeleteCareerPage(
        @PathVariable(value = "code") String code
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        careerService.deleteCareer(code);

        return modelAndView;
    }

    /**
     * Add a subject into a career retrieved by id
     * @param careerId
     * @return
     */
    @GetMapping(path = "/{careerId}/subjects/add")
    public ModelAndView getAddSubjectToCareerPage(
            @PathVariable(value = "careerId") long careerId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ADD_SUBJECT_FORM_VIEWNAME);
        
        modelAndView.addObject(
                "listOfSubjects",
                subjectService.getSubjectsByState(true)
        );
        modelAndView.addObject(
                "careerSubmitted",
                careerService.getCareerById(careerId)
        );
        modelAndView.addObject(
                "allowEditing",
                false
        );

        return modelAndView;
    }

    /**
     * Save a subject to a career
     * @param careerId
     * @param subjectId
     * @return
     */
    @GetMapping(path = "/{careerId}/subjects")
    public ModelAndView getSaveSubjectPage(
            @PathVariable(value = "careerId") long careerId,
            @RequestParam(value = "subjectId") long subjectId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        careerService.addSubjectToCareer(careerId, subjectId);

        return modelAndView;
    }

    /**
     * Delete a subject from a career
     * @param studentId
     * @param subjectId
     * @return
     */
    @GetMapping(path = "/{careerId}/subjects/{subjectId}/delete")
    public ModelAndView getDeleteSubjectFromCareerPage(
            @PathVariable(value = "careerId") long careerId,
            @PathVariable(value = "subjectId") long subjectId
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        careerService.deleteSubjectFromCareer(careerId, subjectId);

        return modelAndView;
    }
}
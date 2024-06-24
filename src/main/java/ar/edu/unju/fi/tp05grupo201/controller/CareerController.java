package ar.edu.unju.fi.tp05grupo201.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.service.imp.CareerServiceImp;
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
    private final String REDIRECT_TO_LIST_ENDPOINT = "redirect:/career/list";
    
    /**
     * Dependencies
     */
    private final CareerServiceImp careerService;
    
    /**
     * ENDPOINTS
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

    @GetMapping(path = "/update/{code}")
    public ModelAndView getUpdateCareerFormPage(
        @PathVariable(value = "code") String code
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(FORM_VIEWNAME);
        modelAndView.addObject("allowEditing", true);
        modelAndView.addObject(
            "careerSubmitted",
            careerService.getCareerByCode(code)
        );

        return modelAndView;
    }

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
    
    @GetMapping(path = "/delete/{code}")
    public ModelAndView getDeleteCareerPage(
        @PathVariable(value = "code") String code
    ) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(REDIRECT_TO_LIST_ENDPOINT);
        careerService.deleteCareer(code);

        return modelAndView;
    }
}
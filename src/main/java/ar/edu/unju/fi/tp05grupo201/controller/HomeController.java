package ar.edu.unju.fi.tp05grupo201.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    /**
     * Shows the home page of the project
     * @return
     */
    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }
}

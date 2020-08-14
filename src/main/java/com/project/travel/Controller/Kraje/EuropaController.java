package com.project.travel.Controller.Kraje;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EuropaController {
    @GetMapping("/europa")
    public String europa(){
        return "europa";
    }
}

package com.project.travel.Controller.Kraje;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AustraliaController {
    @GetMapping("/australia")
    public String australia(){
        return "australia";
    }
}

package com.project.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ZarejestrujController {
    @GetMapping("/zarejestruj")
    public String zarejestrujPage(){
        return "zarejestruj";
    }

}

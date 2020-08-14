package com.project.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BezpieczneWakacje {
    @GetMapping("/bezpieczne")
    public String bezpieczne(){
        return "bezpieczne";
    }
}

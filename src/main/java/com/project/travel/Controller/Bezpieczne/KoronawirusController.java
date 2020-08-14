package com.project.travel.Controller.Bezpieczne;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class KoronawirusController {
    @GetMapping("/koronawirus")
    public String koronawirusPage(){
        return "koronawirus";
    }
}

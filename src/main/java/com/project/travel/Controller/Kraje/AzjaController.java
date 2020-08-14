package com.project.travel.Controller.Kraje;

//import com.project.travel.Data.Continents;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AzjaController {
    @GetMapping("/azja")
    public String azja(){
        return "azja";
    }
//    public String azja(Model model){
//        model.addAllAttributes("azja", Continents.getListaPanstw);
//        return "azja";
    }


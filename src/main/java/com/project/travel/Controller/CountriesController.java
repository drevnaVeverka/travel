package com.project.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class CountriesController {

    @RequestMapping(value = "/countries")
    @ResponseBody
    public String getRegions(@RequestParam String country) {


        return "countries";
       // Map<String, Set<String>> regions = regionsService.getRegions();
      //  return regions.get(country);
    }

}

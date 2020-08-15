package com.project.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@Controller
public class CountriesController {

    @RequestMapping(value = "/countries")
    @ResponseBody
    public String getRegions(@RequestParam("depdrop_all_params[kontynenty]") int kontynenty) throws IOException, SQLException {
        System.out.println("aaaa zwracam countries, dla kontynent id:"+kontynenty);
       var json=new kierunkiController().getCountriesListAsJson(kontynenty);
        System.out.println("json:"+json);
        return json;

    }

}

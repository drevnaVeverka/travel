package com.project.travel.Controller;

import com.project.travel.Data.Database;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class kierunkiJsonController {

    @RequestMapping(value = "/countries")
    @ResponseBody
    public String getCountries(@RequestParam("depdrop_all_params[kontynenty]") int kontynenty) throws IOException, SQLException {
        System.out.println("aaaa zwracam countries, dla kontynent id:"+kontynenty);
        var json=getCountriesListAsJson(kontynenty);
        System.out.println("json:"+json);
        return json;

    }
/*
    @RequestMapping(value = "/hotels")
    @ResponseBody
    public String getHotels(@RequestParam("depdrop_all_params[kraje]") int country) throws IOException, SQLException {
        System.out.println("aaaa zwracam hotele, dla country id:"+country);
        var json=getHotelsListAsJson(country);
        System.out.println("json:"+json);
        return json;

    }

 */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public List<ContinentModel> getContinentsList() throws SQLException {

        String sqlStatement ="SELECT " +

                " \"Continent\".continent_id as continent_id," +
                   " \"Continent\".name\n as continent_name" +
                " FROM \"Continent\" \n";
        System.out.println(sqlStatement);



        var resultSet = Database
                .getConnection()
                .createStatement()
                .executeQuery(sqlStatement);

        List<ContinentModel> continentList = new ArrayList<>();
        while (resultSet.next()){
            continentList.add(new ContinentModel(
                    resultSet.getInt("continent_id"),
                   resultSet.getString("continent_name")
            ));
        }
        System.out.println(continentList);

        return continentList;
    }
/*
    private String getHotelsListAsJson(int country) {
        List<RoomModel> hotels = getHotels(country);


        var jsonCountriesInner = new JSONArray();

        for (RoomModel singleRoom: countriesList
        ) {
            jsonCountriesInner
                    .put(new JSONObject()
                            .put("id",singleRoom.countryId)
                            .put("name",singleRoom.country)
                    );
        }

        var jsonCountriesOuter=new JSONObject()
                .put("output",jsonCountriesInner)
                .put("selected","");


        return jsonCountriesOuter.toString();
    }


 */


    public String getCountriesListAsJson(int continentId) throws SQLException, IOException {
        List<RoomModel> roomsList = new kierunkiController().getDataBaseData(continentId, -1);

        List<RoomModel> countriesList= roomsList.stream().filter(distinctByKey(p -> p.getCountry())).collect(Collectors.toList());

        var jsonCountriesInner = new JSONArray();

        for (RoomModel singleRoom: countriesList
        ) {
            jsonCountriesInner
                    .put(new JSONObject()
                            .put("id",singleRoom.countryId)
                            .put("name",singleRoom.country)
                    );
        }

        var jsonCountriesOuter=new JSONObject()
                .put("output",jsonCountriesInner)
                .put("selected","");


        return jsonCountriesOuter.toString();

    }



}

class CountryModel
{
    public final int countryId;
    public final String country;

    CountryModel(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }
}
class ContinentModel
{
    public final int continentId;
    public final String continent;

    ContinentModel(int continentId, String continent) {
        this.continentId = continentId;
        this.continent = continent;
    }
}


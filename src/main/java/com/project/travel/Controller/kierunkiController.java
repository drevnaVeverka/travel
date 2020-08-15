package com.project.travel.Controller;

import com.project.travel.Data.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class kierunkiController {

    /**
     * select "Rooms".room_id, "Rooms".persons, "Rooms".price, "Hotels".name, "Hotels".description, "Country".name, "Continent".name
     * from "Rooms"
     * INNER JOIN "Hotels" ON "Hotels".hotel_id="Rooms".hotel_id
     * INNER JOIN "Country" ON "Country".country_id="Hotels".country_id
     * INNER JOIN "Continent" ON "Continent".continent_id="Country".continent_fki
     *
     * WHERE
     *
     * "Continent".continent_id = 2
     *
     * ;
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }


    @GetMapping("/kierunki")
    public void getHotelData(
            Model model,
            @RequestParam(defaultValue = "-1") int continentId,
            @RequestParam(defaultValue = "-1") int countryId
    ) throws SQLException {
        String sqlStatement ="SELECT" +
                " \"Rooms\".room_id," +
                " \"Country\".country_id," +
                " \"Continent\".continent_id," +
                " \"Rooms\".persons, \"Rooms\".price," +
                " \"Hotels\".name as hotel_name," +
                " \"Hotels\".description," +
                " \"Country\".name as country_name," +
                " \"Continent\".name\n as continent_name" +
                " FROM \"Rooms\" \n" +
                " INNER JOIN \"Hotels\" ON \"Hotels\".hotel_id=\"Rooms\".hotel_id\n" +
                " INNER JOIN \"Country\" ON \"Country\".country_id=\"Hotels\".country_id\n" +
                " INNER JOIN \"Continent\" ON \"Continent\".continent_id=\"Country\".continent_fki";

        if(continentId >= 0) {
            sqlStatement = appendWhere(sqlStatement, "\"Continent\".continent_id=" + continentId);
        }
        if(countryId >= 0){
            sqlStatement = appendWhere(sqlStatement, "\"Country\".country_id=" + countryId);
        }
        System.out.println(sqlStatement);



        var resultSet = Database
                .getConnection()
                .createStatement()
                .executeQuery(sqlStatement);

        List<RoomModel> roomsList = new ArrayList<>();
        while (resultSet.next()){
            roomsList.add(new RoomModel(
                    resultSet.getInt("room_id"),
                    resultSet.getInt("country_id"),
                    resultSet.getInt("continent_id"),
                    resultSet.getInt("persons"),
                    resultSet.getString("continent_name"),
                    resultSet.getString("country_name"),
                    resultSet.getString("hotel_name"),
                    resultSet.getString("price")


            ));
        }
        System.out.println(roomsList);

        List<RoomModel> countriesList= roomsList.stream().filter(distinctByKey(p -> p.getCountry())).collect(Collectors.toList());
        List<RoomModel> continentsList= roomsList.stream().filter(distinctByKey(p -> p.getContinent())).collect(Collectors.toList());

        System.out.println("---- continents list--- ");
        System.out.println(continentsList);

        System.out.println("---- countries list--- ");
        System.out.println(countriesList);

        model.addAttribute("roomsList", roomsList);
        model.addAttribute("continentsList", continentsList);
        model.addAttribute("countriesList", countriesList);



//        while(resultSet.next()) {
//            System.out.println(resultSet.);
//            System.out.println(resultSet.getString("hotel_id"));
//            System.out.println(resultSet.getDouble("price"));
//        }
    }

    private String appendWhere(String sql, String condition) {
        if(sql.contains("WHERE")){
            return sql + " AND " + condition;
        }else {
            return sql + " WHERE " + condition;
        }
    }
}

class RoomModel {
    public final int roomId;
    public final int countryId;
    public final int continentId;
    public final int persons;
    public final String continent;
    public final String country;
    public final String hotel_name;
    public final String price;



    RoomModel(int roomId, int countryId, int continentId, int persons, String continent, String country, String hotel_name,String price) {
        this.roomId = roomId;
        this.countryId=countryId;
        this.continentId=continentId;
        this.persons = persons;
        this.continent = continent;
        this.country = country;
        this.hotel_name = hotel_name;
        this.price = price;


    }

    public String getCountry()
    {
        return this.country;
    }

    public int getCountryId()
    {
        return this.countryId;
    }

    public String getContinent()
    {
        return this.continent;
    }

    public int getContinentId()
    {
        return this.continentId;
    }
    @Override
    public String toString() {
        return "RoomModel{" +
                "roomId=" + roomId +
                " countryId=" + countryId +
                " continentId=" + continentId +
                ", persons=" + persons +
                ", continent='" + continent + '\'' +
                ", country='" + country + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", price='" + price + '\'' +


                '}';
    }
}

// ORM
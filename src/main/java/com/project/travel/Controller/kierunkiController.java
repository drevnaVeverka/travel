package com.project.travel.Controller;

import com.project.travel.Data.Database;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.*;


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


    public  List<RoomModel> getDataBaseData(int continentId, int countryId ) throws SQLException
    {

        String sqlStatement ="SELECT " +
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
            System.out.println("asking only for continent_id="+continentId);

            sqlStatement = appendWhere(sqlStatement, "\"Continent\".continent_id=" + continentId);
        }
        if(countryId >= 0){
            System.out.println("asking only for country_id="+countryId);
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

        return roomsList;
    }

    @GetMapping("/kierunki")
    public void kierunki(
            Model model,
            @RequestParam(defaultValue = "-1") int continentId,
            @RequestParam(defaultValue = "-1") int countryId
    ) throws SQLException {

        List<RoomModel> roomsList = getDataBaseData(continentId,countryId);
        List<ContinentModel> continentsList = new kierunkiJsonController().getContinentsList();
        model.addAttribute("roomsList", roomsList);
        model.addAttribute("continentsList", continentsList);
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


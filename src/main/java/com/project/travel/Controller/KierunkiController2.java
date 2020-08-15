//package com.project.travel.Controller;
//
//import com.project.travel.Data.Database;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class KierunkiController2 {
//
//    /**
//     * select "Rooms".room_id, "Rooms".persons, "Rooms".price, "Hotels".name, "Hotels".description, "Country".name, "Continent".name
//     * from "Rooms"
//     * INNER JOIN "Hotels" ON "Hotels".hotel_id="Rooms".hotel_id
//     * INNER JOIN "Country" ON "Country".country_id="Hotels".country_id
//     * INNER JOIN "Continent" ON "Continent".continent_id="Country".continent_fki
//     *
//     * WHERE
//     *
//     * "Continent".continent_id = 2
//     *
//     * ;
//     */
//
//    @GetMapping("/kierunki2")
//    public void afryka(
//            Model model,
//            @RequestParam(defaultValue = "-1") int continentId,
//            @RequestParam(defaultValue = "-1") int countryId
//    ) throws SQLException {
//        String sqlStatement ="SELECT" +
//                " \"Rooms\".room_id, \"Rooms\".persons, \"Rooms\".price," +
//                " \"Hotels\".name, \"Hotels\".description," +
//                " \"Country\".name as country_name," +
//                " \"Continent\".name\n as continent_name" +
//                " FROM \"Rooms\" \n" +
//                " INNER JOIN \"Hotels\" ON \"Hotels\".hotel_id=\"Rooms\".hotel_id\n" +
//                " INNER JOIN \"Country\" ON \"Country\".country_id=\"Hotels\".country_id\n" +
//                " INNER JOIN \"Continent\" ON \"Continent\".continent_id=\"Country\".continent_fki";
//
//        if(continentId >= 0) {
//            sqlStatement = appendWhere(sqlStatement, "\"Continent\".continent_id=" + continentId);
//        }
//        if(countryId >= 0){
//            sqlStatement = appendWhere(sqlStatement, "\"Country\".country_id=" + countryId);
//        }
//        System.out.println(sqlStatement);
//
//
//
//        var resultSet = Database
//                .getConnection()
//                .createStatement()
//                .executeQuery(sqlStatement);
//
//        List<RoomModel> models = new ArrayList<>();
//        while (resultSet.next()){
//            models.add(new RoomModel(
//                    resultSet.getInt("room_id"),
//                    resultSet.getInt("persons"),
//                    resultSet.getString("price"),
//                    resultSet.getString("country_name"),
//                    resultSet.getString("continent_name")
//            ));
//        }
//        System.out.println(models);
//
//        model.addAttribute("rooms", models);
////        while(resultSet.next()) {
////            System.out.println(resultSet.);
////            System.out.println(resultSet.getString("hotel_id"));
////            System.out.println(resultSet.getDouble("price"));
////        }
//    }
//
//    private String appendWhere(String sql, String condition) {
//       if(sql.contains("WHERE")){
//           return sql + " AND " + condition;
//       }else {
//           return sql + " WHERE " + condition;
//       }
//    }
//}
//
//class RoomModel {
//    public final int roomId;
//    public final int persons;
//    public final String price;
//    public final String country;
//    public final String continent;
//
//    RoomModel(int roomId, int persons, String price, String country, String continent) {
//        this.roomId = roomId;
//        this.persons = persons;
//        this.price = price;
//        this.country = country;
//        this.continent = continent;
//    }
//
//    @Override
//    public String toString() {
//        return "RoomModel{" +
//                "roomId=" + roomId +
//                ", persons=" + persons +
//                ", price='" + price + '\'' +
//                ", country='" + country + '\'' +
//                ", continent='" + continent + '\'' +
//                '}';
//    }
//}
//
//// ORM
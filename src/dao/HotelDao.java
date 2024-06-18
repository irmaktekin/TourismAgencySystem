package dao;

import core.DbConnector;
import entity.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDao {
    private Connection connection;
    public HotelDao(){
        this.connection = DbConnector.getInstance();
    }
    public ArrayList<Hotel> getAllHotels(){
        ArrayList<Hotel> listHotel = new ArrayList<>();
        String query = "Select * From public.hotel Order By hotel_id";
        try{
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                listHotel.add(this.mapToHotel(rs));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return listHotel;
    }
    public Hotel mapToHotel(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotel_id(rs.getInt("hotel_id"));
        hotel.setHotel_name(rs.getString("hotel_name"));
        hotel.setAddress(rs.getString("address"));
        hotel.setMail(rs.getString("mail"));
        hotel.setPhone_number(rs.getInt("phone_number"));
        hotel.setStar(rs.getInt("star"));
        return hotel;


    }
}

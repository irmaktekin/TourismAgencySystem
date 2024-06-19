package dao;

import core.DbConnector;
import entity.HostelType;
import entity.Hotel;
import entity.User;

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
    public boolean updateHotel(Hotel hotel) {
        String query = "Update public.hotel Set " +
                "hotel_name = ? , " +
                "address = ? , " +
                "mail = ? , " +
                "phone_number = ? " +
                "star = ? " +
                "hostel_type = ? " +
                "Where hotel_id = ?";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1,hotel.getHotel_name());
            pr.setString(2, hotel.getAddress());
            pr.setString(3,hotel.getMail());
            pr.setInt(4,hotel.getPhone_number());
            pr.setInt(5,hotel.getStar());
           // pr.setString(6,hotel.getHostel_type().getHostelTypeInfo());
            pr.setInt(7,hotel.getHotel_id());
            return pr.executeUpdate() != -1;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean createHotel(Hotel hotel) {
        String query = "INSERT INTO public.hotel "+
                "(" +
                "hotel_name," +
                "address," +
                "mail," +
                "phone_number,"+
                "star," +
                ")"+
                " Values (?,?,?,?,?,?)";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1,hotel.getHotel_name());
            pr.setString(2, hotel.getAddress());
            pr.setString(3,hotel.getMail());
            pr.setInt(4,hotel.getPhone_number());
            pr.setInt(5,hotel.getStar());
            return pr.executeUpdate() != -1;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean deleteById(int id){
        String query = "Delete From public.hotel Where hotel_id = ?";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() !=-1;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public Hotel getById(int id){
        Hotel hotelObj = null;
        String query = "Select * From public.hotel where hotel_id = ?";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()) hotelObj = this.mapResultSetToHotel(rs);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return hotelObj;
    }

    public Hotel mapResultSetToHotel(ResultSet rs){
        Hotel hotel = new Hotel();
        try{
            hotel.setHotel_id(rs.getInt("hotel_id"));
            hotel.setHotel_name(rs.getString("hotel_name"));
            hotel.setMail(rs.getString("mail"));
            hotel.setAddress(rs.getString("address"));
            hotel.setPhone_number(rs.getInt("phone_number"));
            hotel.setStar(rs.getInt("star"));
           // hotel.setHostel_type(HostelType.valueOf(rs.getString("hostel_type")));
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return hotel;
    }
}

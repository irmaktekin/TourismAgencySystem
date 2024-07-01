package dao;
import core.DbConnector;
import entity.Hotel;
import entity.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ReservationDao {
    private Connection connection;

    public ReservationDao() {
        this.connection = DbConnector.getInstance();
    }
    public ArrayList<Reservation> getAllReservations(){
        ArrayList<Reservation> listRes = new ArrayList<>();
        String query = "Select * From public.reservation Order By reservation_id ";
        try{
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                listRes.add(this.mapToReservation(rs));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return listRes;
    }
    public Reservation mapToReservation(ResultSet rs) throws SQLException {
        Reservation res = new Reservation();
        res.setRes_id(rs.getInt("reservation_id"));
        res.setHotel_id(rs.getInt("hotel_id"));
        res.setRoomId(rs.getInt("room_id"));
        res.setCustomer_name(rs.getString("customer_name"));
        res.setMobile_phone(rs.getString("customer_mobile"));
        res.setChild_count(rs.getInt("child_count"));
        res.setAdult_count(rs.getInt("adult_count"));
        res.setTotal_price(rs.getFloat("total_price"));
        res.setEmail(rs.getString("email"));
        res.setStart_date(rs.getString("reservation_start"));
        res.setEnd_date(rs.getString("reservation_end"));


        return res;
    }
    public boolean createReservation (Reservation res, int hotelId, int roomId, String custname, int childCount, int adultCount, String email, LocalDate startDate, LocalDate endDate){
        int nightCount = 0;
        nightCount = (int)ChronoUnit.DAYS.between(startDate, endDate);

        //calculate the price according to the night,child, adult count.
        String roomPriceQuery = "SELECT adult_price, child_price FROM room WHERE room_id = ?";
        String query = "INSERT INTO public.reservation " +
                "(" +
                "hotel_id," +
                "room_id , " +
                "customer_name, " +
                "customer_mobile, " +
                "child_count, " +
                "adult_count, " +
                "total_price , " +
                "email, "+
                "reservation_start, "+
                "reservation_end"+
                ")"+
                " Values (?,?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement priceStatement = connection.prepareStatement(roomPriceQuery);
            priceStatement.setInt(1, roomId);
            ResultSet priceResultSet = priceStatement.executeQuery();
            //calculate the total price with the room price, guest count and the night count.
            double roomPriceAdult = 0.0f;
            double roomPriceChild = 0.0f;
            if (priceResultSet.next()) {
                roomPriceAdult = priceResultSet.getFloat("adult_price") ;
                roomPriceChild = priceResultSet.getFloat("child_price") ;

            } else {
                throw new SQLException("Room with ID " + roomId + " not found or price is not available.");
            }
            double totalPrice = ((roomPriceAdult*adultCount)+(roomPriceChild*childCount))*nightCount;
            PreparedStatement pr  = connection.prepareStatement(query);
            pr.setInt(1,hotelId);
            pr.setInt(2,roomId);
            pr.setString(3,custname);
            pr.setString(4,res.getMobile_phone());
            pr.setInt(5, childCount);
            pr.setInt(6,adultCount);
            pr.setDouble(7,totalPrice);
            pr.setString(8,email);
            pr.setString(9, String.valueOf(startDate));
            pr.setString(10, String.valueOf(endDate));


            updateRoomStock(roomId);

            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Decrement 1 for the stock of the room
    public boolean updateRoomStock(int roomId) {
        String updateQuery = "UPDATE room SET stock_count = stock_count - 1 WHERE room_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, roomId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteById(int id,int roomId){
        String query = "Delete From public.reservation Where reservation_id = ?";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,id);
            incrementRoomStock(roomId);
            return pr.executeUpdate() !=-1;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    private boolean incrementRoomStock(int roomId) {
        String updateQuery = "UPDATE room SET stock_count = stock_count + 1 WHERE room_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, roomId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateReservation(Reservation reservation,int selectedResId,LocalDate startDate,LocalDate endDate){
        String query = "Update public.reservation Set " +
                "customer_name = ? , " +
                "customer_mobile = ? , " +
                "child_count = ? , " +
                "adult_count = ? , " +
                "total_price = ? , " +
                "email = ? , "+
                "reservation_start = ? , "+
                "reservation_end = ?"+
                "Where reservation_id = ?";
        try{


            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1,reservation.getCustomer_name());
            pr.setString(2,reservation.getMobile_phone());
            pr.setInt(3,reservation.getChild_count());
            pr.setInt(4,reservation.getAdult_count());
            pr.setDouble(5,reservation.getTotal_price());
            pr.setString(6,reservation.getEmail());
            pr.setString(7, String.valueOf(startDate));
            pr.setString(8, String.valueOf(endDate));
            pr.setInt(9,selectedResId);

            return pr.executeUpdate() != -1;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public Reservation getById(int id){
        Reservation resObject = null;
        String query = "Select * From public.reservation where reservation_id = ?";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()) resObject = this.mapResultSetReservation(rs);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return resObject;
    }
    public Reservation mapResultSetReservation(ResultSet rs){
        Reservation reservation = new Reservation();
        try{
            reservation.setHotel_id(rs.getInt("hotel_id"));
            reservation.setCustomer_name(rs.getString("customer_name"));
            reservation.setMobile_phone(rs.getString("customer_mobile"));
            reservation.setChild_count(rs.getInt("child_count"));
            reservation.setAdult_count(rs.getInt("adult_count"));
            reservation.setRoomId(rs.getInt("room_id"));
            reservation.setTotal_price(rs.getDouble("total_price"));
            reservation.setEmail(rs.getString("email"));
            reservation.setStart_date(rs.getString("reservation_start"));
            reservation.setEnd_date(rs.getString("reservation_end"));


        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return reservation;
    }

}

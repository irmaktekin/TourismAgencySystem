package dao;
import core.DbConnector;
import entity.Hotel;
import entity.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDao {
    private Connection connection;

    public ReservationDao() {
        this.connection = DbConnector.getInstance();
    }
    public ArrayList<Reservation> getAllReservations(){
        ArrayList<Reservation> listRes = new ArrayList<>();
        String query = "Select * From public.reservation Order By reservation_id";
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
        res.setNightCount(rs.getInt("night_count"));
        res.setTotal_price(rs.getInt("total_price"));
        res.setEmail(rs.getString("email"));

        return res;
    }
    public boolean createReservation (Reservation res, int hotelId,int roomId,String custname,int nightCount,int childCount,int adultCount,String email){
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
                "night_count, " +
                "total_price , " +
                "email"+
                ")"+
                " Values (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement priceStatement = connection.prepareStatement(roomPriceQuery);
            priceStatement.setInt(1, roomId);
            ResultSet priceResultSet = priceStatement.executeQuery();
            //calculate the total price with the room price, guest count and the night count.
            float roomPriceAdult = 0.0f;
            float roomPriceChild = 0.0f;
            if (priceResultSet.next()) {
                roomPriceAdult = priceResultSet.getFloat("adult_price") ;
                roomPriceChild = priceResultSet.getFloat("child_price") ;

            } else {
                throw new SQLException("Room with ID " + roomId + " not found or price is not available.");
            }
            float totalPrice = ((roomPriceAdult*adultCount)+(roomPriceChild*childCount))*nightCount;
            PreparedStatement pr  = connection.prepareStatement(query);
            pr.setInt(1,hotelId);
            System.out.println("hotelid"+hotelId);
            pr.setInt(2,roomId);
            System.out.println("roomId"+roomId);
            pr.setString(3,custname);
            pr.setString(4,res.getMobile_phone());
            pr.setInt(5, childCount);
            pr.setInt(6,adultCount);
            pr.setInt(7,nightCount);
            pr.setFloat(8,totalPrice);
            pr.setString(9,email);
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

    public boolean updateReservation(Reservation reservation,int selectedResId){
        String query = "Update public.reservation Set " +
                "hotel_id = ? , " +
                "customer_name = ? , " +
                "customer_mobile = ? , " +
                "child_count = ? , " +
                "adult_count = ? , " +
                "night_count = ? , " +
                "total_price = ? , " +
                "e_mail = ?" +
                "Where reservation_id = ?";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,reservation.getHotel_id());
            pr.setString(2,reservation.getCustomer_name());
            pr.setString(3,reservation.getMobile_phone());
            pr.setInt(4,reservation.getChild_count());
            pr.setInt(5,reservation.getAdult_count());
            pr.setInt(6,reservation.getNightCount());
            pr.setFloat(7,reservation.getTotal_price());
            pr.setString(8,reservation.getEmail());
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
            reservation.setTotal_price(rs.getInt("total_price"));
            reservation.setEmail(rs.getString("email"));


        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return reservation;
    }

}

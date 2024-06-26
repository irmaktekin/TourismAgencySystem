package dao;
import core.DbConnector;
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
        res.setCustomer_name(rs.getString("customer_name"));
        res.setMobile_phone(rs.getString("customer_mobile"));
        res.setChild_count(rs.getInt("child_count"));
        res.setAdult_count(rs.getInt("adult_count"));
        res.setTotal_price(rs.getInt("total_price"));

        return res;
    }
    public boolean createReservation (Reservation res, int hotelId,int roomId,int nightCount,int childCount,int adultCount){
        //calculate the price according to the night,child, adult count.
        String roomPriceQuery = "SELECT adult_price, child_price FROM room WHERE room_id = ?";
        String query = "INSERT INTO public.reservation " +
                "(" +
                "hotel_id," +
                "customer_name, " +
                "customer_mobile, " +
                "child_count, " +
                "adult_count, " +
                "total_price" +
                ")"+
                " Values (?,?,?,?,?,?)";

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
            pr.setString(2,res.getCustomer_name());
            pr.setString(3,res.getMobile_phone());
            pr.setInt(4, res.getChild_count());
            pr.setInt(5,res.getAdult_count());
            pr.setFloat(6,totalPrice);
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
    public boolean deleteById(int id){
        String query = "Delete From public.reservation Where hotel_id = ?";
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
}

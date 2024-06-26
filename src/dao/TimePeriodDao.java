package dao;

import core.DbConnector;
import entity.TimePeriod;
import java.sql.*;
import java.time.LocalDate;

public class TimePeriodDao {
    private Connection connection;
    public TimePeriodDao(){
        this.connection = DbConnector.getInstance();
    }

    public TimePeriod getById(int id) {
        TimePeriod timePeriodObj = null;
        String query = "Select * From public.hotel_time_period where hotel_id = ?";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()) timePeriodObj = this.mapResultSetToTimePeriod(rs);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return timePeriodObj;
    }
    public TimePeriod mapResultSetToTimePeriod(ResultSet rs){
        TimePeriod timePeriodObj = new TimePeriod();
        try{
            timePeriodObj.setHotel_id(rs.getInt("hotel_id"));
            timePeriodObj.setStart_date1(LocalDate.parse(rs.getString("start_date1")));
            timePeriodObj.setEnd_date1(LocalDate.parse(rs.getString("end_date1")));
            timePeriodObj.setStart_date2(LocalDate.parse(rs.getString("start_date2")));
            timePeriodObj.setEnd_date2(LocalDate.parse(rs.getString("end_date2")));

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return timePeriodObj;
    }
}

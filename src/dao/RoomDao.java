package dao;

import core.DbConnector;
import entity.HostelType;
import entity.Room;
import entity.TimePeriod;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private Connection connection;

    public RoomDao() {
        this.connection = DbConnector.getInstance();
    }

    public List<TimePeriod> getTimePeriodsForHotel(int hotelId) {
        List<TimePeriod> timePeriods = new ArrayList<>();
        String query = "SELECT * FROM hotel_time_period WHERE hotel_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TimePeriod timePeriod = new TimePeriod();
                timePeriod.setTime_period_id(resultSet.getInt("hotel_time_period_id"));
                timePeriod.setStart_date1(resultSet.getDate("start_date1").toLocalDate());
                timePeriod.setEnd_date1(resultSet.getDate("end_date1").toLocalDate());
                timePeriod.setStart_date2(resultSet.getDate("start_date2").toLocalDate());
                timePeriod.setEnd_date2(resultSet.getDate("end_date2").toLocalDate());
                timePeriods.add(timePeriod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timePeriods;
    }

    public ArrayList<Room> getAllRooms() {
        ArrayList<Room> listRoom = new ArrayList<>();
        String query = "Select * From public.room Order By room_id";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                listRoom.add(this.mapToRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRoom;
    }

    public Room mapToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoom_id(rs.getInt("room_id"));
        room.setHotel_id(rs.getInt("hotel_id"));
        room.setStock_count(rs.getInt("stock_count"));
        room.setBed_count(rs.getInt("bed_count"));
        room.setSquare_meters(rs.getInt("square_meters"));
        room.setHostel_type_id(rs.getInt("hostel_type_id"));
        room.setRoomType(rs.getString("room_type"));
        room.setTime_period_id(rs.getInt("time_period_id"));
        room.setAdult_price(rs.getFloat("adult_price"));
        room.setChild_price(rs.getFloat("child_price"));
        room.setTv_available(rs.getBoolean("tv"));
        room.setMinibar_available(rs.getBoolean("minibar"));
        room.setConsole_available(rs.getBoolean("console"));
        room.setSafe_available(rs.getBoolean("safe"));
        room.setProjector_available(rs.getBoolean("projector"));

        return room;
    }

    public boolean createRoom (Room room, int hotelId,int timePeriodId,String tv,String minibar,String console,String safe,String projector){
        String query = "INSERT INTO public.room " +
                "(" +
                "hotel_id," +
                "hostel_type_id, " +
                "room_type, " +
                "time_period_id, "+
                "adult_price, " +
                "child_price, " +
                "stock_count, " +
                "bed_count, " +
                "square_meters, " +
                "tv, " +
                "minibar, " +
                "console, " +
                "safe, "+
                "projector" +
                ")"+
                " Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr  = connection.prepareStatement(query);
            pr.setInt(1,hotelId);
            pr.setInt(2,room.getHostel_type_id());
            pr.setString(3,room.getRoomType());
            pr.setInt(4, timePeriodId);
            pr.setFloat(5,room.getAdult_price());
            pr.setFloat(6,room.getChild_price());
            pr.setInt(7,room.getStock_count());
            pr.setInt(8,room.getBed_count());
            pr.setDouble(9,room.getSquare_meters());
            pr.setString(10,tv);
            pr.setString(11,minibar);
            pr.setString(12,console);
            pr.setString(13,safe);
            pr.setString(14,projector);

            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<HostelType> getHostelTypesForHotel(int hotelId) {
        List<HostelType> hostelTypes = new ArrayList<>();
        String query = "SELECT ht.hostel_type_id, ht.hostel_type_name FROM hostel_type ht " +
                        "JOIN hotel_hostel_type hht ON ht.hostel_type_id = hht.hostel_type_id " +
                        "WHERE hht.hotel_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String typeName = rs.getString("hostel_type_name");
                HostelType hostelType = mapToHostelType(typeName);
                hostelTypes.add(hostelType);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hostelTypes;
    }

    private HostelType mapToHostelType(String typeName) {
        for (HostelType type : HostelType.values()) {
            if (type.getHostelTypeInfo().equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        return null; // Return null if no matching enum constant is found
    }

    public ArrayList<Room> searchForReservation(String hotelLocation, LocalDate startDate, LocalDate endDate,int customerCount) {
        int parameterIndex = 1;
        ArrayList<Room> rooms = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        String query = "SELECT r.*, tp.*, h.address " +
                        "FROM room r " +
                        "INNER JOIN public.hotel h ON r.hotel_id = h.hotel_id " +
                        "INNER JOIN public.hotel_time_period tp ON h.hotel_id = tp.hotel_id";

        if(hotelLocation != null && !hotelLocation.isEmpty()){
            conditions.add("h.address = ?");
        }
        if(customerCount>0){
            conditions.add("r.bed_count >= ?");
        }
        if(startDate!=null){
            conditions.add("(tp.start_date1 <= ? OR tp.start_date2 <= ?)");
        }
        if(endDate != null){
            conditions.add("(tp.end_date1 >= ? OR tp.end_date2 >= ?)");
        }
        if(!conditions.isEmpty()){
            query += " WHERE " + String.join(" AND ",conditions);
        }
        try(PreparedStatement st = connection.prepareStatement(query)){
            if(customerCount>0){
                st.setInt(parameterIndex++,customerCount);
            }
            if(hotelLocation!=null && !hotelLocation.isEmpty()){
                st.setString(parameterIndex++,hotelLocation);
            }
            if(startDate!=null){
                st.setDate(parameterIndex++,java.sql.Date.valueOf(startDate));
                st.setDate(parameterIndex++, java.sql.Date.valueOf(startDate)); // Second parameter for start_date2

            }
            if(endDate!=null){
                st.setDate(parameterIndex++,java.sql.Date.valueOf(endDate));
                st.setDate(parameterIndex++, java.sql.Date.valueOf(endDate)); // Second parameter for start_date2

            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoom_id(rs.getInt("room_id"));
                room.setHotel_id(rs.getInt("hotel_id"));
                room.setAddress(rs.getString("address"));
                room.setBed_count(rs.getInt("bed_count"));
                room.setStock_count(rs.getInt("stock_count"));
                room.setSquare_meters(rs.getInt("square_meters"));
                room.setTv_available(rs.getBoolean("tv"));
                room.setConsole_available(rs.getBoolean("console"));
                room.setProjector_available(rs.getBoolean("projector"));
                room.setMinibar_available(rs.getBoolean("minibar"));

                TimePeriod timePeriod = new TimePeriod();
                timePeriod.setTime_period_id(rs.getInt("time_period_id"));
                timePeriod.setStart_date1(rs.getDate("start_date1").toLocalDate());
                timePeriod.setEnd_date1(rs.getDate("end_date1").toLocalDate());
                timePeriod.setStart_date2(rs.getDate("start_date2").toLocalDate());
                timePeriod.setEnd_date2(rs.getDate("end_date2").toLocalDate());

                rooms.add(room);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return rooms;
    }
}


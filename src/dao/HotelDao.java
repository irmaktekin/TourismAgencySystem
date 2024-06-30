package dao;

import core.DbConnector;
import entity.Hotel;
import entity.TimePeriod;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        hotel.setPhone_number(rs.getString("phone_number"));
        hotel.setStar(rs.getInt("star"));

        return hotel;
    }

    public TimePeriod updateHotel(Hotel hotel,TimePeriod timePeriod) {
        String query = "Update public.hotel Set " +
                "hotel_name = ? , " +
                "address = ? , " +
                "mail = ? , " +
                "phone_number = ? , " +
                "star = ? " +
                "Where hotel_id = ?";

        String timePeriodQuery = "Update public.hotel_time_period Set "+
                "start_date1 = ? , " +
                "end_date1 = ? , " +
                "start_date2 = ? , " +
                "end_date2 = ? " +
                "Where hotel_id = ?";
        try(PreparedStatement hotelStatement = connection.prepareStatement(query);
            PreparedStatement timePeriodStatement = connection.prepareStatement(timePeriodQuery)){

            hotelStatement.setString(1,hotel.getHotel_name());
            hotelStatement.setString(2, hotel.getAddress());
            hotelStatement.setString(3,hotel.getMail());
            hotelStatement.setString(4,hotel.getPhone_number());
            hotelStatement.setInt(5,hotel.getStar());
            hotelStatement.setInt(6,hotel.getHotel_id());

            int rowsAffectedHotel = hotelStatement.executeUpdate();

            timePeriodStatement.setDate(1, java.sql.Date.valueOf(timePeriod.getStart_date1()));
            timePeriodStatement.setDate(2, java.sql.Date.valueOf(timePeriod.getEnd_date1()));
            timePeriodStatement.setDate(3, java.sql.Date.valueOf(timePeriod.getStart_date2()));
            timePeriodStatement.setDate(4, java.sql.Date.valueOf(timePeriod.getEnd_date2()));
            timePeriodStatement.setInt(5, hotel.getHotel_id());

            int rowsAffectedTimePeriod = timePeriodStatement.executeUpdate();
            if( rowsAffectedHotel > 0 && rowsAffectedTimePeriod > 0){
                return getTimePeriodById(hotel.getHotel_id());
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public TimePeriod createHotel(Hotel hotel, List<String> selectedHostelTypes, List<String> selectedFacilities,TimePeriod timePeriod) {
        List<Integer> hostelTypeIds = new ArrayList<>();
        List <Integer> facilityIds = new ArrayList<>();

        String selectHostelTypeQuery = "SELECT hostel_type_id FROM public.hostel_type WHERE hostel_type_name = ?";

        String selectFacilityQuery  = "SELECT facility_id from public.facility where facility_name = ?";

        String query = "INSERT INTO public.hotel "+
                "(" +
                "hotel_name," +
                "address," +
                "mail," +
                "phone_number,"+
                "star" +
                ")"+
                " Values (?,?,?,?,?) RETURNING hotel_id";
        try(PreparedStatement pr = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement prHostelType = connection.prepareStatement(selectHostelTypeQuery);
            PreparedStatement prFacility = connection.prepareStatement(selectFacilityQuery);

            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO hotel_hostel_type (hotel_id, hostel_type_id) VALUES (?, ?)");
            PreparedStatement insertFacility = connection.prepareStatement("INSERT INTO hotel_facility (hotel_id, facility_id) VALUES (?, ?)")){

            pr.setString(1,hotel.getHotel_name());
            pr.setString(2, hotel.getAddress());
            pr.setString(3,hotel.getMail());
            pr.setString(4,hotel.getPhone_number());
            pr.setInt(5,hotel.getStar());

            int rowInserted = pr.executeUpdate();

            ResultSet generatedKeys = pr.getGeneratedKeys();
                int hotelId ;
                if (generatedKeys.next()) {
                    hotelId = generatedKeys.getInt(1);
                    hotel.setHotel_id(hotelId);
                    // Set the hotel_id in your Hotel object
                    hotel.setHotel_id(hotelId);
                } else {
                    throw new SQLException("Failed to get auto-generated hotel_id.");
                }

            for(String hostelTypeName : selectedHostelTypes){
                prHostelType.setString(1,hostelTypeName);
                ResultSet rs =prHostelType.executeQuery();
                if(rs.next()){
                    int hostelTypeId = rs.getInt("hostel_type_id");
                    hostelTypeIds.add(hostelTypeId);
                    insertStatement.setInt(1,hotelId);
                    insertStatement.setInt(2,hostelTypeId);
                    insertStatement.executeUpdate();
                }
            }
            for(String facilityName : selectedFacilities){
                prFacility.setString(1,facilityName);
                ResultSet rs = prFacility.executeQuery();
                if(rs.next()){
                    int facilityId = rs.getInt("facility_id");
                    facilityIds.add(facilityId);
                    insertFacility.setInt(1,hotelId);
                    insertFacility.setInt(2,facilityId);
                    insertFacility.executeUpdate();
                }
            }

            String queryTimePeriod = "INSERT INTO public.hotel_time_period " +
                    "(" +
                    "hotel_id," +
                    "start_date1, " +
                    "end_date1, " +
                    "start_date2, "+
                    "end_date2" +
                    ")"+
                        " Values (?,?,?,?,?)";

            try {
                PreparedStatement timePeriodQuery  = connection.prepareStatement(queryTimePeriod);
                timePeriodQuery.setInt(1,hotel.getHotel_id());
                timePeriodQuery.setDate(2, Date.valueOf(timePeriod.getStart_date1()));
                timePeriodQuery.setDate(3,Date.valueOf(timePeriod.getEnd_date1()));
                timePeriodQuery.setDate(4,Date.valueOf(timePeriod.getStart_date2()));
                timePeriodQuery.setDate(5,Date.valueOf(timePeriod.getEnd_date2()));

                timePeriodQuery.executeUpdate();
                return timePeriod;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println();
        }
        return null;
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
            hotel.setPhone_number(rs.getString("phone_number"));
            hotel.setStar(rs.getInt("star"));


        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return hotel;
    }
    private TimePeriod getTimePeriodById(int hotelId) throws SQLException {
        String query = "SELECT * FROM public.hotel_time_period WHERE hotel_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                TimePeriod timePeriod = new TimePeriod();
                timePeriod.setTime_period_id(rs.getInt("hotel_time_period_id"));
                timePeriod.setHotel_id(rs.getInt("hotel_id"));
                timePeriod.setStart_date1(rs.getDate("start_date1").toLocalDate());
                timePeriod.setEnd_date1(rs.getDate("end_date1").toLocalDate());
                timePeriod.setStart_date2(rs.getDate("start_date2").toLocalDate());
                timePeriod.setEnd_date2(rs.getDate("end_date2").toLocalDate());
                return timePeriod;
            }
        }
        return null;
    }
    public List<Integer> getHostelTypeIdsForHotel(int hotelId) throws SQLException {
        List<Integer> hostelTypeIds = new ArrayList<>();
        String query = "SELECT hostel_type_id FROM hotel_hostel_type WHERE hotel_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int hostelTypeId = resultSet.getInt("hostel_type_id");
                hostelTypeIds.add(hostelTypeId);
            }
        }
        return hostelTypeIds;
    }
}

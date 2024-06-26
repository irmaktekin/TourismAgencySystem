package dao;

import core.DbConnector;
import entity.Facility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilityDao {
    private Connection connection;
    public FacilityDao(){
        this.connection= DbConnector.getInstance();
    }
    public ArrayList<String> getNamesFromDatabase() {
        ArrayList<String> facilityNames = new ArrayList<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("Select facility_name from public.facility");
            while (rs.next()) {
                String faciltyName = rs.getString("facility_name");
                facilityNames.add(faciltyName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facilityNames;
    }

    public List<Facility> getFacilitiesForHotel(int hotelId) {
        List<Facility> facilities = new ArrayList<>();
        String query = "SELECT f.facility_id, f.facility_name " +
                "FROM facility f " +
                "JOIN hotel_facility fh ON f.facility_id = fh.facility_id " +
                "WHERE fh.hotel_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int facilityId = resultSet.getInt("facility_id");
                String facilityName = resultSet.getString("facility_name");
                Facility facility = new Facility(facilityId, facilityName);
                facilities.add(facility);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
        return facilities;
    }
}

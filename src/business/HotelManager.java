package business;

import dao.HotelDao;
import entity.Hotel;
import entity.TimePeriod;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelManager {
    private final HotelDao hotelDao;

    public HotelManager() {
        this.hotelDao = new HotelDao();
    }

    public ArrayList<Hotel> getAllHotels() {
        return this.hotelDao.getAllHotels();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Hotel> hotels) {
        ArrayList<Object[]> hotel_List = new ArrayList<>();
        for (Hotel obj : hotels) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getHotel_name();
            rowObject[i++] = obj.getAddress();
            rowObject[i++] = obj.getMail();
            rowObject[i++] = obj.getPhone_number();
            rowObject[i++] = obj.getStar();
            hotel_List.add(rowObject);

        }
        return hotel_List;
    }

    public TimePeriod update(Hotel hotel, TimePeriod timePeriod) {
        return this.hotelDao.updateHotel(hotel, timePeriod);
    }

    public TimePeriod create(Hotel hotel, List<String> selectedHostelTypes, List<String> selectedFacilities, TimePeriod timePeriod) {
        return this.hotelDao.createHotel(hotel, selectedHostelTypes, selectedFacilities, timePeriod);
    }

    public boolean deleteById(int id) {
        return this.hotelDao.deleteById(id);
    }

    public Hotel getById(int id) {
        return this.hotelDao.getById(id);
    }

    public List<Integer> getHostelTypesForHotel(int hotelId) throws SQLException {
        return this.hotelDao.getHostelTypeIdsForHotel(hotelId);
    }
}

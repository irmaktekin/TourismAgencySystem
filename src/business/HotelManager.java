package business;

import dao.HotelDao;
import entity.Hotel;
import entity.User;

import java.util.ArrayList;

public class HotelManager {
    private final HotelDao hotelDao;
    public HotelManager(){
        this.hotelDao = new HotelDao();
    }

    public ArrayList<Hotel> getAllHotels(){
        return this.hotelDao.getAllHotels();
    }
    public ArrayList<Object[]> getForTable(int size,ArrayList<Hotel> hotels){
        ArrayList<Object[]> hotel_List = new ArrayList<>();
        for(Hotel obj : hotels){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getHotel_name();
            rowObject[i++] = obj.getAddress();
            rowObject[i++] = obj.getMail();
            rowObject[i++] = obj.getPhone_number();
            rowObject[i++] = obj.getStar();
            rowObject[i++] = obj.getHostel_type();

            hotel_List.add(rowObject);

        }
        return hotel_List;
    }
}

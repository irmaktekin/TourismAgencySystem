package business;

import dao.FacilityDao;
import entity.Facility;
import java.util.ArrayList;
import java.util.List;

public class FacilityManager {

    private FacilityDao facilityDao;

    public FacilityManager(){
        this.facilityDao = new FacilityDao();
    }
    public ArrayList<String> getNamesFromDatabase(){
        return facilityDao.getNamesFromDatabase();
    }
    public List<Facility> getFacilitiesForHotel(int hotelId){
        return facilityDao.getFacilitiesForHotel(hotelId);
    }
}

package business;

import dao.RoomDao;
import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private final RoomDao roomDao;

    public RoomManager(){
        this.roomDao = new RoomDao();
    }
    public ArrayList<Room> getAllHotels() {
        return this.roomDao.getAllRooms();
    }
    public boolean create(Room room,int hotelId, int timePeriodId,String tv,String minibar,String console,String safe,String projector){
        return this.roomDao.createRoom(room,hotelId,timePeriodId,tv, minibar, console, safe, projector);
    }

    public List<HostelType> getHostelTypesForHotel(int hotelId) {
        return this.roomDao.getHostelTypesForHotel(hotelId);
    }
    public List<TimePeriod> getTimePeriodsForHotel(int hotelId){
        System.out.println("get time periods manager");
        return this.roomDao.getTimePeriodsForHotel(hotelId);
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> rooms) {
        ArrayList<Object[]> room_List = new ArrayList<>();
        for (Room obj : rooms) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getRoom_id();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getStock_count();
            rowObject[i++] = obj.getBed_count();
            rowObject[i++] = obj.getSquare_meters();
            rowObject[i++] = obj.isTv_available();
            rowObject[i++] = obj.isMinibar_available();
            rowObject[i++] = obj.isConsole_available();
            rowObject[i++] = obj.isSafe_available();
            rowObject[i++] = obj.isProjector_available();
            rowObject[i++] = obj.getHotel_name();


            room_List.add(rowObject);
        }
        return room_List;
    }
    public ArrayList<Room> searchForTable(String hotelLocation, LocalDate startDate, LocalDate endDate,Integer customerCount,String hotelName){
        return this.roomDao.searchForReservation( hotelLocation,  startDate,  endDate, customerCount,hotelName);
    }

}

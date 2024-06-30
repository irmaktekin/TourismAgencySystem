package business;

import dao.ReservationDao;
import entity.Reservation;

import java.util.ArrayList;

public class ReservationManager {
    private final ReservationDao resDao;

    public ReservationManager() {
        this.resDao = new ReservationDao();
    }
    public ArrayList<Reservation> getAllReservations() {
        return this.resDao.getAllReservations();
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservations) {
        ArrayList<Object[]> res_List = new ArrayList<>();
        for (Reservation obj : reservations) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getRes_id();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getCustomer_name();
            rowObject[i++] = obj.getMobile_phone();
            rowObject[i++] = obj.getChild_count();
            rowObject[i++] = obj.getAdult_count();
            rowObject[i++] = obj.getTotal_price();
            res_List.add(rowObject);

        }
        return res_List;
    }
    public boolean create(Reservation res, int hotelId,int roomId,int nightCount, int child_price,int adult_price){
        return this.resDao.createReservation(res, hotelId,roomId,nightCount,child_price,adult_price);
    }
    public boolean deleteById(int id) {
        return this.resDao.deleteById(id);
    }
    public boolean update(int selectedResId, Reservation res) {
        return this.resDao.updateReservation(res,selectedResId);
    }
    public Reservation getById(int id) {
        return this.resDao.getById(id);
    }

}

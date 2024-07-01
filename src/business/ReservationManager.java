package business;

import dao.ReservationDao;
import entity.Reservation;

import java.sql.Date;
import java.time.LocalDate;
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
            rowObject[i++] = obj.getRoomId();
            rowObject[i++] = obj.getCustomer_name();
            rowObject[i++] = obj.getMobile_phone();
            rowObject[i++] = obj.getChild_count();
            rowObject[i++] = obj.getAdult_count();
            rowObject[i++] = obj.getTotal_price();
            rowObject[i++] = obj.getEmail();
            rowObject[i++] = obj.getStart_date();
            rowObject[i++] = obj.getEnd_date();

            res_List.add(rowObject);

        }
        return res_List;
    }
    public boolean create(Reservation res, int hotelId, int roomId, String custName, int child_price, int adult_price, String email, LocalDate startDate, LocalDate endDate ){
        return this.resDao.createReservation(res, hotelId,roomId,custName,child_price,adult_price,email,startDate,endDate);
    }
    public boolean deleteById(int id,int roomId) {
        return this.resDao.deleteById(id,roomId);
    }
    public boolean update(int selectedResId, Reservation res,LocalDate startDate,LocalDate endDate) {
        return this.resDao.updateReservation(res,selectedResId,startDate,endDate);
    }
    public Reservation getById(int id) {
        return this.resDao.getById(id);
    }

}

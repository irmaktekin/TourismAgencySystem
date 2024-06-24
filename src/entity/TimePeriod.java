package entity;

import java.sql.Date;

public class TimePeriod {
    private int hotel_id;
    private java.sql.Date start_date1;
    private java.sql.Date end_date1;
    private java.sql.Date start_date2;
    private java.sql.Date end_date2;

    public Date getEnd_date2() {
        return end_date2;
    }

    public void setEnd_date2(Date end_date2) {
        this.end_date2 = end_date2;
    }

    public Date getStart_date2() {
        return start_date2;
    }

    public void setStart_date2(Date start_date2) {
        this.start_date2 = start_date2;
    }

    public Date getEnd_date1() {
        return end_date1;
    }

    public void setEnd_date1(Date end_date1) {
        this.end_date1 = end_date1;
    }

    public Date getStart_date1() {
        return start_date1;
    }

    public void setStart_date1(Date start_date1) {
        this.start_date1 = start_date1;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }
}

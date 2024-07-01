package entity;

import java.time.LocalDate;

public class TimePeriod {
    private int time_period_id;
    private int hotel_id;
    private LocalDate start_date1;
    private LocalDate end_date1;
    private LocalDate start_date2;

    public int getTime_period_id() {
        return time_period_id;
    }

    public void setTime_period_id(int time_period_id) {
        this.time_period_id = time_period_id;
    }

    private LocalDate end_date2;

    public LocalDate getEnd_date2() {
        return end_date2;
    }

    public void setEnd_date2(LocalDate end_date2) {
        this.end_date2 = end_date2;
    }

    public LocalDate getStart_date2() {
        return start_date2;
    }

    public void setStart_date2(LocalDate start_date2) {
        this.start_date2 = start_date2;
    }

    public LocalDate getEnd_date1() {
        return end_date1;
    }

    public void setEnd_date1(LocalDate end_date1) {
        this.end_date1 = end_date1;
    }

    public LocalDate getStart_date1() {
        return start_date1;
    }

    public void setStart_date1(LocalDate start_date1) {
        this.start_date1 = start_date1;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }
    @Override
    public String toString() {
        return start_date1.toString() + " - " + end_date1.toString();
    }

    public String toString2() {
        return start_date2.toString() + " - " + end_date2.toString();
    }
}
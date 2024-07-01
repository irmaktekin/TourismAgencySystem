
package entity;

import java.sql.Time;

public class Room {
    private int room_id;
    private int hotel_id;
    private int hostel_type_id;
    Hotel hotel;
    TimePeriod timePeriod;
    private float child_price;
    private int stock_count;
    private int bed_count;
    private float square_meters;
    private String roomType;
    private boolean tv_available;
    private boolean minibar_available;
    private boolean console_available;
    private boolean safe_available;
    private boolean projector_available;

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    private transient String hotel_name;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private int time_period_id;
    private float adult_price;
    private String address;
    // Add a field for storing address temporarily

    public boolean isProjector_available() {
        return projector_available;
    }

    public void setProjector_available(boolean projector_available) {
        this.projector_available = projector_available;
    }

    public boolean isMinibar_available() {
        return minibar_available;
    }

    public void setMinibar_available(boolean minibar_available) {
        this.minibar_available = minibar_available;
    }

    public boolean isConsole_available() {
        return console_available;
    }

    public void setConsole_available(boolean console_available) {
        this.console_available = console_available;
    }

    public boolean isSafe_available() {
        return safe_available;
    }

    public void setSafe_available(boolean safe_available) {
        this.safe_available = safe_available;
    }

    public boolean isTv_available() {
        return tv_available;
    }

    public void setTv_available(boolean tv_available) {
        this.tv_available = tv_available;
    }

    public float getSquare_meters() {
        return square_meters;
    }

    public void setSquare_meters(float square_meters) {
        this.square_meters = square_meters;
    }

    public int getBed_count() {
        return bed_count;
    }

    public void setBed_count(int bed_count) {
        this.bed_count = bed_count;
    }

    public int getStock_count() {
        return stock_count;
    }

    public void setStock_count(int stock_count) {
        this.stock_count = stock_count;
    }

    public float getChild_price() {
        return child_price;
    }

    public void setChild_price(float child_price) {
        this.child_price = child_price;
    }

    public float getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(float adult_price) {
        this.adult_price = adult_price;
    }

    public int getTime_period_id() {
        return time_period_id;
    }

    public void setTime_period_id(int time_period_id) {
        this.time_period_id = time_period_id;
    }

    public int getHostel_type_id() {
        return hostel_type_id;
    }

    public void setHostel_type_id(int hostel_type_id) {
        this.hostel_type_id = hostel_type_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

}

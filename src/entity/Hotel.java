package entity;

import core.ComboItem;

public class Hotel {
    private int hotel_id;
    private int hostel_type_id;
    private String hotel_name;
    private String address;
    private String mail;
    private String phone_number;
    private Integer star;
    private String hostel_type;

    public int getHostel_type_id() {
        return hostel_type_id;
    }

    public void setHostel_type_id(int hostel_type_id) {
        this.hostel_type_id = hostel_type_id;
    }

    public String getHostel_type() {
        return hostel_type;
    }

    public void setHostel_type(String hostel_type) {
        this.hostel_type = hostel_type;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Hotel() {
    }

    public Hotel(int hotel_id, String hostel_type, Integer star, String phone_number, String mail, String address, String hotel_name) {
        this.hotel_id = hotel_id;
        this.hostel_type = hostel_type;
        this.star = star;
        this.phone_number = phone_number;
        this.mail = mail;
        this.address = address;
        this.hotel_name = hotel_name;
    }

    public ComboItem getComboItem(){
        return new ComboItem(this.getHotel_id(),this.getStar().toString());
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotel_id=" + hotel_id +
                ", hotel_name='" + hotel_name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + mail + '\'' +
                ", phone_number=" + phone_number +
                ", star=" + star +
                ", hostel_type='" + hostel_type + '\'' +
                '}';
    }
}

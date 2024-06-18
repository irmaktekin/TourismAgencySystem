package entity;

public class Hotel {
    private int hotel_id;
    private String hotel_name;
    private String address;
    private String mail;
    private int phone_number;
    private int star;

    public int getHostel_type() {
        return hostel_type;
    }

    public void setHostel_type(int hostel_type) {
        this.hostel_type = hostel_type;
    }

    private int hostel_type;


    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }



    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
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
}

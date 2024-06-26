package view;

import business.ReservationManager;
import core.Helper;
import entity.*;
import javax.swing.*;

public class EditReservationView extends Layout{
    private JTextField fld_cust_name;
    private JTextField fld_mobile;
    private JTextField fld_mail;
    private JPanel container;
    private JLabel lbl_reservation;
    private JLabel lbl_night_count;
    private JTextField fld_night_count;
    private JLabel count_child;
    private JTextField fld_child_count;
    private JTextField fld_adult_count;
    private JPanel pnl_details;
    private JButton btn_createres;
    private JTextField fld_total_price;
    private  User user;
    private Room room;
    private int hotelId;
    private Hotel hotel;
    private int roomId;
    private Reservation res;
    private ReservationManager reservationManager;

    public EditReservationView(User user, TimePeriod timePeriod, Room room, Reservation res,int hotelId,int roomId){
        this.user=user;
        this.room = room;
        this.res = res;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.add(container);
        this.initalizeGui(600,500);
        reservationManager = new ReservationManager();

        //When create button click, reservation is created.
        btn_createres.addActionListener(e-> {
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_adult_count,this.fld_child_count,this.fld_mail,this.fld_mobile,this.fld_night_count,this.fld_cust_name})){
                Helper.displayMessage("fill");
            }
            else{
                this.res.setCustomer_name(fld_cust_name.getText());
                this.res.setMobile_phone(fld_mobile.getText());
                this.res.setAdult_count(Integer.parseInt(fld_adult_count.getText()));
                this.res.setChild_count(Integer.parseInt(fld_child_count.getText()));
            }
            if(this.res.getRes_id() ==0){
                this.reservationManager.create(res,hotelId,roomId, Integer.parseInt(fld_night_count.getText()),Integer.parseInt(fld_adult_count.getText()),Integer.parseInt(fld_child_count.getText()));
            }
        });

    }

}

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
    private int resId;
    private Reservation res;
    private ReservationManager reservationManager;

    public EditReservationView(User user, TimePeriod timePeriod, Room room, Reservation res,int hotelId,int roomId,int selectedResId){
        this.user=user;
        this.room = room;
        this.res = res;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.resId = selectedResId;
        this.add(container);
        this.initalizeGui(600,500);
        reservationManager = new ReservationManager();

        //set the field values for update
        if(selectedResId!=0){
            this.fld_cust_name.setText(res.getCustomer_name());
            this.fld_child_count.setText(res.getChild_count().toString());
            this.fld_adult_count.setText(res.getAdult_count().toString());
            this.fld_mobile.setText(res.getMobile_phone());
        }

        //When create button click, reservation is created.
        btn_createres.addActionListener(e-> {
            boolean result = false;
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_adult_count,this.fld_child_count,this.fld_mail,this.fld_mobile,this.fld_night_count,this.fld_cust_name})){
                Helper.displayMessage("fill");
            }
            else{
                this.res.setCustomer_name(fld_cust_name.getText());
                this.res.setMobile_phone(fld_mobile.getText());
                this.res.setAdult_count(Integer.parseInt(fld_adult_count.getText()));
                this.res.setChild_count(Integer.parseInt(fld_child_count.getText()));
            }
            System.out.println("selectedresId"+selectedResId);
            if(selectedResId == 0){
                this.reservationManager.create(res,hotelId,roomId, Integer.parseInt(fld_night_count.getText()),Integer.parseInt(fld_adult_count.getText()),Integer.parseInt(fld_child_count.getText()));
                result = true;
            }
            else{
                this.reservationManager.update(selectedResId,res);
                result = true;

            }
            if (result) {
                Helper.displayMessage("done");
                this.dispose();
            } else {
                Helper.displayMessage("error");
            }
        });

    }

}

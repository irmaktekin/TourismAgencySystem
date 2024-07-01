package view;

import business.ReservationManager;
import core.Helper;
import entity.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditReservationView extends Layout{
    private JTextField fld_cust_name;
    private JTextField fld_mobile;
    private JTextField fld_mail;
    private JPanel container;
    private JTextField fld_res_start_date;
    private JTextField fld_child_count;
    private JTextField fld_adult_count;
    private JButton btn_createres;
    private JTextField fld_res_end;
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
        this.initalizeGui(600,300);
        reservationManager = new ReservationManager();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        //set the field values for update
        if(selectedResId!=0){
            this.fld_cust_name.setText(res.getCustomer_name());
            this.fld_child_count.setText(res.getChild_count().toString());
            this.fld_adult_count.setText(res.getAdult_count().toString());
            this.fld_mobile.setText(res.getMobile_phone());
            this.fld_mail.setText(res.getEmail());

            this.fld_res_start_date.setText(res.getStart_date());
            this.fld_res_end.setText(res.getEnd_date());


            LocalDate parsedDateStart = LocalDate.parse(fld_res_start_date.getText(),inputFormatter);
            LocalDate parsedDateEnd = LocalDate.parse(fld_res_end.getText(),inputFormatter);

            String formattedDateStart = parsedDateStart.format(outputFormatter);
            String formattedDateEnd= parsedDateEnd.format(outputFormatter);
            this.fld_res_start_date.setText(formattedDateStart);
            this.fld_res_end.setText(formattedDateEnd);



        }

        //When create button click, reservation is created.
        btn_createres.addActionListener(e-> {
            boolean result = false;
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_adult_count,this.fld_child_count,this.fld_mail,this.fld_mobile,this.fld_res_start_date,this.fld_cust_name})){
                Helper.displayMessage("fill");
            }
            else{

                this.res.setCustomer_name(fld_cust_name.getText());
                this.res.setMobile_phone(fld_mobile.getText());
                this.res.setAdult_count(Integer.parseInt(fld_adult_count.getText()));
                this.res.setChild_count(Integer.parseInt(fld_child_count.getText()));
                this.res.setEmail(fld_mail.getText());
                this.res.setRoomId(roomId);
                this.res.setHotel_id(hotelId);
                //Format date
                LocalDate strDate = LocalDate.parse(fld_res_start_date.getText(),outputFormatter);
                LocalDate endDate = LocalDate.parse(fld_res_end.getText(),outputFormatter);

                if(selectedResId == 0){
                    this.reservationManager.create(res,hotelId,roomId, fld_cust_name.getText(),Integer.parseInt(fld_adult_count.getText()),Integer.parseInt(fld_child_count.getText()),fld_mail.getText(),strDate,endDate);
                    result = true;
                }
                else{
                    this.reservationManager.update(selectedResId,res,strDate,endDate);
                    result = true;

                }
                if (result) {
                    Helper.displayMessage("done");
                    this.dispose();
                } else {
                    Helper.displayMessage("error");
                }
            }

        });

    }

}
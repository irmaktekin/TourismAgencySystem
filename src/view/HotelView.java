package view;

import core.ComboItem;
import core.Helper;
import entity.HostelType;
import entity.HotelStar;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelView extends Layout {
    private JPanel container;
    private JLabel lbl_title_hotel;
    private JLabel lbl_hotel_name;
    private JTextField fld_hotel_name;
    private JLabel lbl_address_hotel;
    private JTextField textField1;
    private JTextField fld_hotel_mail;
    private JLabel lbl_hotel_phone;
    private JTextField fld_hotel_phone;
    private JLabel lbl_star;
    private JComboBox cmb_star;
    private JButton btn_save_hotel;
    private JLabel lbl_hostel_type;
    private JComboBox cmb_hostel_type;
    private User user;

    public HotelView(User user){
        this.user = user;
        this.add(container);
        initalizeGui(400,400);
        setHostelType();
        setHotelStar();

        btn_save_hotel.addActionListener(e->{
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_hotel_mail,this.fld_hotel_name,this.fld_hotel_phone})){
                Helper.displayMessage("fill");
            }
            else{
                System.out.println("Save hotel");
            }
        });
    }
    //Gets the hosteltype names into combo box.
    private void setHostelType(){
        String[] hostelTypeNames = new String[HostelType.values().length];
        for (int i = 0; i < HostelType.values().length; i++) {
            hostelTypeNames[i] = HostelType.values()[i].getHostelType();
        }
        this.cmb_hostel_type.setModel(new DefaultComboBoxModel<>(hostelTypeNames));
    }
    private void setHotelStar(){
        //Gets the stars into combo box.
        Integer [] hotelStars = new Integer[HotelStar.values().length];
        for (int i = 0; i < HotelStar.values().length; i++) {
            hotelStars[i] = HotelStar.values()[i].getHotelRate();

        }
        this.cmb_star.setModel(new DefaultComboBoxModel<>(hotelStars));
    }


}

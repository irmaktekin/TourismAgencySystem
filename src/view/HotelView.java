package view;

import business.HotelManager;
import core.ComboItem;
import core.Helper;
import entity.HostelType;
import entity.Hotel;
import entity.HotelStar;
import entity.User;

import javax.swing.*;

public class HotelView extends Layout {
    private JPanel container;
    private JLabel lbl_title_hotel;
    private JLabel lbl_hotel_name;
    private JTextField fld_hotel_name;
    private JLabel lbl_address_hotel;
    private JTextField fld_hotel_address;
    private JTextField fld_hotel_mail;
    private JLabel lbl_hotel_phone;
    private JTextField fld_hotel_phone;
    private JLabel lbl_star;
    private JComboBox cmb_star;
    private JButton btn_save_hotel;
    private JLabel lbl_hostel_type;
    private JComboBox cmb_hostel_type;
    private JLabel lbl_facilities;
    private JCheckBox freeParkingCheckBox;
    private JCheckBox fitnessCheckBox;
    private JCheckBox hotelConciergeCheckBox;
    private JCheckBox swimmingPoolCheckBox;
    private JCheckBox SPACheckBox;
    private JCheckBox freeWIFICheckBox;
    private JCheckBox a724RoomServiceCheckBox;
    private User user;
    private Hotel hotel;
    private HotelManager hotelManager;
    public HotelView(User user,Hotel hotel){
        this.user = user;
        this.hotel = hotel;
        hotelManager = new HotelManager();
        this.add(container);
        initalizeGui(400,400);
        setHostelType();
        setHotelStar();

        //Set fields
        if(this.hotel.getHotel_id() !=0){
            this.lbl_title_hotel.setText("Edit Hotel");
            this.fld_hotel_name.setText(hotel.getHotel_name());
            this.fld_hotel_address.setText(hotel.getAddress());
            this.fld_hotel_phone.setText(hotel.getPhone_number().toString());
            this.fld_hotel_mail.setText(hotel.getMail());
            ComboItem selectedItem = hotel.getComboItem();
            this.cmb_star.getModel().setSelectedItem(selectedItem);
            /*ComboItem selectedHostel = hotel.getHostelType();
            this.cmb_hostel_type.getModel().setSelectedItem(selectedHostel);*/
        }

        btn_save_hotel.addActionListener(e->{
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_hotel_mail,this.fld_hotel_name,this.fld_hotel_phone})){
                Helper.displayMessage("fill");
            }
            else{
                boolean result = false;
                this.hotel.setHotel_name(fld_hotel_name.getText());
                this.hotel.setStar(Integer.parseInt(cmb_star.getModel().getSelectedItem().toString()));
                this.hotel.setPhone_number(Integer.parseInt(fld_hotel_phone.getText()));
                this.hotel.setAddress(fld_hotel_address.getText());
                this.hotel.setMail(fld_hotel_mail.getText());
                this.hotel.setHostel_type(HostelType.valueOf(cmb_hostel_type.getModel().getSelectedItem().toString()));
                if(this.hotel.getHotel_id() !=0){
                    result = this.hotelManager.update(this.hotel);
                }
                else{
                    result = this.hotelManager.create(this.hotel);
                }
                //If updated/created successfully
                if(result){
                    Helper.displayMessage("done");
                    dispose();
                }
                else{
                    Helper.displayMessage("error");
                }
            }
        });
    }
    //Gets the hosteltype names into combo box.
    private void setHostelType(){
        String[] hostelTypeNames = new String[HostelType.values().length];
        for (int i = 0; i < HostelType.values().length; i++) {
            hostelTypeNames[i] = HostelType.values()[i].getHostelTypeInfo();
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

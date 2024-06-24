package view;

import business.FacilityManager;
import business.HotelManager;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HotelView extends Layout {
    private JPanel container;
    private JButton btn_save_hotel;
    private JLabel lbl_title_hotel;
    private JLabel lbl_facility;
    private JLabel lbl_hotel_name;
    private JTextField fld_hotel_name;
    private JLabel lbl_address_hotel;
    private JTextField fld_hotel_address;
    private JTextField fld_hotel_mail;
    private JLabel lbl_hotel_phone;
    private JTextField fld_hotel_phone;
    private JLabel lbl_star;
    private JComboBox cmb_star;
    private JComboBox cmb_hostel_type;
    private JFormattedTextField fld_strdate1;
    private JFormattedTextField fld_fnshdate1;
    private JFormattedTextField fld_strtdate2;
    private JFormattedTextField fld_fnshdate2;
    private JCheckBox chck_park;
    private JCheckBox chck_2;
    private JCheckBox chck_spa;
    private JCheckBox chck_roomserv;
    private JCheckBox chck_pool;
    private JCheckBox chck_concierge;
    private JCheckBox chck_fitness;
    private JCheckBox ultraAllInclusiveCheckBox;
    private JCheckBox chck_onlybed;
    private JCheckBox halfBoardCheckBox;
    private JCheckBox fullHostelCheckBox;
    private JCheckBox roomBreakfastCheckBox;
    private JCheckBox allInclusiveCheckBox;
    private JCheckBox fullCreditAlcoholExclusiveCheckBox;
    private JPanel cnt_hosteltypes;
    private JPanel cnt_facility;
    private User user;
    private Hotel hotel;
    private HotelManager hotelManager;
    private FacilityManager facilityManager;
    public HotelView(User user,Hotel hotel){
        this.user = user;
        this.hotel = hotel;
        hotelManager = new HotelManager();
        facilityManager = new FacilityManager();
        //btn_save_hotel = new JButton("Save");
        lbl_facility = new JLabel("Select Facility");
       // container.setLayout(new GridLayout(20,4));
        this.add(container);
        initalizeGui(600,600);
        setHostelType();
        setHotelStar();
        //oadCheckBoxComponent();

        //Set fields
        if(this.hotel.getHotel_id() !=0){
            this.lbl_title_hotel.setText("Edit Hotel");
            this.fld_hotel_name.setText(hotel.getHotel_name());
            this.fld_hotel_address.setText(hotel.getAddress());
            this.fld_hotel_phone.setText(hotel.getPhone_number().toString());
            this.fld_hotel_mail.setText(hotel.getMail());
            ComboItem selectedItem = hotel.getComboItem();
            this.cmb_star.getModel().setSelectedItem(selectedItem);
            this.fld_strdate1.setText(hotel.getStrt_date1().toString());
            this.fld_strtdate2.setText(hotel.getStrt_date2().toString());
            this.fld_fnshdate1.setText(hotel.getFnsh_date1().toString());
            this.fld_fnshdate2.setText(hotel.getFnsh_date2().toString());
        }

        btn_save_hotel.addActionListener(e->{
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_hotel_mail,this.fld_hotel_name,this.fld_hotel_phone,this.fld_strdate1,fld_strtdate2,fld_fnshdate1,fld_fnshdate2})){
                Helper.displayMessage("fill");
            }
            else{
                boolean result = false;
                this.hotel.setHotel_name(fld_hotel_name.getText());
                this.hotel.setStar(Integer.parseInt(cmb_star.getModel().getSelectedItem().toString()));
                this.hotel.setPhone_number(Integer.parseInt(fld_hotel_phone.getText()));
                this.hotel.setAddress(fld_hotel_address.getText());
                this.hotel.setMail(fld_hotel_mail.getText());

                this.hotel.setStrt_date1(java.sql.Date.valueOf(fld_strdate1.getText()));
                this.hotel.setStrt_date2(java.sql.Date.valueOf(fld_strtdate2.getText()));
                this.hotel.setFnsh_date1(java.sql.Date.valueOf(fld_fnshdate1.getText()));
                this.hotel.setFnsh_date2(java.sql.Date.valueOf(fld_fnshdate2.getText()));


                List<String> selectedHostelTypes = new ArrayList<>();
                List<JCheckBox> checkBoxList = new ArrayList<>();
                Component[] components = cnt_hosteltypes.getComponents();
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        checkBoxList.add((JCheckBox) component);
                    }
                }

                for (JCheckBox checkBox : checkBoxList) {
                    if (checkBox.isSelected()) {
                        System.out.println(checkBox.getText());
                        selectedHostelTypes.add(checkBox.getText()); // Assuming checkbox text is hostel type name
                    }
                }

                List<String> selectedFacilities = new ArrayList<>();
                List<JCheckBox> facilityList = new ArrayList<>();
                Component[] facilities = cnt_facility.getComponents();
                for (Component component : facilities) {
                    if (component instanceof JCheckBox) {
                        facilityList.add((JCheckBox) component);
                    }
                }

                for (JCheckBox checkBox : facilityList) {
                    if (checkBox.isSelected()) {
                        System.out.println(checkBox.getText());
                        selectedFacilities.add(checkBox.getText()); // Assuming checkbox text is hostel type name
                    }
                }
                if(this.hotel.getHotel_id() !=0){
                    result = this.hotelManager.update(this.hotel);
                }
                else{
                    result = this.hotelManager.create(this.hotel,selectedHostelTypes,selectedFacilities);
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

    private void getHostelTypes() {
    }

    //Gets the hosteltype names into combo box.
    private void setHostelType(){
        String[] hostelTypeNames = new String[HostelType.values().length];
        for (int i = 0; i < HostelType.values().length; i++) {
            hostelTypeNames[i] = HostelType.values()[i].getHostelTypeInfo();
        }
    }
    private void setHotelStar(){
        //Gets the stars into combo box.
        Integer [] hotelStars = new Integer[HotelStar.values().length];
        for (int i = 0; i < HotelStar.values().length; i++) {
            hotelStars[i] = HotelStar.values()[i].getHotelRate();

        }
        this.cmb_star.setModel(new DefaultComboBoxModel<>(hotelStars));
    }
    /*private void loadCheckBoxComponent(){
        ;
        container.add(lbl_facility);
        ArrayList<String> facilityNames = facilityManager.getNamesFromDatabase();
        for(String name : facilityNames){
            JCheckBox checkBox = new JCheckBox(name);
            checkBox.setVisible(true);
            container.add(checkBox);
            container.add(btn_save_hotel,CENTER_ALIGNMENT);

        }
    }
    private static List<JCheckBox> retrieveCheckboxes(Container container) {
        List<JCheckBox> checkBoxList = new ArrayList<>();
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                checkBoxList.add((JCheckBox) component);
            }
        }

        return checkBoxList;
    }
*/



}

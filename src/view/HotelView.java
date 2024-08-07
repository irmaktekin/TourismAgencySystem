package view;

import business.FacilityManager;
import business.HotelManager;
import business.TimePeriodManager;
import core.ComboItem;
import core.Helper;
import entity.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
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
    private TimePeriod timePeriod;
    private HotelManager hotelManager;
    private FacilityManager facilityManager;
    private TimePeriodManager timePeriodManager;

    public HotelView(User user,Hotel hotel,TimePeriod timePeriod) throws SQLException {
        this.user = user;
        this.hotel = hotel;
        this.timePeriod =timePeriod;
        hotelManager = new HotelManager();
        facilityManager = new FacilityManager();
        timePeriodManager = new TimePeriodManager();
        lbl_facility = new JLabel("Select Facility");
        this.add(container);
        initalizeGui(600,600);
        setHostelType();
        setHotelStar();
        loadFacilities();

        //Set fields if it is an update operation
        if(this.hotel.getHotel_id() !=0){
            this.lbl_title_hotel.setText("Edit Hotel");
            this.fld_hotel_name.setText(hotel.getHotel_name());
            this.fld_hotel_address.setText(hotel.getAddress());
            this.fld_hotel_phone.setText(hotel.getPhone_number());
            this.fld_hotel_mail.setText(hotel.getMail());
            ComboItem selectedItem = hotel.getComboItem();
            this.cmb_star.getModel().setSelectedItem(selectedItem);
            this.fld_strdate1.setText(timePeriod.getStart_date1().format( DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            this.fld_fnshdate1.setText(timePeriod.getEnd_date1().format( DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            this.fld_strtdate2.setText(timePeriod.getStart_date2().format( DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            this.fld_fnshdate2.setText(timePeriod.getEnd_date2().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            setCheckBoxesForHotel(hotel.getHotel_id());
        }

        //create operation
        btn_save_hotel.addActionListener(e->{
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_hotel_mail,this.fld_hotel_name,this.fld_hotel_phone,this.fld_strdate1,fld_strtdate2,fld_fnshdate1,fld_fnshdate2})){
                Helper.displayMessage("fill");
            }
            else{
                boolean result = false;
                try{
                    //Set the fields for creation of hotel
                    this.hotel.setHotel_name(fld_hotel_name.getText());
                    this.hotel.setStar(Integer.parseInt(cmb_star.getModel().getSelectedItem().toString()));
                    this.hotel.setPhone_number(fld_hotel_phone.getText());
                    this.hotel.setAddress(fld_hotel_address.getText());
                    this.hotel.setMail(fld_hotel_mail.getText());
                    this.timePeriod.setHotel_id(hotel.getHotel_id());
                    this.timePeriod.setStart_date1(LocalDate.parse(fld_strdate1.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    this.timePeriod.setEnd_date1(LocalDate.parse(fld_fnshdate1.getText(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    this.timePeriod.setStart_date2(LocalDate.parse(fld_strtdate2.getText(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    this.timePeriod.setEnd_date2(LocalDate.parse(fld_fnshdate2.getText(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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
                            selectedFacilities.add(checkBox.getText()); // Assuming checkbox text is hostel type name
                        }
                    }
                    if(this.hotel.getHotel_id() !=0){
                        this.timePeriod = this.hotelManager.update(this.hotel,this.timePeriod);
                        result =true;
                    }
                    else{
                        this.timePeriod = this.hotelManager.create(this.hotel,selectedHostelTypes,selectedFacilities,timePeriod);
                        result=true;
                    }

                    //If updated/created successfully
                    if( result){
                        Helper.displayMessage("done");
                        dispose();
                    }
                    else{
                        Helper.displayMessage("error");
                    }
                }
                catch (DateTimeParseException exception){
                    Helper.displayMessage("formatmismatchdate");

                }
                catch (InputMismatchException exception){
                    Helper.displayMessage("inputmismatch");
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
    }
    private void setHotelStar(){
        //Gets the stars into combo box.
        Integer [] hotelStars = new Integer[HotelStar.values().length];
        for (int i = 0; i < HotelStar.values().length; i++) {
            hotelStars[i] = HotelStar.values()[i].getHotelRate();

        }
        this.cmb_star.setModel(new DefaultComboBoxModel<>(hotelStars));
    }
    //Set the selected facilites for UI
    private void loadFacilities() {
        List<Facility> hotelFacilities = facilityManager.getFacilitiesForHotel(hotel.getHotel_id());
        for (Component component : cnt_facility.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                for (Facility facility : hotelFacilities) {
                    if (checkBox.getText().equals(facility.getFacility_name())) {
                        checkBox.setSelected(true);
                        break;
                    }
                }
            }
        }
    }

    private void setCheckBoxesForHotel(int hotelId) throws SQLException {
        List<Integer> hostelTypeIds = hotelManager.getHostelTypesForHotel(hotelId);

        for (Integer hostelTypeId : hostelTypeIds) {
            switch (hostelTypeId) {
                case 1:
                    ultraAllInclusiveCheckBox.setSelected(true);
                    break;
                case 2:
                    allInclusiveCheckBox.setSelected(true);
                    break;
                case 3:
                    roomBreakfastCheckBox.setSelected(true);
                    break;
                case 4:
                    fullHostelCheckBox.setSelected(true);
                case 5:
                    halfBoardCheckBox.setSelected(true);
                case 6:
                    chck_onlybed.setSelected(true);
                case 7:
                    fullCreditAlcoholExclusiveCheckBox.setSelected(true);
            }
        }
    }
}
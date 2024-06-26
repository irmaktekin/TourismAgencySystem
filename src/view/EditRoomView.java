package view;

import business.RoomManager;
import core.Helper;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditRoomView extends  Layout{
    private JPanel cont_main;
    private JPanel pnl_room_main;
    private JLabel lbl_roomtype;
    private JComboBox cmb_hosteltype;
    private JComboBox cmb_roomtype;
    private JComboBox cmb_term;
    private JPanel pnl_pricing;
    private JTextField chld_price;
    private JTextField adlt_price;
    private JLabel lbl_title;
    private JPanel pnl_features;
    private JTextField fld_bed;
    private JTextField fld_squaremeters;
    private JTextField fld_stock;
    private JCheckBox minibarCheckBox;
    private JCheckBox gamingConsoleCheckBox;
    private JCheckBox hotelSafeCheckBox;
    private JCheckBox projectionCheckBox;
    private JButton btn_save_room;
    private JPanel pnl_details;
    private JCheckBox tvCheckBox;
    private User user;
    private Room room;
    private Hotel hotel;
    private RoomManager roomManager;
    private int hotelId;

    public EditRoomView(User user, Room room, int hotelid){
        this.user=user;
        this.room = room;
        this.hotelId = hotelid;
        roomManager = new RoomManager();
        this.add(cont_main);
        this.initalizeGui(600,700);

        this.cmb_roomtype.setModel(new DefaultComboBoxModel<>(RoomType.values()));
        populateHostelTypes(hotelId);
        //retrive the time periods
        List<TimePeriod> timePeriods = roomManager.getTimePeriodsForHotel(hotelId);
        System.out.println(timePeriods.size());
        for (TimePeriod timePeriod : timePeriods) {
            cmb_term.addItem(timePeriod);
            cmb_term.addItem(timePeriod.toString2());
        }




        btn_save_room.addActionListener(e->{
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_bed,this.fld_squaremeters,this.fld_stock})){
                Helper.displayMessage("fill");
            }
            else {
                boolean result = false;
                try {
                    this.room.setBed_count(Integer.parseInt(fld_bed.getText()));
                    this.room.setSquare_meters(Integer.parseInt(fld_squaremeters.getText()));
                    this.room.setStock_count(Integer.parseInt(fld_stock.getText()));
                    this.room.setAdult_price(Float.parseFloat(adlt_price.getText()));


// Step 1: Retrieve the selected item from JComboBox
                    Object selectedTerm = cmb_term.getSelectedItem();

// Step 2: Check if the selected item is of the expected type
                    if (selectedTerm instanceof TimePeriod) {
                        TimePeriod selectedTimePeriod = (TimePeriod) selectedTerm;

                        // Step 3: Set the time_period_id in your Room object
                        this.room.setTime_period_id(selectedTimePeriod.getTime_period_id());
                    } else {
                        // Handle case where the selected item is not of type TimePeriod (optional)
                        System.out.println("Selected item is not of type TimePeriod");
                    }
                    this.room.setChild_price(Float.parseFloat(chld_price.getText()));
                    RoomType selectedRoomType = (RoomType) this.cmb_roomtype.getSelectedItem();
                    this.room.setRoomType(selectedRoomType.getTypeName());
                    Object selectedItem = this.cmb_hosteltype.getSelectedItem();
                    if (selectedItem instanceof String) {
                        String selectedHostelTypeString = (String) selectedItem;
                        HostelType hostelType = mapStringToHostelType(selectedHostelTypeString);
                        if (hostelType != null) {
                            int hostelTypeId = hostelType.getTypeId();
                            this.room.setHostel_type_id(hostelTypeId);
                        } else {
                            System.out.println("Could not map selected item to HostelType");
                        }
                    } else {
                        System.out.println("Selected item is not a String");
                    }

                    //Insert checkbox data for room attribute
                    List<String> selectedRoomAttributes = new ArrayList<>();
                    List<JCheckBox> checkBoxList = new ArrayList<>();
                    Component[] components = pnl_features.getComponents();
                    System.out.println("Number of components in pnl_features: " + components.length);

                    for (Component component : components) {
                        if (component instanceof JCheckBox) {
                            checkBoxList.add((JCheckBox) component);
                        }
                    }
                    for (JCheckBox checkBox : checkBoxList) {
                        if (checkBox.isSelected()) {
                            String featureName = checkBox.getText();
                            selectedRoomAttributes.add(featureName);
                            System.out.println(featureName);
                        }
                    }

                    String tvAvailable = selectedRoomAttributes.contains("Tv") ? "y" : "n";
                    String minibarAvailable = selectedRoomAttributes.contains("Minibar") ? "y" : "n";
                    String consoleAvailable = selectedRoomAttributes.contains("Gaming Console") ? "y" : "n";
                    String safeAvailable = selectedRoomAttributes.contains("Safe") ? "y" : "n";
                    String projectorAvailable = selectedRoomAttributes.contains("Projection") ? "y" : "n";



                    if (this.room.getRoom_id() != 0) {
                        //result = this.roomManager.update(this.hotel);
                        System.out.println("update is performed");
                    } else {
                        System.out.println(tvAvailable);
                        result = this.roomManager.create(room, hotelId, room.getTime_period_id(),tvAvailable, minibarAvailable, consoleAvailable, safeAvailable, projectorAvailable);
                    }
                    //If updated/created successfully
                    if (result) {
                        Helper.displayMessage("done");
                        dispose();
                    } else {
                        Helper.displayMessage("error");
                    }


                } catch (NumberFormatException ex) {
                    Helper.displayMessage("inputmismatch");
                }
            }




    });



}
    private void populateHostelTypes(int hotelId) {
        // Get hostel types for the specific hotel from DAO
        List<HostelType> hostelTypes = roomManager.getHostelTypesForHotel(hotelId);

        // Create a string array to hold type names for the JComboBox
        String[] hostelTypeNames = hostelTypes.stream().map(HostelType::getHostelTypeInfo).toArray(String[]::new);

        // Set the model of JComboBox with hostel type names
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(hostelTypeNames);
        cmb_hosteltype.setModel(model);
    }
    private HostelType mapStringToHostelType(String selectedHostelTypeString) {
        switch (selectedHostelTypeString) {
            case "Ultra All Inclusive":
                return HostelType.ULTRA;
            case "All Inclusive":
                return HostelType.INCLUSIVE;
            case "Room Breakfast":
                return HostelType.BREAKFAST;
            case "Full Hostel":
                return HostelType.HOSTEL;
            case "Half Board":
                return HostelType.BOARD;
            case "Only Bed":
                return HostelType.BED;
            case "Full Credit Alcohol Exclusive":
                return HostelType.CREDIT;
            default:
                return null; // Handle if string does not match any enum value
        }
    }
}
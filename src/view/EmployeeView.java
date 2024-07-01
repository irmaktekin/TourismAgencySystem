package view;

import business.HotelManager;
import business.ReservationManager;
import business.RoomManager;
import business.TimePeriodManager;
import core.Helper;
import entity.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class EmployeeView extends Layout{
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome_emp;
    private JTabbedPane tab_menu;
    private JPanel pnl_hotels;
    private JScrollPane scrl_hotels;
    private JTable table_hotels;
    private JPanel pnl_rooms;
    private JScrollPane scrl_rooms;
    private JTable table_rooms;
    private JTextField fld_city;
    private JFormattedTextField fld_strt_room;
    private JFormattedTextField fld_end_room;
    private JTextField fld_cust_count;
    private JButton searchButton;
    private JPanel pnl_reservations;
    private JScrollPane scrl_res;
    private JTable table_res;
    private JTextField fld_hotelname;
    private Object[] col_hotel;
    private Object[] col_room;
    private Object[] col_res;
    private HotelManager hotelManager;
    private ReservationManager reservationManager;
    private RoomManager roomManager;
    private TimePeriodManager timePeriodManager;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_room = new DefaultTableModel();
    private DefaultTableModel tmdl_res = new DefaultTableModel();
    private User user;
    private TimePeriod timePeriod;
    private JPopupMenu hotelMenu;
    private JPopupMenu roomMenu;
    private JPopupMenu resMenu;

    public EmployeeView(User user){
        this.user = user;
        this.add(container);
        initalizeGui(600,500);
        this.lbl_welcome_emp.setText("Welcome " + this.user.getUser_name() +"." );
        timePeriod = new TimePeriod();
        this.hotelManager = new HotelManager();
        this.timePeriodManager = new TimePeriodManager();
        this.reservationManager = new ReservationManager();
        this.roomManager = new RoomManager();

        //Creating table components and loading the data.
        loadHotelTable(null);
        loadRoomTable(null);
        loadReservationTable(null);
        loadHotelComponent();
        loadRoomComponent();
        loadReservationComponent();

        this.table_hotels.setComponentPopupMenu(hotelMenu);
        this.table_rooms.setComponentPopupMenu(roomMenu);
        this.table_res.setComponentPopupMenu(resMenu);

        //When the search button is clicked, get the field values from UI.
        searchButton.addActionListener(e->{
            try{
                //Get the fields for search rooms
                String hotelName = fld_hotelname.getText().trim();
                String hotelLocation = fld_city.getText().trim();
                String startDateText = fld_strt_room.getText();
                DateTimeFormatter inputFormatter  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate startDate = startDateText.isEmpty() ? null : LocalDate.parse(startDateText,inputFormatter);
                String endDateText = fld_end_room.getText();
                LocalDate endDate = endDateText.isEmpty() ? null : LocalDate.parse(endDateText,inputFormatter);
                //if the field is not null, then parse it to integer.
                Integer customerCount = 0;
                if (!fld_cust_count.getText().isEmpty()) {
                    try {
                        customerCount = Integer.valueOf(fld_cust_count.getText());
                    } catch (NumberFormatException exception) {
                        exception.printStackTrace(); // Or log the error
                    }
                }
                ArrayList<Room> roomList = this.roomManager.searchForTable(hotelLocation, startDate, endDate,customerCount,hotelName);
                ArrayList<Object[]> roomRowList = this.roomManager.getForTable(this.col_room.length,roomList);
                loadRoomTable(roomRowList);
            }
            catch (DateTimeParseException exception){
                Helper.displayMessage("formatmismatchdate");
            }
        });
    }

    private void loadReservationComponent() {
        this.resMenu = new JPopupMenu();
        tableRowSelect(table_res,resMenu);
        this.resMenu.add("Delete Reservation").addActionListener(e->{
            if(Helper.confirm("Do you want to delete this record?")){
                int selectedResId = this.getTableSelectedRow(table_res,0);
                int selectedRoomId = this.getTableSelectedRow(table_res,2);
                System.out.println(selectedRoomId);

                if(this.reservationManager.deleteById(selectedResId,selectedRoomId)){
                    System.out.println("resid"+selectedResId);
                    Helper.displayMessage("done");
                    loadReservationTable(null);
                    loadRoomTable(null);
                }
                else{
                    Helper.displayMessage("error");
                }
            }
        });
        this.resMenu.add("Update Reservation").addActionListener(e->{
            int selectedResId = this.getTableSelectedRow(table_res,0);
            int selectedHotelId = this.getTableSelectedRow(table_res,1);

            EditReservationView resView = null;
            System.out.println(selectedResId);
            resView = new EditReservationView(new User(),timePeriod,new Room(),reservationManager.getById(selectedResId),0,0,selectedResId);

            resView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //Reload the user table after dispose of the edit view
                    loadReservationTable(null);
                }
            });
        });
    }

    private void loadRoomTable(ArrayList<Object[]> roomList){
        this.col_room = new Object[]{"ID","HotelID","Stock","Bed","Squaremeter","TV","Minibar","Console","Safe","Projector","Hotel Name"};
        if(roomList==null){
            roomList = this.roomManager.getForTable(col_room.length, this.roomManager.getAllHotels());
        }
        this.createTable(this.tmdl_room,this.table_rooms,col_room,roomList);
    }
    private void loadHotelTable(ArrayList<Object[]> hotel_List) {
        this.col_hotel = new Object[]{"ID","Name","Address","Mail","Phone","Star"};
        if(hotel_List==null){
            hotel_List = this.hotelManager.getForTable(col_hotel.length, this.hotelManager.getAllHotels());
        }
        this.createTable(this.tmdl_hotel,this.table_hotels,col_hotel,hotel_List);
    }

    private void loadRoomComponent(){
        this.roomMenu = new JPopupMenu();
        tableRowSelect(table_rooms,roomMenu);

        this.roomMenu.add("Create Reservation").addActionListener(e->{
            int selectedHotelId = this.getTableSelectedRow(table_rooms,1);
            int selectedRoomId = this.getTableSelectedRow(table_rooms,0);

            EditReservationView editReservationView = new EditReservationView(new User(),timePeriod,new Room(),new Reservation(),selectedHotelId,selectedRoomId,0);
            editReservationView.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    loadReservationTable(null);
                    loadRoomTable(null);
                }
            });
        });
    }
    private void loadReservationTable(ArrayList<Object[]> resList){
        this.col_res = new Object[]{"ID","HotelID","Room ID","Customer Name","Customer Mobile","Child Count","Adult Count","Night Count","Total Price","Email"};
        if(resList==null){
            resList = this.reservationManager.getForTable(col_res.length, this.reservationManager.getAllReservations());
        }
        this.createTable(this.tmdl_res,this.table_res,col_res,resList);
    }

    private void loadHotelComponent(){
        this.hotelMenu = new JPopupMenu();
        tableRowSelect(table_hotels,hotelMenu);
        this.hotelMenu.add("Create").addActionListener(e->{
            HotelView hotelView = null;
            try {
                hotelView = new HotelView(new User(),new Hotel(),timePeriod);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //refresh user table after create operation
                    loadHotelTable(null);
                }
            });

        });
        this.hotelMenu.add("Update").addActionListener(e->{
            int selectedHotelId = this.getTableSelectedRow(table_hotels,0);
            HotelView hotelView = null;
            try {
                hotelView = new HotelView(user,this.hotelManager.getById(selectedHotelId),this.timePeriodManager.getById(selectedHotelId));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //Reload the user table after dispose of the edit view
                    loadHotelTable(null);
                }
            });
        });
        this.hotelMenu.add("Delete").addActionListener(e->{
            if(Helper.confirm("Do you want to delete this record?")){
                int selectedHotelId = this.getTableSelectedRow(table_hotels,0);
                if(this.hotelManager.deleteById(selectedHotelId)){
                    Helper.displayMessage("done");
                    loadHotelTable(null);
                }
                else{
                    Helper.displayMessage("error");
                }
            }
        });
        this.hotelMenu.add("Create Room").addActionListener(e->{
            int selectedHotelId = this.getTableSelectedRow(table_hotels,0);

            EditRoomView editRoomView = new EditRoomView(new User(),new Room(),selectedHotelId);
            editRoomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //refresh user table after create operation
                    System.out.println("create room is clicked");
                    loadRoomTable(null);
                }
            });
        });
    }
}

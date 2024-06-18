package view;

import business.HotelManager;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EmployeeView extends Layout{
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome_emp;
    private JTabbedPane tab_menu;
    private JPanel pnl_hotels;
    private JScrollPane scrl_hotels;
    private JTable table_hotels;
    private Object[] col_hotel;
    private HotelManager hotelManager;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private User user;
    private JPopupMenu hotelMenu;


    public EmployeeView(User user){
        this.user = user;
        this.add(container);
        initalizeGui(400,400);
        this.lbl_welcome_emp.setText("Welcome " + this.user.getUser_name() +"." );
        this.hotelManager = new HotelManager();
        loadHotelTable(null);
        loadHotelComponent();
        this.table_hotels.setComponentPopupMenu(hotelMenu);

    }

    private void loadHotelTable(ArrayList<Object[]> hotel_List) {
        this.col_hotel = new Object[]{"ID","Name","Address","Mail","Phone","Star","Hostel Type"};
        if(hotel_List==null){
            hotel_List = this.hotelManager.getForTable(col_hotel.length, this.hotelManager.getAllHotels());
        }
        this.createTable(this.tmdl_hotel,this.table_hotels,col_hotel,hotel_List);

    }
    private void loadHotelComponent(){
        this.hotelMenu = new JPopupMenu();
        tableRowSelect(table_hotels,hotelMenu);
        this.hotelMenu.add("Create").addActionListener(e->{
            HotelView hotelView = new HotelView(new User());
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //refresh user table after create operation
                    loadHotelTable(null);
                }
            });


        });
    }

}

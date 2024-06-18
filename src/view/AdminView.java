package view;

import business.UserManager;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Objects;

public class AdminView extends Layout{
    private JPanel container;
    private JLabel lbl_welcomeadmin;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JPanel pnl_users;
    private JScrollPane scrl_users;
    private JTable table_users;
    private JLabel lbl_userrole;
    private JComboBox cmb_role;
    private User user;
    private Object[] col_user;
    private UserManager userManager;
    private DefaultTableModel tmdl_user = new DefaultTableModel();
    private JPopupMenu userMenu;

    public AdminView(User user){
        this.userManager = new UserManager();
        this.add(container);
        this.initalizeGui(400,400);
        this.user = user;
        if(this.user == null){
            dispose();
        }
        this.lbl_welcomeadmin.setText("Welcome" + this.user.getUser_name() );

        loadUserTable(null);
        loadUserComponent();
        this.table_users.setComponentPopupMenu(userMenu);


    }

    private void loadUserTable(ArrayList<Object[]> userList) {
        System.out.println("Load user table");
        this.col_user = new Object[]{"ID","Username","Mail","Password","Role"};
        if(userList==null){
            System.out.println("Null");
            userList = this.userManager.getForTable(col_user.length, this.userManager.getAllUsers());
        }
        this.createTable(this.tmdl_user,this.table_users,col_user,userList);

    }

    private void loadUserComponent() {
        tableRowSelect(table_users,userMenu);
        this.userMenu = new JPopupMenu();
        this.userMenu.add("Create").addActionListener(e->{
            System.out.println("Clicked create");
        });
        this.userMenu.add("Update").addActionListener(e->{
            System.out.println("Update is clicked");
        });
        this.userMenu.add("Delete").addActionListener(e->{
            System.out.println("Delete is clicked");
        });


    }


}

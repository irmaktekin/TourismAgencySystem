package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
    private JButton btn_search;
    private JButton btn_clear;
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
        loadUserFilter();
        this.table_users.setComponentPopupMenu(userMenu);


    }
    private void loadUserFilter(){
        this.cmb_role.setModel(new DefaultComboBoxModel<>(User.Role.values()));
    }

    private void loadUserTable(ArrayList<Object[]> userList) {
        this.col_user = new Object[]{"ID","Username","Mail","Password","Role"};
        if(userList==null){
            userList = this.userManager.getForTable(col_user.length, this.userManager.getAllUsers());
        }
        this.createTable(this.tmdl_user,this.table_users,col_user,userList);

    }

    private void loadUserComponent() {
        tableRowSelect(table_users,userMenu);
        this.userMenu = new JPopupMenu();
        this.userMenu.add("Create").addActionListener(e->{
            UserEditView userEditView = new UserEditView(new User());
            userEditView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //refresh user table after create operation
                    loadUserTable(null);
                }
            });


        });
        this.userMenu.add("Update").addActionListener(e->{
            int selectedUserId = this.getTableSelectedRow(table_users,0);
            UserEditView userEditView = new UserEditView(this.userManager.getById(selectedUserId));
            userEditView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //Reload the user table after dispose of the edit view
                    loadUserTable(null);
                }
            });
        });
        this.userMenu.add("Delete").addActionListener(e->{
            if(Helper.confirm("Do you want to delete this record?")){
                int selectedUserId = this.getTableSelectedRow(table_users,0);
                if(this.userManager.deleteById(selectedUserId)){
                    Helper.displayMessage("done");
                    loadUserTable(null);
                }
                else{
                    Helper.displayMessage("error");
                }
            }
        });
        //If search button is clicked for the role
        btn_search.addActionListener(e-> {
            ArrayList<User> userList = this.userManager.searchUser((User.Role) this.cmb_role.getSelectedItem());
            ArrayList<Object[]> userRow = this.userManager.getForTable(this.col_user.length,userList);
            loadUserTable(userRow);
        });
    }


}

package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class LoginView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JLabel lbl_login;
    private JPanel p_header;
    private JPanel p_body;
    private JLabel lbl_username;
    private JTextField fld_username;
    private JLabel lbl_password;
    private JTextField fld_password;
    private JButton loginButton;
    private UserManager userManager;

    public LoginView(){
        this.userManager = new UserManager();
        this.add(container);
        this.initalizeGui(400,400);
        loginButton.addActionListener(e-> {
           JTextField [] fieldList = {this.fld_username,this.fld_password};
           if(Helper.isFieldListBlank(fieldList)){
               Helper.displayMessage("fill");
           }
           else{
               User user = this.userManager.login(this.fld_username.getText(),this.fld_password.getText());
               if(user == null){
                   Helper.displayMessage("notfound");
               }
               else if(user.getRole().equals("EMPLOYEE")){
                   EmployeeView employeeView = new EmployeeView(user);
               }
               else{
                   dispose();
                   AdminView adminView = new AdminView(user);
               }
           }

        });
    }


}

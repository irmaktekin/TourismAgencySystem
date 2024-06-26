package view;

import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.User;
import javax.swing.*;


public class UserEditView  extends Layout{
    private UserManager userManager;
    private JPanel container;
    private JLabel lbl_edituser;
    private JLabel lbl_username;
    private JTextField fld_username;
    private JLabel lbl_email;
    private JTextField fld_email;
    private JLabel lbl_password;
    private JPasswordField fld_password;
    private JComboBox cmb_role;
    private JLabel lbl_role;
    private JButton btn_save_user;
    private User user;
    public UserEditView(User user){
        this.user = user;
        userManager = new UserManager();
        this.add(container);
        this.initalizeGui(400,400);

        this.cmb_role.setModel(new DefaultComboBoxModel<>(User.Role.values()));
        //If id is not null, it is an update operation
        //Set fields
        if(this.user.getUser_id() !=0){
            this.lbl_edituser.setText("Edit User");
            ComboItem selectedItem = user.getComboItem();
            this.cmb_role.getModel().setSelectedItem(selectedItem);
            this.fld_username.setText(user.getUser_name());
            this.fld_email.setText(user.getEmail());
            this.fld_password.setText(user.getPassword());
        }
        //If the user clicks save button
        this.btn_save_user.addActionListener(e->{
            if(Helper.isFieldListBlank(new JTextField[]{this.fld_username,this.fld_password,this.fld_email})){
                Helper.displayMessage("fill");
            }
            else{
                //Get the element's values into the user object.
                boolean result = false;
                this.user.setUser_name(fld_username.getText());
                this.user.setEmail(fld_email.getText());
                this.user.setPassword(fld_password.getText());
                this.user.setRole(cmb_role.getModel().getSelectedItem().toString());
                if(this.user.getUser_id() !=0){
                    result = this.userManager.update(this.user);
                }
                else{
                    result = this.userManager.create(this.user);
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

}

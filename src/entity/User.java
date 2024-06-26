package entity;

import core.ComboItem;

public class User {
    private int user_id;
    private String user_name;
    private String email;
    private String password;
    private String role;

    public enum Role {
        ADMIN,
        EMPLOYEE
    }

    public User() {
    }

    public User(int user_id, String role, String password, String email, String user_name) {
        this.user_id = user_id;
        this.role = role;
        this.password = password;
        this.email = email;
        this.user_name = user_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
    public ComboItem getComboItem(){
        return new ComboItem(this.getUser_id(),this.getRole());
    }
}

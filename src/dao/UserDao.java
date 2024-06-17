package dao;

import core.DbConnector;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection con;
    public UserDao(){
        this.con=DbConnector.getInstance();
    }
    //List all users
    public ArrayList <User> findAll(){
        ArrayList<User> users = new ArrayList<User>();
        String query = "Select * From public.user_";
        try{
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                users.add(this.mapResultSetToUser(rs));
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    public User mapResultSetToUser(ResultSet rs){
        User user = new User();
        try{
            user.setUser_id(rs.getInt("user_id"));
            user.setUser_name(rs.getString("user_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("user_password"));
            user.setRole(rs.getString("role"));
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return user;
    }
}

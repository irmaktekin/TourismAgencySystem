package business;

import dao.UserDao;
import entity.User;

import java.util.ArrayList;

public class UserManager {
    private final UserDao userDao;

    public UserManager(){
        this.userDao = new UserDao();
    }
    public User login(String username, String password){
        return this.userDao.login(username, password);
    }
    public ArrayList<User> getAllUsers(){
        return this.userDao.getAllUsers();
    }
    public User getById(int id){
        return this.userDao.getById(id);
    }
    public ArrayList<Object[]> getForTable(int size,ArrayList<User> users){
        ArrayList<Object[]> userList = new ArrayList<>();
        for(User obj : users){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getUser_id();
            rowObject[i++] = obj.getUser_name();
            rowObject[i++] = obj.getEmail();
            rowObject[i++] = obj.getPassword();
            rowObject[i++] = obj.getRole();

            userList.add(rowObject);

        }
        return userList;
    }
    public boolean deleteById(int id){
        return this.userDao.deleteById(id);
    }
    public boolean update(User user){
        return this.userDao.updateUser(user);
    }
    public boolean create(User user){
        return this.userDao.createUser(user);
    }

    public ArrayList<User> searchUser(User.Role userRole){
        String query = "Select * From public.user_";
        ArrayList<String> whereCondition = new ArrayList<>();

        if(userRole !=null){
            whereCondition.add("user_role = '"+userRole.toString() + "'");
        }
        if(!whereCondition.isEmpty()){
            query += " Where " + String.join(" AND ", whereCondition) ;
        }
        System.out.println(query);

        return this.userDao.selectByQuery(query);
    }
}

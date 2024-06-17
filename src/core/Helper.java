package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    //load component to the center of the screen
    public static int getLocationPoint(String type, Dimension size){
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static void displayMessage(String str) {
        String msg;
        String title = switch (str) {
            case "done" -> {
                msg = "Success";
                yield "Result";
            }
            case "fill" ->{
                msg = "Please fill mandatory fields!";
                yield "Alert!";
            }
            case "notfound" ->{
                msg = "User not found!";
                yield "Login Failed!";
            }
            default -> {
                msg = str;
                yield "Message";
            }
        };
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean isFieldListBlank(JTextField[] fieldList){
        for(JTextField field : fieldList){
            if (field.getText().trim().isEmpty()){
                return true;
            }
        }
        return false;
    }
}

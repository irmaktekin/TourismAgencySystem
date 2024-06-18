import core.DbConnector;
import core.Helper;
import view.LoginView;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Helper.initializeTheme();
        LoginView loginView = new LoginView();
    }
}
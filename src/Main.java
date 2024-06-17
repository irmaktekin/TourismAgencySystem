import core.DbConnector;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection =  DbConnector.getInstance();
    }
}
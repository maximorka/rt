package feature.storage;

import feature.prefs.Prefs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class Storage {
    public static Storage getINSTANCE() {
        return INSTANCE;
    }

    public static final Storage INSTANCE = new Storage();
    private Connection connection;


    private Storage(){
        try {
            connection =  DriverManager.getConnection(new Prefs().getString(Prefs.DATABASE_CONNECTION_URL));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
}

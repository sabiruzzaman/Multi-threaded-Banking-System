package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    private static volatile Connection DB_CONNECTION = null;
    private static final Object lock = new Object();

    private ConnectionClass() {}

    public static Connection getInstance() {
        if (DB_CONNECTION == null) {
            synchronized (lock) {
                if (DB_CONNECTION == null) {
                    String url = "jdbc:postgresql://" + DatabaseConfig.host + ":" + DatabaseConfig.port + "/" + DatabaseConfig.databaseName;
                    try {
                        DB_CONNECTION = DriverManager.getConnection(url, DatabaseConfig.user, DatabaseConfig.password);
                        if (DB_CONNECTION != null) {
                            System.out.println("Connection established successfully!");
                        } else {
                            System.out.println("Connection failed!");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return DB_CONNECTION;
    }
}

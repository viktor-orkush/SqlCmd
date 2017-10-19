package model;

import java.sql.SQLException;

public class CreateDB {
    public static boolean isInitialized = false;
    public static DatabaseManager manager;

    public static void runOnceForSettingDB() {
        manager = new JDBCDatabaseManager();
        if (isInitialized) return;
        try {
            manager.connect("postgres", "admin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            manager.deleteDataBase("sqlcmd");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        try {
            manager.createDataBase("sqlcmd");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        try {
            manager.connect("sqlcmd", "admin", "admin");
            manager.createTable();
            manager.disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        isInitialized = true;
    }
}
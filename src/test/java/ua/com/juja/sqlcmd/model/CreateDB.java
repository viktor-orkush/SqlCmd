package ua.com.juja.sqlcmd.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class CreateDB {
    private static MyPropertiesForTest prop = MyPropertiesForTest.instance();
    public static boolean isInitialized = false;
    public static DatabaseManager manager;

    public static void runOnceForSettingDB() {
        manager = new JDBCDatabaseManager();
        if (isInitialized) return;
        try {
            manager.connect(prop.GLOBAL_USER_NAME, prop.GLOBAL_PASSWORD);
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

            ArrayList<String> columnName = new ArrayList<>();
            columnName.add("id");
            columnName.add("name");
            columnName.add("password");

            ArrayList<String> columnType = new ArrayList<>();
            columnType.add("int");
            columnType.add("varchar(50)");
            columnType.add("varchar(50)");


            manager.createTable("users", columnName, columnType);
            manager.disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        isInitialized = true;
    }
}

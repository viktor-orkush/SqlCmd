package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyPropertiesForTest {
    static private MyPropertiesForTest _instance = null;
    public static final String PATH_TO_PROPERTIES = "src/test/java/resources/config.properties";
    public static String GLOBAL_USER_NAME = "";

    public static String GLOBAL_PASSWORD = "";
    public static String DB_NAME = "";
    public static String DB_USER_NAME = "";
    public static String DB_PASSWORD = "";
    public static String TABLE_NAME = "";

    public static String DB_NAME_FOR_TEST = "";
    public static String TABLE_NAME_FOR_TEST = "";

    private MyPropertiesForTest() {
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            Properties prop = new Properties();
            prop.load(fileInputStream);

            GLOBAL_USER_NAME = prop.getProperty("GLOBAL_USER_NAME");
            GLOBAL_PASSWORD = prop.getProperty("GLOBAL_PASSWORD");

            DB_NAME = prop.getProperty("DB_NAME");
            DB_USER_NAME = prop.getProperty("DB_USER_NAME");
            DB_PASSWORD = prop.getProperty("DB_PASSWORD");
            TABLE_NAME = prop.getProperty("TABLE_NAME");

            DB_NAME_FOR_TEST = prop.getProperty("DB_NAME_FOR_TEST");
            TABLE_NAME_FOR_TEST = prop.getProperty("TABLE_NAME_FOR_TEST");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static public MyPropertiesForTest instance(){
        if (_instance == null) {
            _instance = new MyPropertiesForTest();
        }
        return _instance;
    }
}

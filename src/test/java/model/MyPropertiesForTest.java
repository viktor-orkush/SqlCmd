package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyPropertiesForTest {
    public static final String PATH_TO_PROPERTIES = "src/test/java/resources/config.properties";

    public static String GLOBAL_USER_NAME;
    public static String GLOBAL_PASSWORD;

    public static String DB_NAME;
    public static String DB_USER_NAME;
    public static String DB_PASSWORD;

    public MyPropertiesForTest() {
        getProperties();
    }

    public static void getProperties() {
        FileInputStream fileInputStream;
        Properties prop = new Properties();

        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);

            GLOBAL_USER_NAME = prop.getProperty("GLOBAL_USER_NAME");
            GLOBAL_PASSWORD = prop.getProperty("GLOBAL_PASSWORD");

            DB_NAME = prop.getProperty("DB_NAME");
            DB_USER_NAME = prop.getProperty("DB_USER_NAME");
            DB_PASSWORD = prop.getProperty("DB_PASSWORD");
        } catch (IOException e) {
            System.out.println("Файл с настройками " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();
        }
    }
}

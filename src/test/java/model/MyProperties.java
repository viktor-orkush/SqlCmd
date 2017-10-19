package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    public static final String PATH_TO_PROPERTIES = "src/test/java/resources/config.properties";
    static String GLOBAL_USER_NAME;
    static String GLOBAL_PASSWORD;

    static String DB_NAME;
    static String DB_USER_NAME;
    static String DB_PASSWORD;

    public static void getProperties() {
        FileInputStream fileInputStream;
        Properties prop = new Properties();

        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);

            GLOBAL_USER_NAME = prop.getProperty("DB_PASSWORD");
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

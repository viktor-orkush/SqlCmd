package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    public static final String PATH_TO_PROPERTIES = "src/test/java/resources/config.properties";

    public static String URL;


    public MyProperties() {
        getProperties();
    }

    public static void getProperties() {
        FileInputStream fileInputStream;
        Properties prop = new Properties();

        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);

            URL = prop.getProperty("URL");
        } catch (IOException e) {
            System.out.println("Файл с настройками " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();
        }
    }
}

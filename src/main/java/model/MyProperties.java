package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    private static MyProperties _instance;
    private final String PATH_TO_PROPERTIES = "src/test/java/resources/config.properties";

    public static String URL;

    private MyProperties() {
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            Properties prop = new Properties();
            prop.load(fileInputStream);

            URL = prop.getProperty("URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MyProperties instance() {
        if (_instance == null) {
            _instance = new MyProperties();
        }
        return _instance;
    }
}

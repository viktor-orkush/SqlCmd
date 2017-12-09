package ua.com.juja.sqlcmd.service;

import java.sql.SQLException;
import java.util.List;

public interface Service {

    List<String> commandList();

    void connect(String database, String user, String pass) throws SQLException, ClassNotFoundException;
}

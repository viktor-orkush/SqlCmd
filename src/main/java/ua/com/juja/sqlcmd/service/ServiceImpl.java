package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ServiceImpl implements Service {
    private DatabaseManager manager;

    public ServiceImpl() {
        manager = new JDBCDatabaseManager();
    }

    @Override
    public List<String> commandList() {
        return Arrays.asList("help", "menu", "connect");
    }

    @Override
    public void connect(String database, String user, String pass) throws SQLException, ClassNotFoundException {
        manager.connect(database, user, pass);
    }
}

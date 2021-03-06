package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class ListDB implements Command {

    private View view;
    private DatabaseManager manager;

    public ListDB(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return "listDB".equals(command);
    }

    @Override
    public void process(String command) {
        java.util.List<String> dbList = null;
        try {
            dbList = manager.getListDataBase();
            view.write("Список БД", dbList);
        } catch (SQLException e) {
            view.write("Не удалось получить список БД по причине: " + e.getMessage());
        }
    }
}

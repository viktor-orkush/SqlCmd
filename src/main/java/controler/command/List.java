package controler.command;

import model.DatabaseManager;
import view.View;

import java.sql.SQLException;
import java.util.Arrays;

public class List implements Command {

    private View view;
    private DatabaseManager manager;

    public List(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return "list".equals(command);
    }

    @Override
    public void process(String command) {
        java.util.List<String> tableList = null;
        try {
            tableList = manager.getListTables();
            view.write(Arrays.toString(tableList.toArray()));
        } catch (SQLException e) {
            view.write("Не удалось получить список таблиц по причине: " + e.getMessage());
        }
    }
}

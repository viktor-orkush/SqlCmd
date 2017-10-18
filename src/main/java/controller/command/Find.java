package controller.command;

import controller.command.Exeption.IncorrectInputArgumentException;
import model.DataSet;
import model.DatabaseManager;
import view.View;

import java.sql.SQLException;
import java.util.List;

public class Find implements Command {

    private View view;
    DatabaseManager manager;
    private static final String[] EXAMPLE_COMMAND_FIND = new String[]{"find", "tableName"};

    public Find(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException {
        String[] argumentArray = command.split("[|]");

        if (argumentArray.length != EXAMPLE_COMMAND_FIND.length)
            throw new IncorrectInputArgumentException("Введено не верное количество аргументов");

        String tableName = argumentArray[1];
        try {
            printTableHeader(tableName);
            printTableValues(tableName);
        } catch (SQLException e) {
            view.write(String.format("Не удалось получить данные таблицы %s по причине: ", tableName) + e.getMessage());
        }
    }

    private void printTableHeader(String tableName) throws SQLException {
        List<String> tableHeader = null;
        tableHeader = manager.getTableHeader(tableName);
        if (tableHeader.size() == 0) throw new IllegalArgumentException();
        String printValues = "";
        for (String header : tableHeader) {
            printValues += header + "| ";
        }
        view.write("---------------------------");
        view.write(printValues);
        view.write("---------------------------");

    }

    private void printTableValues(String tableName) throws SQLException {
        List<DataSet> tableData = null;
        tableData = manager.getTableData(tableName);
        for (DataSet data : tableData) {
            printRow(data);
        }
    }

    private void printRow(DataSet tableData) {
        Object[] tableValues = tableData.getValues();
        String printValues = "";
        for (int j = 0; j < tableValues.length; j++) {
            printValues += tableValues[j] + "| ";
        }
        view.write(printValues);
    }
}

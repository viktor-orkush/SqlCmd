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
            String[] tableHeader = getStrArrTableHeader(tableName);
            Object[][] tableData = getObjArrTableValues(tableName);
            view.write(tableHeader, tableData);
        } catch (SQLException e) {
            view.write(String.format("Не удалось получить данные таблицы %s по причине: ", tableName) + e.getMessage());
        }
    }

    private String[] getStrArrTableHeader(String tableName) throws SQLException {
        List<String> tableHeader = null;
        tableHeader = manager.getTableHeader(tableName);
        if (tableHeader.size() == 0) throw new IllegalArgumentException();
        String[] headerArr = new String[tableHeader.size()];
        for (int i = 0; i < tableHeader.size(); i++) {
            headerArr[i] = tableHeader.get(i);
        }
        return headerArr;
    }

    private Object[][] getObjArrTableValues(String tableName) throws SQLException {
        List<DataSet> tableData = null;
        tableData = manager.getTableData(tableName);
        if (tableData.size() == 0) return  new Object[0][0];
        int columnLength = tableData.get(0).getNames().length;
        int rowLength = tableData.size();
        Object[][] dataObj = new Object[rowLength][columnLength];
        for (int i = 0; i < tableData.size(); i++) {
            dataObj[i] = tableData.get(i).getValues();
        }
        return (Object[][]) dataObj;
    }
}

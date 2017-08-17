package controler.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

/**
 * Created by Viktor on 15.08.2017.
 */
public class Find implements Command {

    private View view;
    DatabaseManager manager;

    public Find(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] examComFind = {"find", "tableName"};
        String[] arrLine = command.split("[|]");
        if (arrLine.length != examComFind.length)
            throw new IllegalArgumentException("Введте название таблицы правильно");
        String tableName = arrLine[1];

        String[] tableHeader = manager.getTableHeader(tableName);
        printTableHeader(tableHeader);

        DataSet[] tableData = manager.getTableData(tableName);
        printTableValues(tableData);
    }

    private void printTableHeader(String[] tableHeader) {
        String printValues ="";
        for (int j = 0; j < tableHeader.length; j++) {
            printValues += tableHeader[j] + "| ";
        }
        view.write("---------------------------");
        view.write(printValues);
        view.write("---------------------------");
    }

    private void printTableValues(DataSet[] tableData) {
        for (int i = 0; i < tableData.length; i++) {
            printRow(tableData[i]);
        }
    }

    private void printRow(DataSet tableData) {
        Object[] tableValues = tableData.getValues();
        String printValues ="";
        for (int j = 0; j < tableValues.length; j++) {
            printValues += tableValues[j] + "| ";
        }
        view.write(printValues);
    }
}

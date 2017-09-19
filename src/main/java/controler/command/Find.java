package controler.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

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
        String[] argumentArray = command.split("[|]");

        if (argumentArray.length != examComFind.length)
            throw new IllegalArgumentException("Введте название таблицы правильно");

        String tableName = argumentArray[1];
        String[] tableHeader = manager.getTableHeader(tableName);

        try{
            printTableHeader(tableHeader);
        }catch(Exception e){
            //doNothing
        }
        DataSet[] tableData = manager.getTableData(tableName);
        try{
            printTableValues(tableData);
        }catch(Exception e){
            //doNothing
        }
    }

    private void printTableHeader(String[] tableHeader) {
        if(tableHeader.length == 0) throw new IllegalArgumentException();
        String printValues ="";
        for (int j = 0; j < tableHeader.length; j++) {
            printValues += tableHeader[j] + "| ";
        }
        view.write("---------------------------");
        view.write(printValues);
        view.write("---------------------------");
    }

    private void printTableValues(DataSet[] tableData) {
        if(tableData.length == 0) throw new IllegalArgumentException();
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

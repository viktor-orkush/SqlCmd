package controler.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

import java.util.*;
import java.util.List;

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
        java.util.List<DataSet> tableData = manager.getTableData(tableName);
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

    private void printTableValues(List<DataSet> tableData) {
        if(tableData.size() == 0) throw new IllegalArgumentException();
        for (DataSet data : tableData) {
            printRow(data);
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

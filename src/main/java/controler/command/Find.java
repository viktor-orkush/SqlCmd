package controler.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

import javax.xml.crypto.Data;
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

        try{
            printTableHeader(tableName);
        }catch(Exception e){
            //doNothing
        }

        try{
            printTableValues(tableName);
        }catch(Exception e){
            //doNothing
        }
    }

    private void printTableHeader(String tableName) {
        List<String> tableHeader = manager.getTableHeader(tableName);
        if(tableHeader.size() == 0) throw new IllegalArgumentException();
        String printValues ="";
        for (String header : tableHeader) {
            printValues += header+ "| ";
        }
        view.write("---------------------------");
        view.write(printValues);
        view.write("---------------------------");
    }

    private void printTableValues(String tableName) {
        List<DataSet> tableData = manager.getTableData(tableName);
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

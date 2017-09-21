package model;

import java.util.LinkedList;
import java.util.List;

public class InMamryDatabaseManager implements DatabaseManager {
    public static final String TABLE_NAME = "users";

    private List<DataSet> dataList = new LinkedList<>();

    @Override
    public void connect(String database, String user, String password) {
//do nothing
    }

    @Override
    public List<String> getListTables() {
        List<String> tableList = new LinkedList<>();
        tableList.add(TABLE_NAME);
        return tableList;
    }

    @Override
    public void clear(String tableName) {
        dataList.clear();
    }

    @Override
    public void create(String tableName, DataSet input) {
        dataList.add(input);
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        return dataList;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (DataSet data : dataList) {
            if(Integer.parseInt(String.valueOf(data.get("id"))) == id){
                data.updateFrome(newValue);
            }
        }
    }

    @Override
    public List<String> getTableHeader(String tableName) {
        return new LinkedList<String>();
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}

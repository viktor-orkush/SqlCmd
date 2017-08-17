package model;

import java.util.Arrays;

/**
 * Created by Viktor on 11.08.2017.
 */
public class InMamryDatabaseManager implements DatabaseManager {
    public static final String TABLE_NAME = "users";

    private DataSet[] data = new DataSet[100];
    int length = 0;
    @Override
    public void connect(String database, String user, String password) {
//do nothing
    }

    @Override
    public String[] getListTables() {
        return new String[] {TABLE_NAME};
    }

    @Override
    public void clear(String tableName) {
        data = new DataSet[100];
        length = 0;
    }

    @Override
    public void create(String tableName, DataSet input) {
        data[length ++] = input;
    }

    @Override
    public DataSet[] getTableData(String tableName) {
        return Arrays.copyOf(data, length);
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (int index = 0; index < length; index++) {
            if(Integer.parseInt(String.valueOf(data[index].get("id"))) == id){
                data[index].updateFrome(newValue);
            }
        }
    }

    @Override
    public String[] getTableHeader(String tableName) {
        return new String[0];
    }
}

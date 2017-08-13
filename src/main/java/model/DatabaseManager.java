package model;

/**
 * Created by Viktor on 11.08.2017.
 */
public interface DatabaseManager {
    void connect(String database, String user, String password);

    String[] getListTables();

    void clear(String tableName);

    void create(String tableName, DataSet input);

    DataSet[] getTableData(String tableName);

    void update(String tableName, int id, DataSet newValue);
}

package model;

import java.util.List;

public interface DatabaseManager {
    void connect(String database, String user, String password);

    List<String> getListTables();

    void clear(String tableName);

    void create(String tableName, DataSet input);

    List<DataSet> getTableData(String tableName);

    void update(String tableName, int id, DataSet newValue);

    List<String> getTableHeader(String tableName);

    boolean isConnected();
}

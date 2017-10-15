package model;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseManager {
    void connect(String database, String user, String password) throws Exception;

    void disconnect() throws SQLException;

    List<String> getListTables() throws SQLException;

    void clear(String tableName) throws SQLException;

    void insert(String tableName, DataSet input) throws SQLException;

    List<DataSet> getTableData(String tableName) throws SQLException;

    void update(String tableName, int id, DataSet newValue) throws SQLException;

    List<String> getTableHeader(String tableName) throws SQLException;

    boolean isConnected();

    void createDataBase(String dbName) throws Exception;

    void createTable() throws SQLException;

    void deleteDataBase(String dbName) throws Exception;

    List<String> getListDataBase() throws Exception;
}

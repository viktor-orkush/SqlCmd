package model;

import model.exeption.DataBaseException;
import model.exeption.DeleteTableException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DatabaseManager {
    void connect(String database, String user, String password) throws ClassNotFoundException, SQLException;

    void connect(String user, String password) throws ClassNotFoundException, SQLException;

    void disconnect() throws SQLException;

    List<String> getListTables() throws SQLException;

    void clear(String tableName) throws SQLException;

    void insert(String tableName, DataSet input) throws SQLException;

    List<DataSet> getTableData(String tableName) throws SQLException;

    void update(String tableName, int id, DataSet newValue) throws SQLException;

    List<String> getTableHeader(String tableName) throws SQLException;

    boolean isConnected();

    void createDataBase(String dbName) throws DataBaseException, ClassNotFoundException, SQLException;

    void deleteDataBase(String dbName) throws SQLException, ClassNotFoundException, DataBaseException;

    void createTable(String tableName, ArrayList<String> columnName, ArrayList<String> columnType) throws SQLException;

    void deleteTable(String tableName) throws SQLException, DeleteTableException;

    List<String> getListDataBase() throws SQLException;
}

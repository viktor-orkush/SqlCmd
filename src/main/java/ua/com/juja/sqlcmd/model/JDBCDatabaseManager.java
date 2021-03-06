package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.model.exeption.DataBaseException;
import ua.com.juja.sqlcmd.model.exeption.DeleteTableException;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JDBCDatabaseManager implements DatabaseManager {
    private MyProperties prop = MyProperties.instance();
    private Connection connect;

    @Override
    public void connect(String database, String user, String password) throws ClassNotFoundException, SQLException {
        plugJDBCjar();
        try {
            verifyConnect();
            database += "?loggerLevel=OFF";
            connect = DriverManager.getConnection(prop.URL + "/" + database, user, password);
        } catch (SQLException e) {
            connect = null;
            throw new SQLException(String.format("Не получается подключиться к базе: %s под юзером: %s", database, user), e.getMessage());
        }
    }

    @Override
    public void connect(String user, String password) throws ClassNotFoundException, SQLException {
        plugJDBCjar();
        try {
            verifyConnect();
            connect = DriverManager.getConnection(prop.URL, user, password);
        } catch (SQLException e) {
            connect = null;
            throw new SQLException(String.format("Не получается подключиться к базе под юзером: %s: ", user), e.getMessage());
        }
    }

    private void plugJDBCjar() throws ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Не удалось подключить jdbc.jar драйвер к проекту!", e);
        }
    }

    @Override
    public void createDataBase(String dbName) throws DataBaseException, ClassNotFoundException, SQLException {
        if (getListDataBase().contains(dbName)) throw new DataBaseException(String.format("БД з таким именем: %s уже существует",dbName));

        try (Statement statement = connect.createStatement()) {
            String sql = "CREATE DATABASE " + dbName + " WITH ENCODING='UTF8'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException(String.format("Не получается создать базуданых: %s", dbName), e.getMessage());
        }
    }

    @Override
    public void deleteDataBase(String dbName) throws SQLException, ClassNotFoundException, DataBaseException {
        if (!getListDataBase().contains(dbName)) throw new DataBaseException("Такой БД не существует");

        try (Statement statement = connect.createStatement()) {
            String sql = "DROP DATABASE " + dbName;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException(String.format("Не получается удалить базуданых: %s по причине " + e.getMessage(), dbName));
        }
    }

    private void verifyConnect() throws SQLException {
        if (connect != null) {
            connect.close();
        }
    }

    @Override
    public List<String> getListDataBase() throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false");
             ResultSet rs = ps.executeQuery()) {

            List<String> DBList = new ArrayList<>();
            while (rs.next()) {
                DBList.add(rs.getString(1));
            }
            return DBList;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void createTable(String tableName, ArrayList<String> columnName, ArrayList<String> columnType) throws SQLException {
        try (Statement stmt = connect.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName;

//                    "CREATE TABLE IF NOT EXISTS " + tableName +
//                    "(id SERIAL NOT NULL PRIMARY KEY," +
//                    "name varchar(225) NOT NULL," +
//                    "password varchar(225))";
            if(columnName.size() >= 1 && columnType.size() >=1 && columnName.size()== columnType.size()) {
                sql += " (";
                for (int index = 0; index < columnName.size(); index++) {
                    if(index == 0) sql += " " + columnName.get(index) + " " + columnType.get(index)+ " NOT NULL PRIMARY KEY,";
                    else sql += " " + columnName.get(index) + " " + columnType.get(index) + ",";
                }
                sql = sql.substring(0, sql.length()-1);
                sql += ")";
            }
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void deleteTable(String tableName) throws SQLException, DeleteTableException {
        if (!getListTables().contains(tableName)) throw new DeleteTableException("Такой таблицы не существует");

        try (Statement statement = connect.createStatement()) {
            String sql = "DROP TABLE " + tableName;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException(String.format("Не получается удалить таблицу: %s", tableName), e.getMessage());
        }
    }

    @Override
    public void disconnect() throws SQLException {
        if (connect != null) {
            try {
                connect.close();
                connect = null;
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }
    }

    @Override
    public List<String> getListTables() throws SQLException {
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name " +
                     "FROM information_schema.tables " +
                     "WHERE table_schema = 'public' AND table_type = 'BASE TABLE'")) {
            List<String> listTable = new LinkedList<>();

            while (rs.next()) {
                String tableName = rs.getString("table_name");
                listTable.add(tableName);
            }
            return listTable;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void clear(String tableName) throws SQLException {
        String sql = "TRUNCATE  TABLE " + tableName;
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void insert(String tableName, DataSet input) throws SQLException {
        String[] arrHeaderTables = input.getNames();
        String headerTable = formatString(arrHeaderTables, "%s,");
        String values = formatDS(input, "'%s',");

        String sql = "INSERT INTO " + tableName + "( " + headerTable + " ) "
                + "VALUES (" + values + ");";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public List<DataSet> getTableData(String tableName) throws SQLException {
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {
            int size = getSize(tableName);
            List<DataSet> data = new LinkedList<>();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                for (int i = 1; i <= columnCount; i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getString(i));
                }
                data.add(dataSet);
            }
            return data;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private int getSize(String tableName) throws SQLException {
        try (Statement stmt = connect.createStatement();
             ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            rsCount.next();
            int size = rsCount.getInt(1);
            return size;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) throws SQLException {
        String[] name = newValue.getNames();
        Object[] value = newValue.getValues();

        String setData = formatString(name, "%s = ?,");
        try (PreparedStatement pstmt = connect.prepareStatement("UPDATE " + tableName
                + " SET " + setData
                + " WHERE id = " + id)) {
            for (int i = 0; i < value.length; i++) {
                pstmt.setObject(i + 1, value[i].toString());
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public List<String> getTableHeader(String tableName) throws SQLException {
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT column_name" +
                     " FROM information_schema.columns" +
                     " WHERE table_name   = '" + tableName + "'")) {
            List<String> columnList = new LinkedList<>();
            while (rs.next()) {
                String tableCol = rs.getString("column_name");
                columnList.add(tableCol);
            }
            return columnList;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean isConnected() {
        return connect != null;
    }

    private String formatString(String[] inputString, String formatType) {
        String string = "";
        for (int i = 0; i < inputString.length; i++) {
            string += String.format(formatType, inputString[i]);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    private String formatDS(DataSet inputDS, String formatType) {
        Object[] arrValues = inputDS.getValues();
        String string = "";
        for (int i = 0; i < arrValues.length; i++) {
            string += String.format(formatType, arrValues[i]);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }
}

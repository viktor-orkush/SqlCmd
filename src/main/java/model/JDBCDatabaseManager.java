package model;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connect;

    public void connect(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Pleas add jdbc jar driver to project", e);
        }
        try {
            if(connect != null){
                connect.close();
            }
            database += "?loggerLevel=OFF";
            connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + database, user, password);
        } catch (Exception e) {
            connect = null;
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s",
                            database, user),
                    e);
        }
    }

    @Override
    public String[] getListTables() {
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name " +
                     "FROM information_schema.tables " +
                     "WHERE table_schema = 'public' AND table_type = 'BASE TABLE'"))
        {
            String[] listTable = new String[10]; //todo magic number
            int index = 0;
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                listTable[index++] = tableName;
            }
            listTable = Arrays.copyOf(listTable, index, String[].class);
            return listTable;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void clear(String tableName) {
        String sql = "TRUNCATE  TABLE " + tableName;
        try (PreparedStatement stmt = connect.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String tableName, DataSet input) {
            String[] arrHeaderTables = input.getNames();
            String headerTable = formaString(arrHeaderTables, "%s,");
            String values = formatDS(input, "'%s',");

            String sql = "INSERT INTO " + tableName + "( " + headerTable + " ) "
                    + "VALUES (" + values + ");";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName))
        {
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
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    private int getSize(String tableName) {
        try (Statement stmt = connect.createStatement();
             ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName))
        {
            rsCount.next();
            int size = rsCount.getInt(1);
            return size;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        String[] name = newValue.getNames();
        Object[] value = newValue.getValues();

        String setData = formaString(name, "%s = ?,");
        try (PreparedStatement pstmt = connect.prepareStatement("UPDATE " + tableName
                                                                + " SET " + setData
                                                                + " WHERE id = " + id))
        {
            for (int i = 0; i < value.length; i++) {
                pstmt.setObject(i + 1, value[i].toString());
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getTableHeader(String tableName) {
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT column_name" +
                     " FROM information_schema.columns" +
                     " WHERE table_name   = '" + tableName + "'"))
        {
            String[] columnArr = new String[10];
            int index = 0;
            while (rs.next()) {
                String tableCol = rs.getString("column_name");
                columnArr[index++] = tableCol;
            }
            columnArr = Arrays.copyOf(columnArr, index, String[].class);
            return columnArr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isConnected() {
        return connect != null;
    }

    private String formaString(String[] inputString, String formatType) {
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

package model;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.Arrays;

public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connect;

    public void connect(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Pleas add jdbc jar driver to project", e);
        }
        try {
            database += "?loggerLevel=OFF";
            connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + database, user, password);
        }catch (Exception e) {
            connect = null;
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s",
                            database, user),
                    e);
        }
    }

    @Override
    public String[] getListTables() {
        try{
            Statement stmt = connect.createStatement();
            String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'";
            ResultSet rs = stmt.executeQuery(sql);
            String[] listTable = new String[10];
            int index = 0;
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                listTable[index++] = tableName;
            }
            listTable = Arrays.copyOf(listTable, index, String[].class);
            rs.close();
            stmt.close();
            return listTable;
        }
        catch(SQLException e){
//            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void clear(String tableName) {
        try {
            PreparedStatement stmt = connect.prepareStatement("TRUNCATE  TABLE " + tableName);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void create(String tableName, DataSet input) {
        try {
            String[] arrHeaderTables= input.getNames();

            String headerTable = formaString(arrHeaderTables, "%s,");

            String values = formatDS(input, "'%s',");

            String sql = "INSERT INTO " + tableName + "( "+ headerTable +" ) "
                    + "VALUES (" + values + ");";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e){
//            e.printStackTrace();
        }
    }

    @Override
    public DataSet[] getTableData(String tableName) {
        try {
            int size = getSize(tableName);

            DataSet[] data = new DataSet[size];
            int index = 0;

            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                data[index] = new DataSet();
                for (int i = 1; i <= columnCount; i++) {
                    data[index].put(rsmd.getColumnName(i), rs.getString(i));
                }

                index++;
            }
            rs.close();
            stmt.close();
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private int getSize(String tableName){
        try{
            Statement stmt = connect.createStatement();
            ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
            rsCount.next();
            int size = rsCount.getInt(1);
            stmt.close();
            return size;
        }
        catch(SQLException e){
//            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        try{
            String[] name = newValue.getNames();
            Object[] value = newValue.getValues();

            String setData = formaString(name, "%s = ?,");

            String SQL = "UPDATE " + tableName
                    + " SET " + setData
                    + " WHERE id = " + id;

            PreparedStatement pstmt = connect.prepareStatement(SQL);
            for (int i = 0; i < value.length; i++) {
                pstmt.setObject(i + 1, value[i].toString());
            }
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(SQLException e){
//            e.printStackTrace();
        }
    }

    @Override
    public String[] getTableHeader(String tableName) {
        try{
            Statement stmt = connect.createStatement();
            String sql = "SELECT column_name" +
                        " FROM information_schema.columns" +
                        " WHERE table_name   = '"+ tableName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            String[] columnArr = new String[10];
            int index = 0;
            while (rs.next()) {
                String tableCol = rs.getString("column_name");
                columnArr[index++] = tableCol;
            }
            columnArr = Arrays.copyOf(columnArr, index, String[].class);
            rs.close();
            stmt.close();
            return columnArr;
        }
        catch(SQLException e){
//            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isConnected() {
        return connect != null;
    }

    private String formaString(String[] inputString, String formatType) {
        String string ="";
        for (int i = 0; i < inputString.length; i++) {
            string += String.format(formatType, inputString[i]);
        }
        string = string.substring(0, string.length()-1);
        return string;
    }

    private String formatDS(DataSet inputDS, String formatType) {
        Object[] arrValues= inputDS.getValues();
        String string = "";
        for (int i = 0; i < arrValues.length; i++) {
            string += String.format(formatType, arrValues[i]);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }
}

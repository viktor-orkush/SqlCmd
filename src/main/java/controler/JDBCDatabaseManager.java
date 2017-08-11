package controler;

import java.sql.*;
import java.util.Arrays;

class Main{
    public static void main(String[] args) {
        JDBCDatabaseManager maneger = new JDBCDatabaseManager("sqlcmd", "admin", "admin");

        //given
        maneger.clear("users");

        //when
        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        maneger.create(input);

        DataSet newValue = new DataSet();
        newValue.put("name", "Victor");
        newValue.put("password", "passNew");
        maneger.update("users", 13, newValue);

        DataSet[] data = maneger.getTableData("users");
        System.out.println(data[0].toString());
    }
}

public class JDBCDatabaseManager implements DatabaseManager {
    Connection connect;

    public JDBCDatabaseManager(String database, String user, String password) {
        getConnection(database, user, password);
    }

    private void getConnection(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Pleas add jdbc jar driver to project");
            e.printStackTrace();
        }
        try {
            connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + database, user, password);
        } catch (SQLException e) {
            System.out.println("Cont get connection from database");
            e.printStackTrace();
            connect = null;
        }
    }

    @Override
    public String[] getListTable() {
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Override
    public void create(DataSet input) {
        try {
            String[] arrHeaderTables= input.getNames();

            String headerTable = formaString(arrHeaderTables, "%s,");

            String values = formatDS(input, "'%s',");

            String sql = "INSERT INTO users ( "+ headerTable +" ) "
                    + "VALUES (" + values + ");";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public DataSet[] getTableData(String tableName) {
        try{
            int size = getSize(tableName);

            DataSet[] data = new DataSet[size];
            int index = 0;

            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

            ResultSetMetaData rsmd=rs.getMetaData();

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
        }
        catch(SQLException e){
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
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

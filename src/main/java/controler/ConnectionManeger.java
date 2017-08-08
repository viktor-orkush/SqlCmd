package controler;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Arrays;


public class ConnectionManeger {
    Connection connect;

    public ConnectionManeger(String database, String user, String password) {
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

    //    delete
    private void deleteAll() throws SQLException {
        PreparedStatement stmt = connect.prepareStatement("TRUNCATE  TABLE users");
        stmt.executeUpdate();
        stmt.close();
    }

    //insert
    private void insert(String name, String pass) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO users (name, password) "
                + "VALUES ('" + name + "','" + pass + "');";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
    }

    //    select
    private void selectAll() throws SQLException, ClassNotFoundException {
        Statement stmt;
        stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            System.out.println("ID = " + id + " NAME = " + name + " passwors = " + password);
        }
        rs.close();
        stmt.close();
    }

    public String[] getListTable() throws SQLException, ClassNotFoundException {
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

    //    update
    public void updateById(int id, String name, String pass) throws SQLException {
        String SQL = "UPDATE users "
                + "SET name = ? "
                + "WHERE id = ?";

        PreparedStatement pstmt = connect.prepareStatement(SQL);
        pstmt.setString(1, name);
        pstmt.setInt(2, id);
        pstmt.executeQuery();
    }
}

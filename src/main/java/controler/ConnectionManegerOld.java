package controler;

import java.sql.*;

public class ConnectionManegerOld {
    public ConnectionManegerOld() {
    }

    public void run() throws SQLException, ClassNotFoundException {
        insert("test", "test");
        System.out.println("insert -------------------------");
        selectAll();
        System.out.println("updateById-------------------------");
        updateById(45, "test1", "test2");
        System.out.println("-------------------------");
        selectAll();
        System.out.println("deleteAll-------------------------");
        deleteAll();
        System.out.println("-------------------------");
        selectAll();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/sqlcmd", "admin",
                "admin");
    }

    //    delete
    private void deleteAll() throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = getConnection().prepareStatement("TRUNCATE  TABLE users");
        stmt.executeUpdate();
        stmt.close();
    }

    //insert
    private void insert(String name, String pass) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO users (name, password) "
                + "VALUES ('"+ name +"','" + pass + "');";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
    }

    //    select
    private void selectAll() throws SQLException, ClassNotFoundException {
        Statement stmt;
        stmt = getConnection().createStatement();
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

    //    update
    public void updateById(int id, String name, String pass) {
        String SQL = "UPDATE users "
                + "SET name = ? "

                + "WHERE id = ?";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(SQL);

            pstmt.setString(1, name);
//            pstmt.setString(2, pass);
            pstmt.setInt(2, id);
            pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

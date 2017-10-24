package model;

import model.exeption.DeleteTableException;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.MyPropertiesForTest.*;
import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
//    private static final String DB_NAME = "sqlcmd";
//    private static final String USER_NAME_TO_DB = "admin";
//    private static final String PASSWORD_TO_DB = "admin";
    public DatabaseManager manager;

    @Before
    public void setup() {
        MyPropertiesForTest.getProperties();
        manager = new JDBCDatabaseManager();
        CreateDB.runOnceForSettingDB();
        try {
            manager.connect(DB_NAME, DB_USER_NAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCreateDB() throws Exception {
        manager.createDataBase("sqlcmd2");
        assertEquals("[postgres, sqlcmd, sqlcmd2]", manager.getListDataBase().toString());
    }

    @Test
    public void testDeleteDB() throws Exception {
        manager.deleteDataBase("sqlcmd2");
        assertEquals("[postgres, sqlcmd]", manager.getListDataBase().toString());
    }

    @Test
    public void testCreateTB() throws SQLException {
        String tableName = "test";
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("id");
        columnName.add("name");

        ArrayList<String> columnType = new ArrayList<>();
        columnType.add("int");
        columnType.add("varchar(50)");
        manager.createTable(tableName, columnName, columnType);
        assertEquals("[users, test]", manager.getListTables().toString());
    }

    @Test
    public void testDeleteTB() throws SQLException, DeleteTableException {
        manager.deleteTable("test");
        assertEquals("[users]", manager.getListTables().toString());
    }

    @Test
    public void getListDataBase() throws Exception {
        List<String> listDB = manager.getListDataBase();
        String actual = Arrays.toString(listDB.toArray());
        assertEquals("[postgres, sqlcmd]", actual);
    }

    @Test
    public void getListTableTest() throws SQLException, ClassNotFoundException {
        List<String> listTable = manager.getListTables();
        String actual = Arrays.toString(listTable.toArray());
        assertEquals("[users]", actual);
    }

    @Test
    public void insertDataSetInTableTest() throws SQLException {
        //given
        manager.clear("users");

        //when
        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        manager.insert("users", input);

        //then
        List<DataSet> dataTable = manager.getTableData("users");
        assertEquals(1, dataTable.size());
        assertEquals("[id, name, password]", Arrays.toString(dataTable.get(0).getNames()));
        assertEquals("[13, Victor, 123]", Arrays.toString(dataTable.get(0).getValues()));
    }

    @Test
    public void updateTest() throws SQLException {
        //given
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        manager.insert("users", input);

        //when
        DataSet newValue = new DataSet();
        newValue.put("name", "Oleg");
        newValue.put("password", "passNew");
        manager.update("users", 13, newValue);

        //then
        List<DataSet> dataTable = manager.getTableData("users");
        assertEquals(1, dataTable.size());
        assertEquals("[id, name, password]", Arrays.toString(dataTable.get(0).getNames()));
        assertEquals("[13, Oleg, passNew]", Arrays.toString(dataTable.get(0).getValues()));
    }

    @Test
    public void getTableHeaderTest() throws SQLException {
        //given
        manager.clear("users");

        //when
        List<String> tableHeader = manager.getTableHeader("users");

        //then
        assertEquals(3, tableHeader.size());
        assertEquals("[id, name, password]", Arrays.toString(tableHeader.toArray()));
    }
}

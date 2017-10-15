package model;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
    DatabaseManager maneger;

    @Before
    public void setup() throws Exception {
        maneger = new JDBCDatabaseManager();
        maneger.createDataBase("sqlcmd2");
        maneger.connect("sqlcmd2", "admin", "admin");
    }

    /*@Test
    public void testCreateDB() throws Exception {
        maneger.createDataBase("sqlcmd2");
        assertEquals("[postgres, sqlcmd, sqlcmd2]", maneger.getListDataBase().toString());
    }*/

    @Test
    public void testDeleteDB() throws Exception {
        maneger.deleteDataBase("sqlcmd2");
        assertEquals("[postgres, sqlcmd]", maneger.getListDataBase().toString());
    }

    @Test
    public void testCreateTable() throws SQLException {
        maneger.createTable();
        assertEquals("[postgres, sqlcmd]", maneger.getListTables().toString());
    }

    @Test
    public void getListDataBase() throws Exception {
        List<String> listDB = maneger.getListDataBase();
        String actual = Arrays.toString(listDB.toArray());
        assertEquals("[sqlcmd]", actual);
    }

    @Test
    public void getListTableTest() throws SQLException, ClassNotFoundException {
        List<String> listTable = maneger.getListTables();
        String actual = Arrays.toString(listTable.toArray());
        assertEquals("[users]", actual);
    }

    @Test
    public void insertDataSetInTableTest() throws SQLException {
        //given
        maneger.clear("users");

        //when
        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        maneger.insert("users", input);

        //then
        List<DataSet> dataTable = maneger.getTableData("users");
        assertEquals(1, dataTable.size());
        assertEquals("[name, password, id]", Arrays.toString(dataTable.get(0).getNames()));
        assertEquals("[Victor, 123, 13]", Arrays.toString(dataTable.get(0).getValues()));
    }

    @Test
    public void updateTest() throws SQLException {
        //given
        maneger.clear("users");

        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        maneger.insert("users", input);

        //when
        DataSet newValue = new DataSet();
        newValue.put("name", "Victor");
        newValue.put("password", "passNew");
        maneger.update("users", 13, newValue);

        //then
        List<DataSet> dataTable = maneger.getTableData("users");
        assertEquals(1, dataTable.size());
        assertEquals("[name, password, id]", Arrays.toString(dataTable.get(0).getNames()));
        assertEquals("[Victor, passNew, 13]", Arrays.toString(dataTable.get(0).getValues()));
    }

    @Test
    public void getTableHeaderTest() throws SQLException {
        //given
        maneger.clear("users");

        //when
        List<String> tableHeader = maneger.getTableHeader("users");

        //then
        assertEquals(3, tableHeader.size());
        assertEquals("[name, password, id]", Arrays.toString(tableHeader.toArray()));
    }
}

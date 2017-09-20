package model;

import model.DataSet;
import model.DatabaseManager;
import model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
    DatabaseManager maneger;

    @Before
    public void setup(){
        maneger = new JDBCDatabaseManager();
        maneger.connect("sqlcmd", "admin", "admin");
    }

    @Test
    public void getListTableTest() throws SQLException, ClassNotFoundException {
        String[] listTable = maneger.getListTables();
        String actual = Arrays.toString(listTable);
        System.out.println(Arrays.toString(listTable));
        assertEquals("[test, users]", actual);
    }

    @Test
    public void insertDataSetInTableTest() {
        //given
        maneger.clear("users");

        //when
        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        maneger.create("users", input);

        //then
        List<DataSet> dataTable = maneger.getTableData("users");
        assertEquals(1, dataTable.size());
        assertEquals("[name, password, id]", Arrays.toString(dataTable.get(0).getNames()));
        assertEquals("[Victor, 123, 13]", Arrays.toString(dataTable.get(0).getValues()));
    }

    @Test
    public void updateTest() {
        //given
        maneger.clear("users");

        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        maneger.create("users", input);

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
    public void getTableHeaderTest() {
        //given
        maneger.clear("users");

        //when
        String[] tableHeader = maneger.getTableHeader("users");

        //then
        assertEquals(3, tableHeader.length);
        assertEquals("[name, password, id]", Arrays.toString(tableHeader));
    }
}

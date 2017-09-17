package model;

import model.DataSet;
import model.DatabaseManager;
import model.InMamryDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ImMamoryDatabaseManagerTest {
    DatabaseManager maneger;
    @Before
    public void setup(){
        maneger = new InMamryDatabaseManager();
    }


    @Test
    public void getListTableTest() throws SQLException, ClassNotFoundException {
        String[] listTable = maneger.getListTables();
        String actual = Arrays.toString(listTable);
        System.out.println(Arrays.toString(listTable));
        assertEquals("[users]", actual);
    }

    @Test
    public void getTableDateTest() {
        //given
        maneger.clear("users");

        //when
        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        maneger.create("users", input);

        //then
        DataSet[] dataTable = maneger.getTableData("users");
        assertEquals(1, dataTable.length);
        System.out.println(dataTable[0].toString());
        assertEquals("[id, name, password]", Arrays.toString(dataTable[0].getNames()));
        assertEquals("[13, Victor, 123]", Arrays.toString(dataTable[0].getValues()));
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
        DataSet[] dataTable = maneger.getTableData("users");
        assertEquals(1, dataTable.length);
        assertEquals("[id, name, password]", Arrays.toString(dataTable[0].getNames()));
        assertEquals("[13, Victor, passNew]", Arrays.toString(dataTable[0].getValues()));
    }
}

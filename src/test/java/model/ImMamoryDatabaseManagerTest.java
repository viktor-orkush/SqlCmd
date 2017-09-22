package model;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ImMamoryDatabaseManagerTest {
    DatabaseManager maneger;
    @Before
    public void setup(){
        maneger = new InMamryDatabaseManager();
    }


    @Test
    public void getListTableTest() throws SQLException, ClassNotFoundException {
        List<String> listTable = maneger.getListTables();
        String actual = Arrays.toString(listTable.toArray());
        System.out.println(Arrays.toString(listTable.toArray()));
        assertEquals("[users]", actual);
    }

    @Test
    public void getTableDateTest() throws SQLException {
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
        assertEquals("[id, name, password]", Arrays.toString(dataTable.get(0).getNames()));
        assertEquals("[13, Victor, 123]", Arrays.toString(dataTable.get(0).getValues()));
    }

    @Test
    public void updateTest() throws SQLException {
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
        assertEquals("[id, name, password]", Arrays.toString(dataTable.get(0).getNames()));
        assertEquals("[13, Victor, passNew]", Arrays.toString(dataTable.get(0).getValues()));
    }
}

package controler;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
    JDBCDatabaseManager maneger;
    @Before
    public void setup(){
        maneger = new JDBCDatabaseManager("sqlcmd", "admin", "admin");
    }


    @Test
    public void getListTableTest() throws SQLException, ClassNotFoundException {
        String[] listTable = maneger.getListTable();
        String actual = Arrays.toString(listTable);
        System.out.println(Arrays.toString(listTable));
        assertEquals("[test, users]", actual);
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
        maneger.create(input);

        //then
        DataSet[] dataTable = maneger.getTableData("users");
        assertEquals(1, dataTable.length);
        System.out.println(dataTable[0].toString());
        assertEquals("[name, password, id]", Arrays.toString(dataTable[0].getNames()));
        assertEquals("[Victor, 123, 13]", Arrays.toString(dataTable[0].getValues()));
    }

    @Test
    public void updateTest() {
        //given
        maneger.clear("users");

        DataSet input = new DataSet();
        input.put("id", "13");
        input.put("name", "Victor");
        input.put("password", "123");
        maneger.create(input);

        //when
        DataSet newValue = new DataSet();
        newValue.put("name", "Victor");
        newValue.put("password", "passNew");
        maneger.update("users", 13, newValue);

        //then
        DataSet[] dataTable = maneger.getTableData("users");
        assertEquals(1, dataTable.length);
        assertEquals("[name, password, id]", Arrays.toString(dataTable[0].getNames()));
        assertEquals("[Victor, passNew, 13]", Arrays.toString(dataTable[0].getValues()));
    }
}

package controler;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ConnectionManegerTest {
    ConnectionManeger maneger;
    @Before
    public void setup(){
        maneger = new ConnectionManeger("sqlcmd", "admin", "admin");
    }


    @Test
    public void getListTableTest() throws SQLException, ClassNotFoundException {
        String[] listTable = maneger.getListTable();
        String actual = Arrays.toString(listTable);
        System.out.println(Arrays.toString(listTable));
        assertEquals("[users, test]", actual);
    }
}

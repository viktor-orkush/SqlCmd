package model;

import model.exeption.DataBaseException;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
    DatabaseManager manager;
    public static boolean isInitialized = false;

    @Before
    public void setup() throws SQLException, ClassNotFoundException, DataBaseException {
        manager = new JDBCDatabaseManager();
        runOnceForSetingDB();
        try{
            manager.connect("sqlcmd2", "admin", "admin");
        }catch(Throwable e){
            System.out.println(e.getMessage());
        }
    }

    private void runOnceForSetingDB() throws ClassNotFoundException, SQLException {
        if(isInitialized) return;
        manager.connect("postgres", "admin");
        try {
            manager.deleteDataBase("sqlcmd2");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        try {
            manager.createDataBase("sqlcmd2");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        manager.connect("sqlcmd2", "admin", "admin");
        manager.createTable();
        isInitialized = true;
    }

    /*@Test
    public void testCreateDB() throws Exception {
        manager.createDataBase("sqlcmd2");
        assertEquals("[postgres, sqlcmd, sqlcmd2]", manager.getListDataBase().toString());
    }*/

 /*   @Test
    public void testDeleteDB() throws Exception {
        manager.deleteDataBase("sqlcmd2");
        assertEquals("[postgres, sqlcmd]", manager.getListDataBase().toString());
    }*/

    /*@Test
    public void testCreateTable() throws SQLException {
        manager.createTable();
        assertEquals("[users]", manager.getListTables().toString());
    }*/

        @Test
        public void getListDataBase () throws Exception {
            List<String> listDB = manager.getListDataBase();
            String actual = Arrays.toString(listDB.toArray());
            assertEquals("[postgres, sqlcmd, sqlcmd2]", actual);
        }

        @Test
        public void getListTableTest () throws SQLException, ClassNotFoundException {
            List<String> listTable = manager.getListTables();
            String actual = Arrays.toString(listTable.toArray());
            assertEquals("[users]", actual);
        }

        @Test
        public void insertDataSetInTableTest () throws SQLException {
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
        public void updateTest () throws SQLException {
            //given
            manager.clear("users");

            DataSet input = new DataSet();
            input.put("id", "13");
            input.put("name", "Victor");
            input.put("password", "123");
            manager.insert("users", input);

            //when
            DataSet newValue = new DataSet();
            newValue.put("name", "Victor");
            newValue.put("password", "passNew");
            manager.update("users", 13, newValue);

            //then
            List<DataSet> dataTable = manager.getTableData("users");
            assertEquals(1, dataTable.size());
            assertEquals("[id, name, password]", Arrays.toString(dataTable.get(0).getNames()));
            assertEquals("[13, Victor, passNew]", Arrays.toString(dataTable.get(0).getValues()));
        }

        @Test
        public void getTableHeaderTest () throws SQLException {
            //given
            manager.clear("users");

            //when
            List<String> tableHeader = manager.getTableHeader("users");

            //then
            assertEquals(3, tableHeader.size());
            assertEquals("[id, name, password]", Arrays.toString(tableHeader.toArray()));
        }
    }

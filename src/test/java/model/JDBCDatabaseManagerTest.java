package model;

import model.exeption.DataBaseException;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
    public DatabaseManager manager;

    @Before
    public void setup() {
        manager = new JDBCDatabaseManager();
        CreateDB.runOnceForSettingDB();
        try{
            manager.connect("sqlcmd", "admin", "admin");
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
    public void testCreateTable() throws SQLException {
        manager.createTable();
        assertEquals("[users]", manager.getListTables().toString());
    }

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

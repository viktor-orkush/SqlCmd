package controler.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import view.View;

import java.sql.SQLException;
import java.util.List;

import java.util.*;

import static org.junit.Assert.*;

public class TestCreate {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        view = Mockito.mock(View.class);
        manager = Mockito.mock(DatabaseManager.class);
        command = new Create(view, manager);
    }

    @Test
    public void testCreateCanProcessTrue() {
        boolean canProcess = command.canProcess("insert|users|id|12");
        assertTrue(canProcess);
    }

    @Test
    public void testCreateCanProcessFalse() {
        boolean canProcess = command.canProcess("createQWE|");
        assertFalse(canProcess);
    }

    @Test
    public void testCreateProcess() throws SQLException {
        List<String> headerList = new LinkedList<>();
        headerList.add("id");
        headerList.add("name");
        headerList.add("password");

        Mockito.when(manager.getTableHeader("users")).thenReturn(headerList);

        command.process("insert|users|id|1|user|victor|password|123");
        Mockito.verify(view, Mockito.atLeastOnce()).write("Данные успешно добавлены в таблтицу users");
    }

    @Test
    public void testCreateProcessWithoutOneParameters() throws SQLException {
        List<String> headerList = new LinkedList<>();
        headerList.add("id");
        headerList.add("name");
        headerList.add("password");

        Mockito.when(manager.getTableHeader("users")).thenReturn(headerList);
        try {
            command.process("insert|users|id|1|user|victor|password|");
        } catch (IllegalArgumentException e) {
            assertEquals("Неверное количество введеных параметров", e.getMessage());
        }
    }

    @Test
    public void testCreateProcessWithoutNameTable() throws SQLException {
        List<String> headerList = new LinkedList<>();
        headerList.add("id");
        headerList.add("name");
        headerList.add("password");

        Mockito.when(manager.getTableHeader("users")).thenReturn(headerList);

        try {
            command.process("insert|");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            assertEquals("Неверное количество введеных параметров", e.getMessage());
        }
    }
}

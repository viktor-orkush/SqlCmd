package controler.command;

import model.DataSet;
import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import view.View;

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
        boolean canProcess = command.canProcess("create|users|id|12");
        assertTrue(canProcess);
    }

    @Test
    public void testCreateCanProcessFalse() {
        boolean canProcess = command.canProcess("createQWE|");
        assertFalse(canProcess);
    }

    @Test
    public void testCreateProcess() {
        Mockito.when(manager.getTableHeader("users")).thenReturn(new String[]{"id", "user", "password"});
        command.process("create|users|id|1|user|victor|password|123");
        Mockito.verify(view, Mockito.atLeastOnce()).write("Данные успешно добавлены в таблтицу users");
    }

    @Test
    public void testCreateProcessWithoutOneParameters() {
        Mockito.when(manager.getTableHeader("users")).thenReturn(new String[]{"id", "user", "password"});
        try {
            command.process("create|users|id|1|user|victor|password|");
        } catch (IllegalArgumentException e) {
            assertEquals("Неверное количество введеных параметров", e.getMessage());
        }
    }

    @Test
    public void testCreateProcessWithoutNameTable() {
        Mockito.when(manager.getTableHeader("users")).thenReturn(new String[]{"id", "user", "password"});
        try {
            command.process("create|");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            assertEquals("Неверное количество введеных параметров", e.getMessage());
        }
    }
}

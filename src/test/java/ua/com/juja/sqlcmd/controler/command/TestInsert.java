package ua.com.juja.sqlcmd.controler.command;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.Insert;
import ua.com.juja.sqlcmd.controller.command.Exeption.ExitException;
import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

import java.util.*;

import static org.junit.Assert.*;

public class TestInsert {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        view = Mockito.mock(View.class);
        manager = Mockito.mock(DatabaseManager.class);
        command = new Insert(view, manager);
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
    public void testCreateProcess() throws SQLException, ExitException, IncorrectInputArgumentException {
        List<String> headerList = new LinkedList<>();
        headerList.add("id");
        headerList.add("name");
        headerList.add("password");

        Mockito.when(manager.getTableHeader("users")).thenReturn(headerList);

        command.process("insert|users|id|1|user|victor|password|123");
        Mockito.verify(view, Mockito.atLeastOnce()).write("Данные успешно добавлены в таблтицу users");
    }

    @Test
    public void testCreateProcessWithoutOneParameters() throws SQLException, ExitException, IncorrectInputArgumentException {
        List<String> headerList = new LinkedList<>();
        headerList.add("id");
        headerList.add("name");
        headerList.add("password");

        Mockito.when(manager.getTableHeader("users")).thenReturn(headerList);
        try {
            command.process("insert|users|id|1|user|victor|password|");
        } catch (IncorrectInputArgumentException e) {
            assertEquals("Введено не верное количество аргументов", e.getMessage());
        }
    }

    @Test
    public void testCreateProcessWithoutNameTable() throws SQLException, ExitException, IncorrectInputArgumentException {
        List<String> headerList = new LinkedList<>();
        headerList.add("id");
        headerList.add("name");
        headerList.add("password");

        Mockito.when(manager.getTableHeader("users")).thenReturn(headerList);

        try {
            command.process("insert|");
            fail("Expected IncorrectInputArgumentException.");
        } catch (IncorrectInputArgumentException e) {
            assertEquals("Введено не верное количество аргументов", e.getMessage());
        }
    }
}

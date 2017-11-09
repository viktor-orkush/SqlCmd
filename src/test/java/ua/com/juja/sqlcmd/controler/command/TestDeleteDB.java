package ua.com.juja.sqlcmd.controler.command;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.DeleteDB;
import ua.com.juja.sqlcmd.controller.command.Exeption.ExitException;
import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class TestDeleteDB {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        view = Mockito.mock(View.class);
        manager = Mockito.mock(DatabaseManager.class);

        command = new DeleteDB(view, manager);
    }

    @Test
    public void testCreateCanProcessTrue() {
        boolean canProcess = command.canProcess("deleteDB|users");
        assertTrue(canProcess);
    }

    @Test
    public void testCreateCanProcessFalse() {
        boolean canProcess = command.canProcess("deleteDCC|");
        assertFalse(canProcess);
    }

    @Test
    public void testDeleteDBProcess() throws ExitException, IncorrectInputArgumentException {
        command.process("deleteDB|users");
        Mockito.verify(view, Mockito.atLeastOnce()).write("База данных users успешно удалена ");
    }

    @Test
    public void testDeleteDBProcessWithTwoParameters() {
        try {
            command.process("deleteDB|users|2");
        } catch (IncorrectInputArgumentException e) {
            assertEquals("Введено не верное количество аргументов", e.getMessage());
        } catch (ExitException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateProcessWithoutNameTable() throws SQLException, ExitException {
        try {
            command.process("deleteDB|");
            fail("Expected IncorrectInputArgumentException");
        } catch (IncorrectInputArgumentException e) {
            assertEquals("Введено не верное количество аргументов", e.getMessage());
        }
    }
}

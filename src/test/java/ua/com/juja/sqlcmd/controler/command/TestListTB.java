package ua.com.juja.sqlcmd.controler.command;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.Exeption.ExitException;
import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;
import ua.com.juja.sqlcmd.controller.command.ListTB;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class TestListTB {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup(){
        view = Mockito.mock(View.class);
        manager = Mockito.mock(DatabaseManager.class);

        command = new ListTB(view, manager);
    }

    @Test
    public void testListProcess() throws SQLException, ExitException, IncorrectInputArgumentException {
        java.util.List<String> tablesList = new LinkedList<>();
        tablesList.add("users");
        tablesList.add("test");
        Mockito.when( manager.getListTables()).thenReturn(tablesList);

        command.process("list");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals(
                "[[users, test]]", captor.getAllValues().toString());
    }

    @Test
    public void testListProcessWithoutParameters() throws SQLException, ExitException, IncorrectInputArgumentException {

        Mockito.when( manager.getListTables()).thenReturn(new LinkedList<>());

        command.process("list");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
       // Mockito.verify(view, Mockito.atLeastOnce()).write(captor.getValue().toString());
        assertEquals("[]", captor.getAllValues().toString());
    }
}

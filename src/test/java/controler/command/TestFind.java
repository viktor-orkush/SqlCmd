package controler.command;

import controller.command.Command;
import controller.command.Exeption.ExitException;
import controller.command.Exeption.IncorrectInputArgumentException;
import controller.command.Find;
import model.DataSet;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import view.View;
import model.DatabaseManager;
import org.junit.Before;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFind {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup(){
        view = Mockito.mock(View.class);
        manager = Mockito.mock(DatabaseManager.class);
        command = new Find(view, manager);
    }

    @Test
    public void testFindCanProcessTrue(){
        boolean canProcess = command.canProcess("find|users");
        assertTrue(canProcess);
    }

    @Test
    public void testFindCanProcessFalse(){
        boolean canProcess = command.canProcess("findqwe|");
        assertFalse(canProcess);
    }

    @Test
    public void testFindProcess() throws SQLException, ExitException, IncorrectInputArgumentException {
        List<String> headerList = new LinkedList<>();
        headerList.add("id");
        headerList.add("name");
        headerList.add("password");

        Mockito.when(manager.getTableHeader("users"))
                .thenReturn(headerList);

        DataSet user1 = new DataSet();
        user1.put("id", "1");
        user1.put("name", "Victor");
        user1.put("password", "123");

        DataSet user2 = new DataSet();
        user2.put("id", "2");
        user2.put("name", "Nastya");
        user2.put("password", "456");

        List<DataSet> dataList = new LinkedList<>();
        dataList.add(user1);
        dataList.add(user2);

        Mockito.when(manager.getTableData("users")).thenReturn(dataList);

        command.process("find|users");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals(
                "[---------------------------, " +
                        "id| name| password| ," +
                        " ---------------------------, " +
                        "1| Victor| 123| , " +
                        "2| Nastya| 456| ]", captor.getAllValues().toString());
    }

    @Test
    public void testFindProcessWithoutParameters() throws SQLException, ExitException, IncorrectInputArgumentException {
        List<String> headerList = new LinkedList<>();
        headerList.add("id");
        headerList.add("name");
        headerList.add("password");

        Mockito.when(manager.getTableHeader("users"))
                .thenReturn(headerList);

        command.process("find|users");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals(
                "[---------------------------, " +
                        "id| name| password| ," +
                        " ---------------------------]" , captor.getAllValues().toString());
    }
}

package controler.command;

import model.DataSet;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import view.View;
import model.DatabaseManager;
import org.junit.Before;
import org.mockito.Mockito;

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
    public void testFindProcess(){
        Mockito.when(manager.getTableHeader("users"))
                .thenReturn(new String[] {"id", "name", "password"});

        DataSet user1 = new DataSet();
        user1.put("id", "1");
        user1.put("name", "Victor");
        user1.put("password", "123");

        DataSet user2 = new DataSet();
        user2.put("id", "2");
        user2.put("name", "Nastya");
        user2.put("password", "456");

        DataSet[] data = new DataSet[] {user1, user2};

        Mockito.when(manager.getTableData("users")).thenReturn(data);

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
    public void testFindProcessWithoutParameters(){
        Mockito.when(manager.getTableHeader("users"))
                .thenReturn(new String[] {"id", "name", "password"});

        command.process("find|users");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals(
                "[---------------------------, " +
                        "id| name| password| ," +
                        " ---------------------------]" , captor.getAllValues().toString());
    }
}

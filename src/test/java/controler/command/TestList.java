package controler.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import view.View;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class TestList {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup(){
        view = Mockito.mock(View.class);
        manager = Mockito.mock(DatabaseManager.class);

        command = new List(view, manager);
    }

    @Test
    public void testListProcess(){
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
    public void testListProcessWithoutParameters(){

        Mockito.when( manager.getListTables()).thenReturn(new LinkedList<>());

        command.process("list");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals(
                "[[]]", captor.getAllValues().toString());
    }
}

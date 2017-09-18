package controler.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import view.View;

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

        Mockito.when( manager.getListTables()).thenReturn(new String[] {"users", "test"});

        command.process("list");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals(
                "[[users, test]]", captor.getAllValues().toString());
    }

    @Test
    public void testListProcessWithoutParameters(){

        Mockito.when( manager.getListTables()).thenReturn(new String[0]);

        command.process("list");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals(
                "[[]]", captor.getAllValues().toString());
    }
}

package controler.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import view.View;

import static org.junit.Assert.*;

public class TestClear {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup (){
        view = Mockito.mock(View.class);
        manager = Mockito.mock(DatabaseManager.class);
        command = new Clear(view, manager);

    }

    @Test
    public void testClearCanProcessTrue(){
        boolean canProcess = command.canProcess("clear|");
        assertTrue(canProcess);
    }

    @Test
    public void testClearCanProcessFalse(){
        boolean canProcess = command.canProcess("clearQWE");
        assertFalse(canProcess);
    }

    @Test
    public void testClearProcessWhithIllegalArgument(){
        try{
            command.process("clear");
            fail("Expected IllegalArgumentException.");
        } catch(Exception e){
            assertEquals("Введено не правельное количество аргументов", e.getMessage());
        }
    }
}

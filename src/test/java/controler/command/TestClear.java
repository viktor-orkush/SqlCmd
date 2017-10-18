package controler.command;

import controller.command.Clear;
import controller.command.Command;
import controller.command.Exeption.ExitException;
import controller.command.Exeption.IncorrectInputArgumentException;
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
    public void testClearProcessWhithIllegalArgument() throws ExitException, IncorrectInputArgumentException {
        try{
            command.process("clear");
            fail("Expected IllegalArgumentException.");
        } catch(Exception e){
            assertEquals("Введено не верное количество аргументов", e.getMessage());
        }
    }

    @Test
    public void testClearProcess() throws ExitException, IncorrectInputArgumentException {
        try{
            command.process("clear|users");
        } catch(Exception e){
        }
        Mockito.verify(view).write("Данные из таблици users успешго очищены!");
    }
}

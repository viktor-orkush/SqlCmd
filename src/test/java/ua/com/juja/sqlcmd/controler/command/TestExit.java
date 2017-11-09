package ua.com.juja.sqlcmd.controler.command;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.Exeption.ExitException;
import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;
import ua.com.juja.sqlcmd.controller.command.Exit;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestExit {
    View view = Mockito.mock(View.class);
    Command command = new Exit(view);

    @Test
    public void testExitCanProcess(){
        boolean canProcess = command.canProcess("exit");
        assertTrue(canProcess);
    }

    @Test
    public void testExitCanProcessQWE(){
        boolean canProcess = command.canProcess("QWE");
        assertFalse(canProcess);
    }


    @Test
    public void testExitProcess() throws ExitException, IncorrectInputArgumentException {
        try{
            command.process("exit");
        } catch(Exception e){

        }
        Mockito.verify(view).write("До скорой встречи!");
    }
}

package controler.command;

import org.junit.Test;
import org.mockito.Mockito;
import view.View;

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
    public void testExit(){
        try{
            command.process("exit");
        } catch(Exception e){

        }
        Mockito.verify(view).write("До скорой встречи!");
    }
}

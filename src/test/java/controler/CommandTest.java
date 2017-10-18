package controler;

import controller.command.Command;
import controller.command.Exeption.ExitException;
import controller.command.Exeption.IncorrectInputArgumentException;
import controller.command.Find;
import model.DatabaseManager;
import model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import view.Console;
import view.View;
import static org.junit.Assert.assertTrue;

/**
 * Created by Viktor on 17.08.2017.
 */
public class CommandTest {
    DatabaseManager maneger;
    View view;

    @Before
    public void setup() throws Exception {
        maneger = new JDBCDatabaseManager();
        maneger.connect("sqlcmd", "admin", "admin");
        view = new Console();
    }

    @Test
    public void findTest() throws ExitException, IncorrectInputArgumentException {
        Command command = new Find(view, maneger);

        String input = "find|users";
        command.process(input);

        assertTrue(command.canProcess(input));
    }
}

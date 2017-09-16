package controler;

import controler.command.Command;
import controler.command.Find;
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
    public void setup(){
        maneger = new JDBCDatabaseManager();
        maneger.connect("sqlcmd", "admin", "admin");
        view = new Console();
    }

    @Test
    public void findTest() {
        Command command = new Find(view, maneger);

        String input = "find|users";
        command.process(input);

        assertTrue(command.canProcess(input));
    }
}

package ua.com.juja.sqlcmd.controler;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.Exeption.ExitException;
import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;
import ua.com.juja.sqlcmd.controller.command.Find;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;
import static org.junit.Assert.assertTrue;

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

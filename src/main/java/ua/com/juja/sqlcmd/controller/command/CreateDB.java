package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.controller.command.Exeption.ExitException;
import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.exeption.DataBaseException;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class CreateDB implements Command {

    private static final String EXAM_COMMAND = "createDB|nameDB";
    private final DatabaseManager manager;
    private final View view;

    public CreateDB(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createDB|");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException, ExitException {
        String[] splitReadLine = command.split("[|]");
        if(splitReadLine.length != parametersLength(EXAM_COMMAND)) throw new IncorrectInputArgumentException();
        String database = splitReadLine[1];
        try {
            manager.createDataBase(database);
            view.write(String.format("База данных %s успешно создана ", database));
        } catch (DataBaseException | ClassNotFoundException | SQLException e) {
            view.write(e.getMessage());
        }
    }
}

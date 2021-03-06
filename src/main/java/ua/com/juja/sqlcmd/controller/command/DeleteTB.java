package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.controller.command.Exeption.ExitException;
import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.exeption.DeleteTableException;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class DeleteTB implements Command {
    private static final String EXAM_COMMAND = "deleteTB|nameTB";
    private final View view;
    private final DatabaseManager manager;

    public DeleteTB(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("deleteTB|");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException, ExitException {
        String[] splitReadLine = command.split("[|]");
        if(splitReadLine.length != parametersLength(EXAM_COMMAND)) throw new IncorrectInputArgumentException();
        String tableName = splitReadLine[1];
        try {
            manager.deleteTable(tableName);
            view.write(String.format("Таблица %s успешно удалена ", tableName));
        } catch (SQLException | DeleteTableException e) {
            view.write(e.getMessage());
        }
    }
}

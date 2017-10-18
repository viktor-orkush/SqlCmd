package controller.command;

import controller.command.Exeption.ExitException;
import controller.command.Exeption.IncorrectInputArgumentException;
import model.DatabaseManager;
import model.exeption.DataBaseException;
import view.View;

import java.sql.SQLException;

public class DeleteDB implements Command {
    private static final String EXAM_COMMAND = "deleteDB|nameDB";
    private final View view;
    private final DatabaseManager manager;

    public DeleteDB(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("deleteDB|");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException, ExitException {
        String[] splitReadLine = command.split("/|");
        if(splitReadLine.length != parametersLength(EXAM_COMMAND)) throw new IncorrectInputArgumentException();
        String database = splitReadLine[1];
        try {
            manager.deleteDataBase(database);
        } catch (SQLException e) {
            view.write(e.getMessage());
        } catch (ClassNotFoundException e) {
            view.write(e.getMessage());
        } catch (DataBaseException e) {
            view.write(e.getMessage());
        }
    }
}

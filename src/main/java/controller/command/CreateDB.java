package controller.command;

import com.sun.javafx.binding.StringFormatter;
import controller.command.Exeption.ExitException;
import controller.command.Exeption.IncorrectInputArgumentException;
import model.DatabaseManager;
import model.exeption.DataBaseException;
import view.View;

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
        String[] splitReadLine = command.split("/|");
        if(splitReadLine.length != parametersLength(EXAM_COMMAND)) throw new IncorrectInputArgumentException();
        String database = splitReadLine[1];
        try {
            manager.createDataBase(database);
        } catch (DataBaseException e) {
            view.write(e.getMessage());
        } catch (ClassNotFoundException e) {
            view.write(e.getMessage());
        } catch (SQLException e) {
            view.write(e.getMessage());
        }
        view.write(String.format("База данных %s успешно создана ", database));
    }
}

package controller.command;

import controller.command.Exeption.ExitException;
import controller.command.Exeption.IncorrectInputArgumentException;
import model.DatabaseManager;
import view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class CreateTB implements Command {
    private static final String EXAM_COMMAND = "createTB|user|id|int";
    private final View view;
    private final DatabaseManager manager;

    public CreateTB(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;}

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createTB|");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException, ExitException {
        String[] splitReadLine = command.split("[|]");
        if(splitReadLine.length < parametersLength(EXAM_COMMAND)) throw new IncorrectInputArgumentException();

        String tableName = splitReadLine[1];
        ArrayList<String> columnName = new ArrayList<>();
        ArrayList<String> columnType = new ArrayList<>();

        for (int index = 2; index < splitReadLine.length; index += 2) {
            columnName.add(splitReadLine[index]);
            columnType.add(splitReadLine[index+1]);
        }
        try {
            manager.createTable(tableName, columnName, columnType);
            view.write(String.format("Таблица %s успешно создана", tableName));
        } catch (SQLException e) {
            view.write(e.getMessage());
        }
    }
}

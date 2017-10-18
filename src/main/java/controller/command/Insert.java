package controller.command;

import controller.command.Exeption.IncorrectInputArgumentException;
import model.DataSet;
import model.DatabaseManager;
import view.View;

import java.sql.SQLException;

public class Insert implements Command {

    private View view;
    private DatabaseManager manager;

    public Insert(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException {
        String argumentArray[] = command.split("[|]");
        if (argumentArray.length < 3)
            throw new IncorrectInputArgumentException();
        String tableName = argumentArray[1];
        java.util.List<String> tableHeader = null;
        try {
            tableHeader = manager.getTableHeader(tableName);
            int inputParameters = (argumentArray.length - 2) / 2;
            if (tableHeader.size() != inputParameters) {
                throw new IncorrectInputArgumentException();
            }
            DataSet dataSet = new DataSet();
            for (int index = 2; index < argumentArray.length; index += 2) {
                dataSet.put(argumentArray[index], argumentArray[index + 1]);
            }
            manager.insert(tableName, dataSet);
            view.write(String.format("Данные успешно добавлены в таблтицу %s", tableName));
        } catch (SQLException e) {
            view.write(String.format("Не удалось добавить данные в таблицу %s по причине: ", tableName) + e.getMessage());
        }
    }
}

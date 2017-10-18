package controller.command;

import controller.command.Exeption.IncorrectInputArgumentException;
import model.DatabaseManager;
import view.View;

import java.sql.SQLException;

public class Clear implements Command{
    private View view;
    private DatabaseManager manager;

    public Clear(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException {
        String[] argumentArray = command.split("[|]");
        if(argumentArray.length != 2) throw new IncorrectInputArgumentException("Введено не верное количество аргументов");
        String tableName = argumentArray[1];

        try {
            manager.clear(tableName);
            view.write(String.format("Данные из таблици %s успешго очищены!", tableName));
        } catch (SQLException e) {
            view.write(String.format("Ошибка удаления данных из таблицы %s по причине: ", tableName) + e.getMessage());
        }
    }
}

package controler.command;

import model.DatabaseManager;
import view.View;

/**
 * Created by Viktor on 16.09.2017.
 */
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
    public void process(String command) {
        String[] argumentArray = command.split("[|]");
        if(argumentArray.length != 2) throw new IllegalArgumentException("Введено не правельное количество аргументов");
        String tableName = argumentArray[1];
        manager.clear(tableName);
        view.write("Данные очищены!");
    }
}

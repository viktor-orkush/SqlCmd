package controler.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

/**
 * Created by Viktor on 16.09.2017.
 */
public class Create implements Command {

    private View view;
    private DatabaseManager manager;

    public Create(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String argumentArray[] = command.split("[|]");
        String tableName = argumentArray[1];
        DataSet dataSet = new DataSet();
        for (int index = 2; index < argumentArray.length; index+=2){
            dataSet.put(argumentArray[index], argumentArray[index+1]);
        }
        manager.create(tableName, dataSet);
        view.write("Данные добавлены!");
    }
}

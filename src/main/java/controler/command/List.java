package controler.command;

import model.DatabaseManager;
import view.View;

import java.util.Arrays;

/**
 * Created by Viktor on 15.08.2017.
 */
public class List implements Command {

    private View view;
    private DatabaseManager manager;

    public List(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return "list".equals(command);
    }

    @Override
    public void process(String command) {
        String[] tableList = manager.getListTables();
        view.write(Arrays.toString(tableList));
    }
}

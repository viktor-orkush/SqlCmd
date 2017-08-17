package controler.command;

import controler.command.Command;
import model.DatabaseManager;
import view.View;

/**
 * Created by Viktor on 17.08.2017.
 */
public class Unsupported implements Command {

    DatabaseManager manager;
    View view;

    public Unsupported(View view, DatabaseManager manager) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("Несуществующая команда: " + command);
    }
}

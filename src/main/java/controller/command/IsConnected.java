package controller.command;

import model.DatabaseManager;
import view.View;

public class IsConnected implements Command {

    DatabaseManager manager;
    View view;

    public IsConnected(View view, DatabaseManager manager) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("Вы не можете пользоваться командой %s пока не подключитесь с помошью команды connect", command));
    }
}

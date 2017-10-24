package controller;

import controller.command.*;
import model.DatabaseManager;
import view.View;

public class MainController {
    View view;
    Command[] commands;

    String input;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        commands = new Command[]{
                new Exit(view),
                new Help(view),
                new ConnectGlobal(view, manager),
                new IsConnected(view, manager),
                new CreateDB(view, manager),
                new CreateTB(view, manager),
                new DeleteDB(view, manager),
                new DeleteTB(view, manager),
                new ListTB(view, manager),
                new Find(view, manager),
                new Insert(view, manager),
                new Clear(view, manager),
                new Unsupported(view, manager)};
    }

    public void run() {
        try {
            while (true) {
                view.write("Введите команду или help для помощи");
                input = view.read();
                for (Command command : commands) {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            String exception = "";
            exception += e.getMessage();
            view.write(exception);
        }
    }
}

package controller;

import controller.command.*;
import controller.command.Exeption.ExitException;
import controller.command.Exeption.IncorrectInputArgumentException;
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
                new Connect(view, manager),
                new IsConnected(view, manager),
                new CreateDB(view, manager),
                new CreateTB(view, manager),
                new DeleteDB(view, manager),
                new DeleteTB(view, manager),
                new ListDB(view, manager),
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
                try {
                    input = view.read();
                    for (Command command : commands) {
                        if (command.canProcess(input)) {
                            command.process(input);
                            break;
                        }
                    }
                } catch (IncorrectInputArgumentException e) {
                    String exception = "";
                    exception += e.getMessage();
                    if (exception != null) view.write(exception);
                }
            }
        } catch (ExitException e) {
            //System.exit(2);
        }
    }
}

package controler;

import controler.command.*;
import model.DatabaseManager;
import view.View;

public class MainControler {
    View view;
    Command[] commands;

    String input;

    public MainControler(View view, DatabaseManager manager) {
        this.view = view;
        commands = new Command[]{
                new Exit(view),
                new Help(view),
                new Connect(view, manager),
                new IsConnected(view, manager),
                new List(view, manager),
                new Find(view, manager),
                new Create(view, manager),
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

        }
    }
}

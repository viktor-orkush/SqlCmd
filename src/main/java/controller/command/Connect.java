package controller.command;

import controller.command.Exeption.IncorrectInputArgumentException;
import model.DatabaseManager;
import view.View;

public class Connect implements Command {
    View view;
    DatabaseManager manager;
    private static final String EXAM_COMMAND = "connect|user|pass|db";

    public Connect(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect");
    }

    @Override
    public void process(String command) {
        try {
            String[] splitReadLine = command.split("[|]");
            if (splitReadLine.length != parametersLength(EXAM_COMMAND))
                throw new IncorrectInputArgumentException("Введено не верное количество аргументов разделенных знаком '|', ожидается 3, но есть: " + splitReadLine.length);
            String database = splitReadLine[1];
            String user = splitReadLine[2];
            String pass = splitReadLine[3];
            manager.connect(database, user, pass);
            view.write("Привет " + user);
        } catch (Exception e) {
            String massage = " ";
            view.write("Не удача по причине " + e.getMessage() + massage);
            view.write("Повтори попитку");
        }
    }
}



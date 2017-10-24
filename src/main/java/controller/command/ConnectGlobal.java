package controller.command;

import controller.command.Exeption.IncorrectInputArgumentException;
import model.DatabaseManager;
import view.View;

import java.sql.SQLException;

public class ConnectGlobal implements Command {
    View view;
    DatabaseManager manager;
    private static final String EXAM_COMMAND = "connect|user|pass|db";

    public ConnectGlobal(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException {
        String database = "";
        String user = "";
        String pass = "";
        String[] splitReadLine = command.split("[|]");
        if (splitReadLine.length < parametersLength(EXAM_COMMAND))
            throw new IncorrectInputArgumentException("Введено не верное количество аргументов");
        try {
            database = splitReadLine[1];
            user = splitReadLine[2];
            pass = splitReadLine[3];
            manager.connect(database, user, pass);
            view.write("Привет " + user);
        } catch (SQLException | ClassNotFoundException e) {
            view.write("Не удача по причине " + e.getMessage());
            view.write("Повторите попытку");
        }
    }
}



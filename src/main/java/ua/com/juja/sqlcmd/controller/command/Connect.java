package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Connect implements Command {
    View view;
    DatabaseManager manager;
    private static final String EXAM_COMMAND_GLOBAL_DB = "connect|user|pass";
    private static final String EXAM_COMMAND_SPESHEL_DB = "connect|user|pass|db";

    public Connect(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) throws IncorrectInputArgumentException {
        String database = "";
        String user = "";
        String pass = "";
        String[] splitReadLine = command.split("[|]");

        if (splitReadLine.length < parametersLength(EXAM_COMMAND_GLOBAL_DB) || splitReadLine.length > parametersLength(EXAM_COMMAND_SPESHEL_DB))
            throw new IncorrectInputArgumentException("Введено не верное количество аргументов");
        try {
            if(splitReadLine.length == parametersLength(EXAM_COMMAND_GLOBAL_DB)){
                user = splitReadLine[1];
                pass = splitReadLine[2];
                manager.connect(user, pass);
            }
            if(splitReadLine.length == parametersLength(EXAM_COMMAND_SPESHEL_DB)){
                database = splitReadLine[1];
                user = splitReadLine[2];
                pass = splitReadLine[3];
                manager.connect(database, user, pass);
            }
            view.write("Привет " + user);
        } catch (SQLException | ClassNotFoundException e) {
            view.write("Не удача по причине " + e.getMessage());
            view.write("Повторите попытку");
        }
    }
}



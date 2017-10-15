package controler.command;

import model.DatabaseManager;
import view.View;

public class Connect implements Command {
    View view;
    DatabaseManager manager;

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
        String examCommand = "connect|user|pass|db";
        try {
            String[] splitReadLine = command.split("[|]");
            if (splitReadLine.length != parametersLength(examCommand) ) {
                throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 3, но есть: " + splitReadLine.length);
            }
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

    private int parametersLength(String examCommand) {
        return examCommand.split("[|]").length;
    }
}

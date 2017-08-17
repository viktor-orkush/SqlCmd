package controler;

import controler.command.*;
import model.DatabaseManager;
import view.View;

public class MainControler {
    View view;
    DatabaseManager manager;
    Command[] commands;

    String input;

    public MainControler(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        commands = new Command[] {new Exit(view), new Help(view), new List(view, manager), new Find(view, manager), new Unsupported(view, manager)};
    }

    public void run() {
        connectToDB();
        while (true) {
            try {
                view.write("Введите команду или help для помощи");
                input = view.read();
                for(Command command : commands){
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                }
            } catch (Exception e) {
                view.write(e.getMessage());
            }
        }
    }






    public void connectToDB() {
        view.write("Привет юзер");
        view.write("Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: database|userName|password");
        while (true) {
            try {
                input = view.read();
                String[] splitReadLine = input.split("[|]");
                if (splitReadLine.length != 3) {
                    throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 3, но есть: " + splitReadLine.length);
                }
                String database = splitReadLine[0];
                String user = splitReadLine[1];
                String pass = splitReadLine[2];
                manager.connect(database, user, pass);
                break;
            } catch (Exception e) {
                String massage = " ";
                if (e.getCause() != null) massage += e.getCause().getMessage();
                view.write("Не удача по причине " + e.getMessage() + massage);
                view.write("Повтори попитку");
            }
        }
        view.write("Успех");
    }
}

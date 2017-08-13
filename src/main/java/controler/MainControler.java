package controler;

import model.DatabaseManager;
import view.View;

/**
 * Created by Viktor on 13.08.2017.
 */
public class MainControler {
    View view;
    DatabaseManager manager;

    public MainControler(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public void run() {
        connectToDB();
    }

    public void connectToDB() {
        String database = "";
        String user = "";
        String pass = "";
        String readLine;

        view.write("Привет юзер");
        while (true) {
            try {
                view.write("Введите название базы, логин и пароль в формате база|логин|пароль");
                readLine = view.read();
                String[] splitReadLine = readLine.split("[|]");
                if (splitReadLine.length != 3) {
                    throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 3, но есть: " + splitReadLine.length);
                }
                database = splitReadLine[0];
                user = splitReadLine[1];
                pass = splitReadLine[2];
                manager.connect(database, user, pass);
                break;
            } catch (Exception e) {
                String massage = " ";
                if(e.getCause() != null) massage += e.getCause().getMessage();
                view.write("Не удача по причине " + e.getMessage() + massage);
                view.write("Повтори попитку");
            }
        }
        view.write("Успех");
    }
}

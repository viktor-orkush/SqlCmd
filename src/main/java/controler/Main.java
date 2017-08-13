package controler;

import model.DatabaseManager;
import model.JDBCDatabaseManager;
import view.Console;
import view.View;

//  sqlcmd|admin|admin
public class Main {

    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        MainControler controler = new MainControler(view, manager);
        controler.run();
    }
}

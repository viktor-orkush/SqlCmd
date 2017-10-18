package controller.command;

import view.View;

public class Help implements Command{

    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return "help".equals(command);
    }

    @Override
    public void process(String command) {
        view.write("Существующие команды:");

        view.write("\tconnect|sqlcmd|admin|admin");
        view.write("\t\tдля  подключения к базе");

        view.write("\tlist");
        view.write("\t\tдля получения списка всех таблиц базы, к которой подключились");

        view.write("\tfind|tableName");
        view.write("\t\tдля получения содержимого таблицы 'tableName'");

        view.write("\tinsert|table|column|value|...");
        view.write("\t\tдля  вставки даних в выбраную таблицу");

        view.write("\tclear|table");
        view.write("\t\tочистить данные с таблицы");

        view.write("\thelp");
        view.write("\t\tдля вывода этого списка на экран");

        view.write("\texit");
        view.write("\t\tдля выхода из программы");
    }
}

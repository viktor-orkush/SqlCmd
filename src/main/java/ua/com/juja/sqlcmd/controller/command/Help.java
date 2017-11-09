package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.view.View;

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

        view.write("\tcreateDB|nameDB");
        view.write("\t\tсоздать базу данных");

        view.write("\tcreateTB|nameTable");
        view.write("\t\tсоздать таблицу");

        view.write("\tdeleteDB|nameDB");
        view.write("\t\tудалить базу дынных");

        view.write("\tdeleteTB|nameTable");
        view.write("\t\tудалить таблицу");

        view.write("\thelp");
        view.write("\t\tвывод списка возможних команд");

        view.write("\texit");
        view.write("\t\tвыход из программы");
    }
}

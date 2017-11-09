package ua.com.juja.sqlcmd.controller.command.Exeption;

public class IncorrectInputArgumentException extends Exception {

    public IncorrectInputArgumentException() {
        super("Введено не верное количество аргументов");
    }

    public IncorrectInputArgumentException(String message) {
        super(message);
    }
}

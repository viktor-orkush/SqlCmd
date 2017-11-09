package ua.com.juja.sqlcmd.controller.command.Exeption;

public class ExitException extends Exception {
    public ExitException() {
    }

    public ExitException(String message) {
        super(message);
    }
}

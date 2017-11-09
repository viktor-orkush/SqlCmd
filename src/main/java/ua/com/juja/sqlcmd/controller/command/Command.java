package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.controller.command.Exeption.ExitException;
import ua.com.juja.sqlcmd.controller.command.Exeption.IncorrectInputArgumentException;

public interface Command {
    boolean canProcess(String command);

    void process(String command) throws IncorrectInputArgumentException, ExitException;

    default int parametersLength(String examCommand) {
        return examCommand.split("[|]").length;
    }
}

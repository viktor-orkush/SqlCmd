package controler.command;

import view.View;

public class Exit implements Command {

    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return "exit".equals(command);
    }

    @Override
    public void process(String command) {
        view.write("До скорой встречи!");
        throw new controler.command.ExitException();
    }
}

package controler.command;

/**
 * Created by Viktor on 15.08.2017.
 */
public interface Command {
    boolean canProcess(String command);

    void process(String command);
}

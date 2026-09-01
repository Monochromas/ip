import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Runs the Botavius command-line application.
 */
public class Botavius {
    private static Storage storage;
    private static TaskList tasks;
    private static Ui ui;
    private static Parser parser;

    /**
     * Starts the application and reads commands until the user enters {@code bye}.
     *
     * @param args command-line arguments, which are not used
     */
    public Botavius(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        String taskData = storage.load();
        System.out.println(taskData);
        tasks = new TaskList(taskData);
        parser = new Parser();
    }

    public static void run() {
        String command = "";
        ui.printBanner();
        ui.greet();
        while (!command.equalsIgnoreCase("bye")) {
            try {
                command = ui.getUserInput();
                command = parser.process(command, tasks);
                ui.printFormattedMessage(command);
            } catch (BotaviusException e) {
                System.out.println(e.getMessage());
            } finally {
                ;
            }
        }
        storage.save(tasks.getTaskStrings());
        ui.goodbye();
    }

    public static void main() {
        new Botavius("save.txt").run();
    }
}

package botavius;

import botavius.exception.BotaviusException;
import botavius.parser.Parser;
import botavius.storage.Storage;
import botavius.tasklist.TaskList;
import botavius.ui.Ui;
/** Entry point and coordinator for the Botavius command-line application. */
public class Botavius {
    /** Provides file-based persistence for the current task list. */
    private static Storage storage;
    /** Holds the tasks loaded for the current session. */
    private static TaskList tasks;
    /** Reads input and prints user-facing messages. */
    private static Ui ui;
    /** Converts user commands into task-list operations. */
    private static Parser parser;

    /**
     * Creates an application using the specified save-file path.
     *
     * @param filePath path of the file used to load and save tasks
     */
    public Botavius(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        String taskData = storage.load();
        System.out.println(taskData);
        tasks = new TaskList(taskData);
        parser = new Parser();
    }

    /** Runs the interactive session until the user enters {@code bye}. */
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
            }
        }
        
        storage.save(tasks.getTaskStrings());
        ui.goodbye();
    }

    /** Starts Botavius using {@code save.txt} as its save file.
     *
     * @param args command-line arguments, currently unused
     */
    public static void main(String[] args) {
        new Botavius("save.txt").run();
    }
}

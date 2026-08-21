import java.util.Scanner;

/**
 * Runs the Botavius command-line application.
 */
public class Botavius {
    /** Reads commands entered by the user. */
    private static final Scanner scanner = new Scanner(System.in);
    /** Stores tasks entered during the current session. */
    private static Task[] storedTasks = new Task[100];
    /** Number of tasks currently stored. */
    private static int index = 0;

    /**
     * Starts the application
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = " ____   ___ _____  _  __     _____ _   _ ____  \n"
                + "| __ ) / _ \\_   _|/ \\ \\ \\   / /|_ _| | | / ___|\n"
                + "|  _ \\| | | || | / _ \\ \\ \\ / /  | || | | \\___ \\\n"
                + "| |_) | |_| || |/ ___ \\ \\ v /   | || |_| |___) |\n"
                + "|____/ \\___/ |_/_/   \\_\\ \\_/   |____\\___/|____/\n";
        System.out.println(banner);
        greet();

        String command = scanner.nextLine();
        System.out.println("____________________________________________________________");

        while (!command.equalsIgnoreCase("bye")) {
            command = process(command);
            System.out.println(command);
            System.out.println("____________________________________________________________");
            command = scanner.nextLine();
            System.out.println("____________________________________________________________");
        }

        goodbye();
    }

    /**
     * Prints the application's greeting message.
     */
    public static void greet() {
        String greeting = """        
        Hello! I'm Botavius: \n
        What can I do for you?\n
        ____________________________________________________________
        """;
        System.out.println(greeting);
    }

    /**
     * Prints the application's goodbye message.
     */
    public static void goodbye() {
        String goodbye = """
        Bye. Hope to see you again soon!\n
        ____________________________________________________________
        """;
        System.out.println(goodbye);
    }

    /**
     * Builds a numbered list of all tasks stored during the session.
     *
     * @return the stored tasks as a numbered string, or an empty string
     *         when no tasks have been stored
     */
    public static String listTasks() {
        StringBuilder return_string = new StringBuilder();
        for (int i = 0; i < index; ++i) {
            return_string
                    .append(Integer.toString(i + 1))
                    .append(": ")
                    .append(storedTasks[i].getTaskString())
                    .append("\n");
        }
        return return_string
                .toString()
                .strip();
    }

    public static String markTask(String[] parameters) {
        int taskIndex = Integer.parseInt(parameters[1]) - 1;
        Task t = storedTasks[taskIndex];
        t.setDone(true);
        return "Nice! I've marked this task as done: "
                + t.getTaskString();
    }

    public static String unmarkTask(String[] parameters) {
        int taskIndex = Integer.parseInt(parameters[1]) - 1;
        Task t = storedTasks[taskIndex];
        t.setDone(false);
        return "OK, I've marked this task as not done yet: "
                + t.getTaskString();
    }

    /**
     * Processes a command entered by the user.
     *
     * @param command command text to process
     * @return return the confirmation message
     *         for the user provided command
     */
    public static String process(String command) {
        String[] parameters = command.split("\\s+");
        switch (parameters[0].toLowerCase()) {
            case "list":
                return listTasks();
            case "mark":
                return markTask(parameters);
            case "unmark":
                return unmarkTask(parameters);
            default:
                Task newTask = new Task(parameters[0]);
                storedTasks[index] = newTask;
                index++;
                return "added: " + command;
        }
    }
}

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
                    .append(storedTasks[i].toString())
                    .append("\n");
        }
        return "Here are the tasks in your list:\n"
                + return_string
                .toString()
                .strip();
    }

    /**
     * Marks the task identified by the command parameters as completed.
     *
     * @param parameters command words, with the task number at position 1
     * @return a confirmation message containing the updated task
     */
    public static String markTask(String[] parameters) {
        int taskIndex = Integer.parseInt(parameters[1]) - 1;
        Task t = storedTasks[taskIndex];
        t.setDone(true);
        return "Nice! I've marked this task as done: "
                + t.toString();
    }

    /**
     * Marks the task identified by the command parameters as not completed.
     *
     * @param parameters command words, with the task number at position 1
     * @return a confirmation message containing the updated task
     */
    public static String unmarkTask(String[] parameters) {
        int taskIndex = Integer.parseInt(parameters[1]) - 1;
        Task t = storedTasks[taskIndex];
        t.setDone(false);
        return "OK, I've marked this task as not done yet: "
                + t.toString();
    }

    public static String deadline(Map<String, String> namedParameters) {
        Deadline newTask = new Deadline(
                namedParameters.get("/task").substring(9),
                namedParameters.get("/by"));
        storedTasks[index] = newTask;
        index++;
        return "Got it. I've added this task:\n"
                + newTask.toString()
                + "\nNow you have "
                + index
                + " tasks in the list.";
    }

    public static String event(Map<String, String> namedParameters) {
        Event newTask = new Event(
                namedParameters.get("/task").substring(6),
                namedParameters.get("/from"),
                namedParameters.get("/to"));
        storedTasks[index] = newTask;
        index++;
        return "Got it. I've added this task:\n"
                + newTask.toString()
                + "\nNow you have "
                + index
                + " tasks in the list.";
    }

    public static String todo(Map<String, String> namedParameters) {
        ToDo newTask = new ToDo(
                namedParameters.get("/task").substring(5));
        storedTasks[index] = newTask;
        index++;
        return "Got it. I've added this task:\n"
                + newTask.toString()
                + "\nNow you have "
                + index
                + " tasks in the list.";
    }

    public static Map<String, String> getNamedParameters(String command) {
        Map<String, String> parametersByName = new HashMap<>();
        Pattern pattern = Pattern.compile("(/[a-zA-Z0-9]+)");
        Matcher matcher = pattern.matcher(command);

        String currentKey = "/task";
        int lastMatchEnd = 0;

        while (matcher.find()) {
            // If not null, have valid key to pair
            if (currentKey != null) {
                String value = command.substring(lastMatchEnd, matcher.start()).trim();
                parametersByName.put(currentKey, value);
            }

            currentKey = matcher.group(0); // Set key to "\XXX"
            lastMatchEnd = matcher.end();
        }

        if (currentKey != null) {
            String value = command.substring(lastMatchEnd).trim();
            parametersByName.put(currentKey, value);
        }
        //only for debugging
        //parametersByName.forEach((key, value) -> System.out.println(key + " => " + value));
        return parametersByName;
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
        Map<String, String> namedParameters = getNamedParameters(command);
        switch (parameters[0].toLowerCase()) {
            case "list":
                return listTasks();
            case "mark":
                return markTask(parameters);
            case "unmark":
                return unmarkTask(parameters);
            case "deadline":
                return deadline(namedParameters);
            case "event":
                return event(namedParameters);
            case "todo":
                return todo(namedParameters);
            default:
                return "bad command issued.";
        }
    }
}

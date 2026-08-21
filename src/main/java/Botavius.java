import java.util.Locale;
import java.util.Scanner;

/**
 * Runs the Botavius command-line application.
 */
public class Botavius {
    /** Reads commands entered by the user. */
    private static final Scanner scanner = new Scanner(System.in);
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
     * Processes a command entered by the user.
     *
     * @param command command text to process
     * @return return the confirmation message
     *         for the user provided command
     */
    public static String process(String command) {
        return command;
    }
}

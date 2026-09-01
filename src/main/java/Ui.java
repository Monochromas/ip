import java.util.Scanner;

public class Ui {

    private static final String BANNER = " ____   ___ _____  _  __     _____ _   _ ____  \n"
            + "| __ ) / _ \\_   _|/ \\ \\ \\   / /|_ _| | | / ___|\n"
            + "|  _ \\| | | || | / _ \\ \\ \\ / /  | || | | \\___ \\\n"
            + "| |_) | |_| || |/ ___ \\ \\ v /   | || |_| |___) |\n"
            + "|____/ \\___/ |_/_/   \\_\\ \\_/   |____\\___/|____/\n";

    /** Reads commands entered by the user. */
    private static Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public static String getUserInput() {
        return scanner.nextLine();
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
    public static void printBanner() {
        System.out.print(BANNER);
    }
    public static void printFormattedMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }
}

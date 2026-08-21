import java.util.Locale;
import java.util.Scanner;

/**
 * Runs the Botavius command-line application.
 */
public class Botavius {
    /**
     * Starts the application, displays its banner, and shows the greeting
     * and goodbye messages.
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
    }
}

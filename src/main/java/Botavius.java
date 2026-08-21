import java.util.Scanner;

public class Botavius {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String banner = " ____   ___ _____  _  __     _____ _   _ ____  \n"
                + "| __ ) / _ \\_   _|/ \\ \\ \\   / /|_ _| | | / ___|\n"
                + "|  _ \\| | | || | / _ \\ \\ \\ / /  | || | | \\___ \\\n"
                + "| |_) | |_| || |/ ___ \\ \\ v /   | || |_| |___) |\n"
                + "|____/ \\___/ |_/_/   \\_\\ \\_/   |____\\___/|____/\n";
        System.out.println(banner);
        greet();
        String command = scanner.nextLine();
        System.out.println("____________________________________________________________");

        while (!command.toLowerCase().equals("bye")) {
            process(command);
            System.out.println(command);
            System.out.println("____________________________________________________________");
            command = scanner.nextLine();
            System.out.println("____________________________________________________________");
        }

        goodbye();

    }

    public static String process(String command) {
        return command;
    }



    public static void greet() {
        String greeting = """        
        Hello! I'm Botavius: \n
        What can I do for you?\n
        ____________________________________________________________
        """;
        System.out.println(greeting);
    }

    public static void goodbye() {
        String goodbye = """
        Bye. Hope to see you again soon!\n
        ____________________________________________________________
        """;
        System.out.println(goodbye);
    }
}

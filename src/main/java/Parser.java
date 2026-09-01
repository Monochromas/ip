import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public Parser() {
        ;
    }
    /**
     * Extracts slash-prefixed named parameters from a command string.
     *
     * @param command command containing an optional task and named values
     * @return map of parameter names to their trimmed values
     */
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
     * @return the confirmation message for the user-provided command
     * @throws BotaviusException if the command is not recognized
     */
    public static String[] getUnnamedParameters(String command) {
        return command.split("\\s+");
    }
    public static String process(String command, TaskList taskList) {
        Map<String, String> namedParameters = getNamedParameters(command);
        String[] parameters = getUnnamedParameters(command);
        switch (parameters[0].toLowerCase()) {
            case "list":
                return taskList.listTasks();
            case "mark":
                return taskList.markTask(parameters);
            case "unmark":
                return taskList.unmarkTask(parameters);
            case "deadline":
                return taskList.deadline(namedParameters);
            case "event":
                return taskList.event(namedParameters);
            case "todo":
                return taskList.todo(namedParameters);
            case "delete":
                return taskList.delete(parameters);
            default:
                throw new BotaviusException("bad command issued.");
        }
    }
}

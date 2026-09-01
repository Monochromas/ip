import java.util.ArrayList;
import java.util.Map;

/** Stores, displays, and updates the tasks in a Botavius session. */
public class TaskList {
    /** Stores tasks entered during the current session, in insertion order. */
    private static ArrayList<Task> storedTasks = new ArrayList<Task>();

    /** Creates a task list and reconstructs tasks from serialized data. */
    public TaskList(String taskData) {
        storedTasks = new ArrayList<Task>();
        System.out.println("tasks: "+taskData); //debug
        String[] taskStrings = taskData.split("\n");
        for (int i = 0; i < taskStrings.length; ++i) {
            System.out.println("task: "+taskStrings[i]); //debug
            String [] data;
            boolean done = taskStrings[i].charAt(4) == 'X';
            Task loadedTask = null;
            switch (taskStrings[i].charAt(1)) {
                case 'T':
                    loadedTask = new ToDo(taskStrings[i].substring(7));
                    break;
                case 'E':
                    data = taskStrings[i].split("from:");
                    String description = data[0].substring(7).strip();
                    data = data[1].split("to:");
                    loadedTask = new Event(description, data[0].strip(), data[1].strip());
                    break;
                case 'D':
                    data = taskStrings[i].split("by:");
                    loadedTask = new Deadline(data[0].substring(7).strip(), data[1].strip());
                    break;
            }
            storedTasks.add(loadedTask);
            if (done) {
                markTask(new String[]{"",Integer.toString(storedTasks.size()-1)});
            }
        }
    }

    /** @return all tasks serialized for saving, one task per line */
    public static String getTaskStrings() {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < storedTasks.size(); ++i) {
            returnString
                    .append(storedTasks.get(i).toStorageString())
                    .append("\n");
        }
        return returnString.toString();
    }
    /** @return a numbered display of the tasks currently stored */
    public static String listTasks() {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < storedTasks.size(); ++i) {
            returnString
                    .append(Integer.toString(i + 1))
                    .append(": ")
                    .append(storedTasks.get(i).toString())
                    .append("\n");
        }
        return "Here are the tasks in your list:\n"
                + returnString
                .toString()
                .strip();
    }

    /**
     * Marks the task identified by the command parameters as completed.
     *
     * @param parameters command words, with the task number at position 1
     * @return a confirmation message containing the updated task
     * @throws BotaviusException if the task number does not identify a task
     */
    public static String markTask(String[] parameters) {
        int taskIndex = Integer.parseInt(parameters[1]);
        if (taskIndex >= storedTasks.size() || taskIndex < 0) {
            throw new BotaviusException("Task index doesn't exist");
        }
        Task t = storedTasks.get(taskIndex);
        t.setDone(true);
        return "Nice! I've marked this task as done: "
                + t.toString();
    }

    /**
     * Marks the task identified by the command parameters as not completed.
     *
     * @param parameters command words, with the task number at position 1
     * @return a confirmation message containing the updated task
     * @throws BotaviusException if the task number does not identify a task
     */
    public static String unmarkTask(String[] parameters) {
        int taskIndex = Integer.parseInt(parameters[1]) - 1;
        if (taskIndex >= storedTasks.size() || taskIndex < 0) {
            throw new BotaviusException("Task index doesn't exist");
        }
        Task t = storedTasks.get(taskIndex);
        t.setDone(false);
        return "OK, I've marked this task as not done yet: "
                + t.toString();
    }

    /**
     * Creates and stores a deadline task from parsed command parameters.
     *
     * @param namedParameters command parameters containing {@code /task} and
     *                        {@code /by} values
     * @return a confirmation message describing the new task
     * @throws BotaviusException if required task information is missing
     */
    public static String deadline(Map<String, String> namedParameters) {
        Deadline newTask = new Deadline(
                namedParameters.get("/task").substring(8),
                namedParameters.get("/by"));
        storedTasks.add(newTask);
        return "Got it. I've added this task:\n"
                + newTask.toString()
                + "\nNow you have "
                + storedTasks.size()
                + " tasks in the list.";
    }

    /**
     * Creates and stores an event task from parsed command parameters.
     *
     * @param namedParameters command parameters containing {@code /task},
     *                        {@code /from}, and {@code /to} values
     * @return a confirmation message describing the new task
     * @throws BotaviusException if required task information is missing
     */
    public static String event(Map<String, String> namedParameters) {
        Event newTask = new Event(
                namedParameters.get("/task").substring(5),
                namedParameters.get("/from"),
                namedParameters.get("/to"));
        storedTasks.add(newTask);
        return "Got it. I've added this task:\n"
                + newTask.toString()
                + "\nNow you have "
                + storedTasks.size()
                + " tasks in the list.";
    }

    /**
     * Creates and stores a to-do task from parsed command parameters.
     *
     * @param namedParameters command parameters containing a {@code /task}
     *                        value
     * @return a confirmation message describing the new task
     * @throws BotaviusException if the task description is missing
     */
    public static String todo(Map<String, String> namedParameters) {
        ToDo newTask = new ToDo(
                namedParameters.get("/task").substring(4));
        storedTasks.add(newTask);
        return "Got it. I've added this task:\n"
                + newTask.toString()
                + "\nNow you have "
                + storedTasks.size()
                + " tasks in the list.";
    }

    /**
     * Deletes the task identified by the command parameters.
     *
     * @param parameters command words, with the task number at position 1
     * @return a confirmation message containing the deleted task
     * @throws BotaviusException if the task number does not identify a task
     */
    public static String delete(String[] parameters) {
        int taskIndex = Integer.parseInt(parameters[1]) - 1;
        if (taskIndex >= storedTasks.size() || taskIndex < 0) {
            throw new BotaviusException("Task index doesn't exist");
        }
        String returnstring =
                "Noted. I've removed this task: "
                        + storedTasks.get(taskIndex).toString()
                        + "\nNow you have "
                        + storedTasks.size()
                        + " tasks in the list.";
        storedTasks.remove(taskIndex);
        return returnstring;
    }
}

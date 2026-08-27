/** Represents a task with a due date or other deadline description. */
public class Deadline extends Task {

    /** The deadline description. */
    private String by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description task description
     * @param by deadline text
     * @throws BotaviusException if the deadline is missing
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        if (by == null) {
            throw new BotaviusException("deadline not provided.");
        }
    }

    /**
     * Formats the deadline task for display.
     *
     * @return task text with its deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

/** Represents a task with a due date or deadline. */
public class Deadline extends Task {

    /** The deadline description. */
    protected String by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description task description
     * @param by deadline text
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
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

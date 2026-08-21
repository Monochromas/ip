/** Represents a standard to-do task. */
public class ToDo extends Task {

    /**
     * Creates an incomplete to-do task.
     *
     * @param description task description
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Formats the to-do task for display.
     *
     * @return task text prefixed with the to-do marker
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

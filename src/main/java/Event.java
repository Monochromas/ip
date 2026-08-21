/** Represents a task scheduled between a start and end time. */
public class Event extends Task {

    /** Event start time. */
    protected String from;
    /** Event end time. */
    protected String to;

    /**
     * Creates an incomplete event task.
     *
     * @param description task description
     * @param from event start time
     * @param to event end time
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Formats the event task for display.
     *
     * @return task text with its start and end times
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

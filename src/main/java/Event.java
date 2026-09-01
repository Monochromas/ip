/** A task scheduled between textual start and end times. */
public class Event extends Task {

    /** Text describing when the event starts. */
    private String from;
    /** Text describing when the event ends. */
    private String to;

    /**
     * Creates an incomplete event task.
     *
     * @param description task description
     * @param from event start time
     * @param to event end time
     * @throws BotaviusException if either time is missing
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        if (from == null) {
            throw new BotaviusException("starting time not provided.");
        }
        if (to == null) {
            throw new BotaviusException("ending time not provided.");
        }
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

    /**
     * Returns the event in the format used when persisting it.
     *
     * @return the event's storage representation
     */
    public String toStorageString() {
        return "[E]" + super.toStorageString() + " from: "
                + from.format(DATE_TIME_FORMAT) + " to: "
                + to.format(DATE_TIME_FORMAT);
    }
}

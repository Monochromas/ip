package botavius.tasklist;

import java.time.LocalDateTime;

/** A task scheduled between textual start and end times. */
public class Event extends Task {

    /** Text describing when the event starts. */
    private LocalDateTime from;
    /** Text describing when the event ends. */
    private LocalDateTime to;

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
        this.from = TaskDateTime.parse(from, "event start time");
        this.to = TaskDateTime.parse(to, "event end time");
    }

    /**
     * Formats the event task for display.
     *
     * @return task text with its start and end times
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + TaskDateTime.toDisplayString(from) + " to: "
                + TaskDateTime.toDisplayString(to) + ")";
    }

    /**
     * Returns the event in the format used when persisting it.
     *
     * @return the event's storage representation
     */
    public String toStorageString() {
        return "[E]" + super.toStorageString() + " from: "
                + TaskDateTime.toStorageString(from) + " to: "
                + TaskDateTime.toStorageString(to);
    }
}

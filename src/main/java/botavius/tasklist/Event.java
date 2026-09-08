package botavius.tasklist;

import botavius.exception.BotaviusException;

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
     * @param from event start date and time in {@code dd-MM-yyyy HH:mm} format
     * @param to event end date and time in {@code dd-MM-yyyy HH:mm} format
     * @throws BotaviusException if either date-time is missing or invalid
     */
    public Event(String description, String from, String to) {
        super(description);
        if (from == null) {
            throw new BotaviusException("starting time not provided.");
        }
        if (to == null) {
            throw new BotaviusException("ending time not provided.");
        }
        try {
            this.from = LocalDateTime.parse(from, DATE_TIME_FORMAT);
            this.to = LocalDateTime.parse(to, DATE_TIME_FORMAT);
        } catch (DateTimeParseException exception) {
            throw new BotaviusException(
                    "event times must be in dd-MM-yyyy HH:mm format.");
        }
    }

    /**
     * Formats the event task for display.
     *
     * @return task text with its start and end times
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + from.format(DISPLAY_FORMAT) + " to: "
                + to.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns the event in the format used when persisting it.
     *
     * @return the event's storage representation
     */
    public String toStorageString() {
        return "[E]" + super.toStorageString() + " from: "
                + from + " to: " + to;
    }
}

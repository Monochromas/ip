package botavius.tasklist;

import botavius.exception.BotaviusException;

/** A task that includes a textual deadline. */
public class Deadline extends Task {

    /** Text describing when the task is due. */
    private String by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description task description
     * @param by deadline text, such as {@code Sunday}
     * @throws BotaviusException if the deadline is missing
     */
    public Deadline(String description, String by) {
        super(description);
        if (by == null) {
            throw new BotaviusException("deadline not provided.");
        }
        try {
            this.by = LocalDateTime.parse(by, DATE_FORMAT);
        } catch (DateTimeParseException exception) {
            throw new BotaviusException("deadline must be in dd-MM-yyyy HH:mm format.");
        }
    }

    /**
     * Formats the deadline task for display.
     *
     * @return task text with its deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns the task in the format used when persisting it.
     *
     * @return the deadline task's storage representation
     */
    public String toStorageString() {
        return "[D]" + super.toStorageString() + " by: " + by;
    }
}

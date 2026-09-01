package botavius.model;

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

    /**
     * Returns the task in the format used when persisting it.
     *
     * @return the deadline task's storage representation
     */
    public String toStorageString() {
        return "[E]" + super.toStorageString() + " by: "
                + by.format(DATE_FORMAT);
    }
}

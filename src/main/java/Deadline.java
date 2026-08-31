import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/** Represents a task with a due date and time. */
public class Deadline extends Task {

    /** The deadline date and time. */
    protected LocalDateTime by;

    /** The format required for deadline dates entered by the user. */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    /** The format used when displaying a deadline. */
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma", Locale.ENGLISH);

    /**
     * Creates an incomplete deadline task.
     *
     * @param description task description
     * @param by deadline date and time in {@code dd-MM-yyyy HH:mm} format
     * @throws BotaviusException if the deadline is missing or invalid
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
}

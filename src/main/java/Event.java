import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/** Represents a task scheduled between a start and end time. */
public class Event extends Task {

    /** Event start time. */
    protected LocalDateTime from;
    /** Event end time. */
    protected LocalDateTime to;

    /** The format required for event date-times entered by the user. */
    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    /** The format used when displaying event date-times. */
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma", Locale.ENGLISH);

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
}

package botavius.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import botavius.exception.BotaviusException;
import org.junit.jupiter.api.Test;

/** Tests validation and formatting of deadline and event date/time values. */
class DateTimeTaskTest {
    /** Verifies that a missing deadline is reported clearly. */
    @Test
    void deadline_missingDate_throwsError() {
        assertEquals("deadline not provided.", assertThrows(BotaviusException.class,
                () -> new Deadline("submit report", "")).getMessage());
    }

    /** Verifies that a date without a time defaults to midnight. */
    @Test
    void deadline_dateOnly_defaultsToMidnight() {
        Deadline deadline = new Deadline("submit report", "09-09-2026");

        assertEquals("[D][ ] submit report (by: 09-Sep-26 12:00 am)", deadline.toString());
        assertEquals("[D][ ] submit report by: 09-09-2026 00:00",
                deadline.toStorageString());
    }

    /** Verifies that malformed dates are rejected. */
    @Test
    void event_invalidDate_throwsError() {
        assertThrows(BotaviusException.class,
                () -> new Event("meeting", "31-02-2026", "10-09-2026 14:30"));
    }

    /** Verifies display and storage formatting for an event. */
    @Test
    void event_validDateTime_formatsForDisplayAndStorage() {
        Event event = new Event("meeting", "10-09-2026 14:30", "10-09-2026 16:00");

        assertEquals("[E][ ] meeting (from: 10-Sep-26 02:30 pm to: 10-Sep-26 04:00 pm)",
                event.toString());
        assertEquals("[E][ ] meeting from: 10-09-2026 14:30 to: 10-09-2026 16:00",
                event.toStorageString());
    }

    /** Verifies that an event cannot end before it starts. */
    @Test
    void event_startAfterEnd_throwsError() {
        assertEquals("event start time must not be after event end time.",
                assertThrows(BotaviusException.class,
                        () -> new Event("meeting", "11-09-2026", "10-09-2026")).getMessage());
    }

    /** Verifies that an event may start and end at the same time. */
    @Test
    void event_startEqualsEnd_isAccepted() {
        Event event = new Event("meeting", "10-09-2026 14:30", "10-09-2026 14:30");

        assertEquals("[E][ ] meeting (from: 10-Sep-26 02:30 pm to: 10-Sep-26 02:30 pm)",
                event.toString());
    }

    /** Verifies display and storage formatting for a do-after task. */
    @Test
    void doAfter_validDateTime_formatsForDisplayAndStorage() {
        DoAfter doAfter = new DoAfter("call client", "10-09-2026 14:30");

        assertEquals("[A][ ] call client (after: 10-Sep-26 02:30 pm)", doAfter.toString());
        assertEquals("[A][ ] call client after: 10-09-2026 14:30",
                doAfter.toStorageString());
    }

    /** Verifies that a missing do-after time is reported clearly. */
    @Test
    void doAfter_missingTime_throwsError() {
        assertEquals("do-after time not provided.", assertThrows(BotaviusException.class,
                () -> new DoAfter("call client", "")).getMessage());
    }
}

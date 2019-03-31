package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's startTime in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should only contain numbers & '.', 2 digits'.'2digits, ie: 13.00\n"
            + "Hours should only contain numbers range from 00 to 23\n"
            + "Minutes should only contain numbers range from 00 to 59";
    public static final String VALIDATION_REGEX = "\\d{2}" + "\\." + "\\d{2}";

    public final String value;

    /**
     * Constructs a {@code StartTime}.
     *
     * @param time A valid time.
     */
    public EndTime(String time) {
        requireNonNull(time);
        checkArgument(isValidEndTime(time), MESSAGE_CONSTRAINTS);
        value = time;
    }

    /**
     * Returns true if a given string is a valid start time.
     */
    public static boolean isValidEndTime(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            String[] splitTime = test.split("\\.");
            int hour = Integer.parseInt(splitTime[0]);
            int minute = Integer.parseInt(splitTime[1]);
            if (hour < 0 || hour > 23) { return false; }
            if (minute < 0 || minute > 59) { return false; }
            return true;
        }
        return false;
    }

    /**
     * Get double version of time.
     */
    public double getTimeDouble(String time) {
        double t;
        double scale = Math.pow(10, 2);
        t = Double.parseDouble(time);

        return Math.round(t * scale) / scale;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

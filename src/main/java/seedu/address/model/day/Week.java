package seedu.address.model.day;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Day's week in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class Week {

    private String start;
    private String end;
    public static final String MESSAGE_START_CONSTRAINTS =
            "Start should only contain numbers & -, 2 digits-2 digits-2digits, ie: 12-03-19";
    public static final String MESSAGE_END_CONSTRAINTS =
            "End should only contain numbers & -, 2 digits-2 digits-2digits, ie: 12-03-19";
    public static final String VALIDATION_REGEX = "\\d{2}" + "-" + "\\d{2}" + "-" + "\\d{2}";

    /**
     * Constructs a {@code Other}.
     *
     * @param start A valid start.
     * @param end A valid end.
     */
    public Week(String start, String end) {
        requireNonNull(start);
        requireNonNull(end);
        checkArgument(isValidStart(start), MESSAGE_START_CONSTRAINTS);
        checkArgument(isValidEnd(end), MESSAGE_END_CONSTRAINTS);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns true if a given string is a valid start time.
     */
    public static boolean isValidStart(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isValidEnd(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && start.equals(((Week) other).start) // state check
                && end.equals(((Week) other).end));
    }

    @Override
    public int hashCode() {
        return (start + end).hashCode();
    }
}

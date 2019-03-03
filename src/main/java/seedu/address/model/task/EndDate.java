package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class EndDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should only contain numbers, and it should be 6 digits long";
    public static final String VALIDATION_REGEX = "\\d{6}";
    public final String value;

    /**
     * Constructs a {@code StartDate}.
     *
     * @param date A valid date.
     */
    public EndDate(String date) {
        requireNonNull(date);
        checkArgument(isValidEndDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidEndDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                && value.equals(((EndDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

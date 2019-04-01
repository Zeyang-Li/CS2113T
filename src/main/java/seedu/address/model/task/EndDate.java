package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's endDate in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {

    public static final String MESSAGE_CONSTRAINTS =
            "End date should only contain numbers & -, 2 digits-2 digits-2digits, ie: 12-03-19\n"
            + "Day should only contain numbers range from 01 to 31\n"
            + "Month should only contain numbers range from 01 to 12";
    public static final String VALIDATION_REGEX = "\\d{2}" + "-" + "\\d{2}" + "-" + "\\d{2}";
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
        if (test.matches(VALIDATION_REGEX)) {
            String[] spiltDate = test.split("-");
            int day = Integer.parseInt(spiltDate[0]);
            int month = Integer.parseInt(spiltDate[1]);
            if (day < 1 || day > 31) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }
            return true;
        }
        return false;
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

package seedu.address.model.day;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Day's cca in the task book.
 */
public class Cca extends Category {

    private double time;
    //public static final String MESSAGE_TIME_CONSTRAINTS =
            //"Time should only contain numbers & '.', 2 digits'.'2digits, ie: 13.00";
    //public static final String VALIDATION_REGEX = "\\d{2}" + "." + "\\d{2}";

    /**
     * Constructs a {@code Cca}.
     *
     * @param time A valid time.
     */
    public Cca(Double time) {
        //requireNonNull(time);
        //checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid start time.
     */
    //public static boolean isValidTime(String test) {
        //return test.matches(VALIDATION_REGEX);
    //}

    public void addTime(double result) {
        time += result;
    }

    public void removeTime(double result) {
        time -= result;
    }

    public double getTime() {
        return time;
    }

    public String getTimeString() {
        return String.valueOf(time);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cca // instanceof handles nulls
                && time == (((Cca) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

}

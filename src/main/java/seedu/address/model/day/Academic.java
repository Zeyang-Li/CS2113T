package seedu.address.model.day;

import java.util.Objects;

/**
 * Represents a Day's academic in the task book.
 */
public class Academic extends Category {

    public static final String MESSAGE_CONSTRAINTS =
            "Academic time should only contain numbers & '.', ie: 1.0";
    public static final String VALIDATION_REGEX = "\\d*" + "\\." + "\\d*";
    private String value;

    /**
     * Constructs a {@code Academic}.
     *
     * @param time A valid time.
     */
    public Academic(String time) {
        value = time;
    }

    /**
     * Returns true if a given string is a valid start time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Adding time after update
     * @param result
     * @return value
     */
    public String addTime(double result) {
        double t = Double.parseDouble(value);
        t += result;
        value = String.valueOf(t);
        return value;
    }

    /**
     * Deducting time after update
     * @param result
     * @return value
     */
    public String removeTime(double result) {
        double t = Double.parseDouble(value);
        t -= result;
        value = String.valueOf(t);
        return value;
    }

    public String getTime() {
        return value;
    }

    public double getTimeDouble() {
        double t;
        double scale = Math.pow(10, 2);
        t = Double.parseDouble(value);

        return Math.round(t * scale) / scale;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Academic // instanceof handles nulls
                && value.equals(((Academic) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}

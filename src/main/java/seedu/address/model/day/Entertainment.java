package seedu.address.model.day;

import java.util.Objects;

/**
 * Represents a Day's entertainment in the task book.
 */
public class Entertainment extends Category {

    private double time;

    /**
     * Constructs a {@code Entertainment}.
     *
     * @param time A valid time.
     */
    public Entertainment(double time) {
        this.time = time;
    }

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
                || (other instanceof Entertainment // instanceof handles nulls
                && time == (((Entertainment) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

}

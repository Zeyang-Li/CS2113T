package seedu.address.model.day;

import java.util.Objects;

/**
 * Represents a Day's cca in the task book.
 */
public class Cca extends Category {

    private double time;

    /**
     * Constructs a {@code Cca}.
     *
     * @param time A valid time.
     */
    public Cca(Double time) {
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
                || (other instanceof Cca // instanceof handles nulls
                && time == (((Cca) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

}

package seedu.address.model.day;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import seedu.address.model.task.Categories;
import seedu.address.model.task.Task;

/**
 * Represents a day in the task book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Day {

    public final double dafault = 0;

    // Identity fields
    private final Date date;
    private Academic academic;
    private Entertainment entertainment;
    private Cca cca;
    private Errand errand;
    private Other other;

    /**
     * Every field must be present and not null.
     */
    public Day(Date date) {
        requireAllNonNull(date);
        this.date = date;
        this.academic = new Academic(dafault);
        this.entertainment = new Entertainment(dafault);
        this.cca = new Cca(dafault);
        this.errand = new Errand(dafault);
        this.other = new Other(dafault);
    }

    public Date getDate() {
        return date;
    }

    public Academic getAcademic() {
        return academic;
    }

    public Entertainment getEntertainment() {
        return entertainment;
    }

    public Cca getCca() {
        return cca;
    }

    public Errand getErrand() {
        return errand;
    }

    public Other getOther() {
        return other;
    }

    /**
     * To add a time into the category for each task
     */
    public void addCategory(Task task) {

        String timeStart = task.getEndTime().value;
        String timeEnd = task.getStartTime().value;
        String category = task.getCategories().value;
        switch(category) {
        case Categories.CAT_ACADEMIC:
            academic.addTime(calculateTime(timeStart, timeEnd));
            break;
        case Categories.CAT_CCA:
            cca.addTime(calculateTime(timeStart, timeEnd));
            break;
        case Categories.CAT_ENTERTAINMENT:
            entertainment.addTime(calculateTime(timeStart, timeEnd));
            break;
        case Categories.CAT_ERRAND:
            errand.addTime(calculateTime(timeStart, timeEnd));
            break;
        case Categories.CAT_OTHER:
            other.addTime(calculateTime(timeStart, timeEnd));
            break;
        default:
            break;
        }
    }

    /**
     * To remove a time into the category for each task
     */
    public void removeCategory(Task task) {

        String timeStart = task.getEndTime().value;
        String timeEnd = task.getStartTime().value;
        String category = task.getCategories().value;
        switch(category) {
        case Categories.CAT_ACADEMIC:
            academic.removeTime(calculateTime(timeStart, timeEnd));
            break;
        case Categories.CAT_CCA:
            cca.removeTime(calculateTime(timeStart, timeEnd));
            break;
        case Categories.CAT_ENTERTAINMENT:
            entertainment.removeTime(calculateTime(timeStart, timeEnd));
            break;
        case Categories.CAT_ERRAND:
            errand.removeTime(calculateTime(timeStart, timeEnd));
            break;
        case Categories.CAT_OTHER:
            other.removeTime(calculateTime(timeStart, timeEnd));
            break;
        default:
            break;
        }
    }

    /**
     * To edit a time into the category for each task
     */
    public void editCategory(Task task, Task editedTask) {

        String taskCategory = task.getCategories().value;
        String editedTaskCategory = editedTask.getCategories().value;

        if (taskCategory == editedTaskCategory) {
            addCategory(editedTask);
        }
        else if (taskCategory != editedTaskCategory) {
            removeCategory(task);
            addCategory(editedTask);
        }
    }

    /**
     * To calculate time into the category for each task
     */
    public double calculateTime(String start, String end) {

        Double result = Double.valueOf(start) - Double.valueOf(end);
        double scale = Math.pow(10, 2);

        return Math.round(result * scale) / scale;
    }

    /**
     * Returns true if both days of the same topic have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two days.
     */
    public boolean isSameDay(Day otherDay) {
        if (otherDay == this) {
            return true;
        }

        return otherDay != null
                && otherDay.getDate().equals(getDate());
    }

    /**
     * Returns true if both days have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return otherDay.getDate().equals(getDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, academic, entertainment, cca, errand, other);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDate())
                .append(" AcademicTime: ")
                .append(getAcademic().getTimeString())
                .append(" EntertainmentTime: ")
                .append(getEntertainment().getTimeString())
                .append(" CcaTime: ")
                .append(getCca().getTimeString())
                .append(" ErrandTime: ")
                .append(getErrand().getTimeString())
                .append(" OtherTime: ")
                .append(getOther().getTimeString());
        return builder.toString();
    }
}

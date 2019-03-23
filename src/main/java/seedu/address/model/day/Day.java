package seedu.address.model.day;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a day in the task book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Day {

    // Identity fields
    private final Date date;
    private final Week week;
    private final Academic academic;
    private final Entertainment entertainment;
    private final Cca cca;
    private final Errand errand;
    private final Other other;

    /**
     * Every field must be present and not null.
     */
    public Day(Date date, Week week, Academic academic, Entertainment entertainment, Cca cca, Errand errand,
               Other other) {
        requireAllNonNull(date, week, academic, entertainment, cca, errand, other);
        this.date = date;
        this.week = week;
        this.academic = academic;
        this.entertainment = entertainment;
        this.cca = cca;
        this.errand = errand;
        this.other = other;
    }

    public Date getDate() { return date; }

    public Week getWeek() { return week; }

    public Academic getAcademic() { return academic; }

    public Entertainment getEntertainment() { return entertainment; }

    public Cca getCca() { return cca; }

    public Errand getErrand() { return errand; }

    public Other getOther() { return other; }

    /**
     * Returns true if both days of the same topic have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two days.
     */
    public boolean isSameDay(Day otherDay) {
        if (otherDay == this) {
            return true;
        }

        return otherDay != null
                && otherDay.getDate().equals(getDate())
                && otherDay.getWeek().equals(getWeek())
                && otherDay.getAcademic().equals(getAcademic())
                && otherDay.getEntertainment().equals(getEntertainment())
                && otherDay.getCca().equals(getCca())
                && otherDay.getErrand().equals(getErrand())
                && otherDay.getOther().equals(getOther());
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
        return otherDay.getDate().equals(getDate())
                && otherDay.getWeek().equals(getWeek())
                && otherDay.getAcademic().equals(getAcademic())
                && otherDay.getEntertainment().equals(getEntertainment())
                && otherDay.getCca().equals(getCca())
                && otherDay.getErrand().equals(getErrand())
                && otherDay.getOther().equals(getOther());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, week, academic, entertainment, cca, errand, other);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDate())
                .append(" WeekStart: " + getWeek().getStart() + " WeekEnd: " + getWeek().getEnd())
                .append(" AcademicTime: " + getAcademic().getTime() + " AcademicLimit: " + getAcademic().getLimit())
                .append(" EntertainmentTime: " + getEntertainment().getTime() + " EntertainmentLimit: "
                        + getEntertainment().getLimit())
                .append(" CcaTime: " + getCca().getTime() + "CcaLimit: " + getCca().getLimit())
                .append(" ErrandTime: " + getErrand().getTime() + " ErrandLimit: " + getErrand().getLimit())
                .append(" OtherTime: " + getOther().getTime() + " OtherLimit: " + getOther().getLimit());
        return builder.toString();
    }
}

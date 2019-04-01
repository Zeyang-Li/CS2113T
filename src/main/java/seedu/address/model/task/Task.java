package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the task book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final StartDate startDate;
    private final EndDate endDate;
    private final StartTime startTime;
    private final EndTime endTime;

    // Data fields
    private final Description description;
    private final Categories categories;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, StartDate startDate, StartTime startTime, EndDate endDate, EndTime endTime,
        Description description, Categories categories, Set<Tag> tags) {
        requireAllNonNull(name, startDate, endDate, startTime, endTime, description, categories, tags);

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.categories = categories;
        this.tags.addAll(tags);

    }

    public Name getName() {
        return name;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public Description getDescription() {
        return description;
    }

    public Categories getCategories() {
        return categories;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both tasks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getName().equals(getName());
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, startTime, endDate, endTime, description, categories, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" StartDate: ")
                .append(getStartDate())
                .append(" StartTime: ")
                .append(getStartTime())
                .append(" EndDate: ")
                .append(getEndDate())
                .append(" EndTime: ")
                .append(getEndTime())
                .append(" Description: ")
                .append(getDescription())
                .append(" Category: ")
                .append(getCategories().value)
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

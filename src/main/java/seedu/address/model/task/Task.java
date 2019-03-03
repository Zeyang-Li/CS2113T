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
    private final Topic topic;
    private final StartDate startDate;
    private final EndDate endDate;
    private final StartTime startTime;
    private final EndTime endTime;
//    private final Duration duration;


    // Data fields
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(Topic topic, StartDate startDate, EndDate endDate, StartTime startTime, EndTime endTime, Description description, Set<Tag> tags) {
        requireAllNonNull(topic, startDate, endDate, startTime, endTime, description, tags);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.tags.addAll(tags);
    }

    public Topic getTopic() {
        return topic;
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both tasks of the same topic have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getTopic().equals(getTopic())
                && otherTask.getStartDate().equals(getStartDate())
                && otherTask.getEndDate().equals(getEndDate())
                && otherTask.getStartTime().equals(getStartTime())
                && otherTask.getEndTime().equals(getEndTime());
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
        return otherTask.getTopic().equals(getTopic())
                && otherTask.getStartDate().equals(getStartDate())
                && otherTask.getEndDate().equals(getEndDate())
                && otherTask.getStartTime().equals(getStartTime())
                && otherTask.getEndTime().equals(getEndTime())
                && otherTask.getDescription().equals(getDescription())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(topic, startDate, endDate, startTime, endTime, description, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTopic())
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
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

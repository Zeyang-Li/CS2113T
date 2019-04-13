package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.Categories;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Revise CS2113T";
    public static final String DEFAULT_STARTDATE = "14-04-19";
    public static final String DEFAULT_STARTTIME = "14.00";
    public static final String DEFAULT_ENDDATE = "14-04-19";
    public static final String DEFAULT_ENDTIME = "17.00";
    public static final String DEFAULT_DESCRIPTION = "Class diagram";
    public static final String DEFAULT_CATEGORY = "c";

    private Name name;
    private StartDate startDate;
    private EndDate endDate;
    private StartTime startTime;
    private EndTime endTime;
    private Description description;
    private Set<Tag> tags;
    private Categories category;

    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        startDate = new StartDate(DEFAULT_STARTDATE);
        startTime = new StartTime(DEFAULT_STARTTIME);
        endDate = new EndDate(DEFAULT_ENDDATE);
        endTime = new EndTime(DEFAULT_ENDTIME);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
        category = new Categories(DEFAULT_CATEGORY);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        startDate = taskToCopy.getStartDate();
        startTime = taskToCopy.getStartTime();
        endDate = taskToCopy.getEndDate();
        endTime = taskToCopy.getEndTime();
        description = taskToCopy.getDescription();
        tags = new HashSet<>(taskToCopy.getTags());
        category = taskToCopy.getCategories();
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code category} of the {@code Task} that we are building.
     */
    public TaskBuilder withCategory(String category) {
        this.category = new Categories(category);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartDate(String startDate) {
        this.startDate = new StartDate(startDate);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartTime(String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withEndDate(String endDate) {
        this.endDate = new EndDate(endDate);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withEndTime(String endTime) {
        this.endTime = new EndTime(endTime);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }


    public Task build() {
        return new Task(name, startDate, startTime, endDate, endTime, description, category, tags);
    }

}

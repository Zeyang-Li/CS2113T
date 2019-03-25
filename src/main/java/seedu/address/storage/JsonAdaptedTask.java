package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Categories;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Name's %s field is missing!";

    private final String name;
    private final String startDate;
    private final String startTime;
    private final String endDate;
    private final String endTime;
    private final String description;
    private final String categories;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("name") String name, @JsonProperty("start date") String startDate,
            @JsonProperty("start time") String startTime, @JsonProperty("end date") String endDate,
            @JsonProperty("end time") String endTime, @JsonProperty("description") String description,
            @JsonProperty("category") String categories, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {

        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.description = description;
        this.categories = categories;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        name = source.getName().fullName;
        startDate = source.getStartDate().value;
        startTime = source.getStartTime().value;
        endDate = source.getEndDate().value;
        endTime = source.getEndTime().value;
        description = source.getDescription().value;
        categories = source.getCategories().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDate.class.getSimpleName()));
        }
        if (!StartDate.isValidStartDate(startDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_CONSTRAINTS);
        }
        final StartDate modelStartDate = new StartDate(startDate);

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartTime.class.getSimpleName()));
        }
        if (!StartTime.isValidStartTime(startTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_CONSTRAINTS);
        }
        final StartTime modelStartTime = new StartTime(startTime);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDate.class.getSimpleName()));
        }
        if (!EndDate.isValidEndDate(endDate)) {
            throw new IllegalValueException(EndDate.MESSAGE_CONSTRAINTS);
        }
        final EndDate modelEndDate = new EndDate(endDate);

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndTime.class.getSimpleName()));
        }
        if (!EndTime.isValidEndTime(endTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_CONSTRAINTS);
        }
        final EndTime modelEndTime = new EndTime(endTime);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (categories == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Categories.class.getSimpleName()));
        }
        if (!Categories.isValidCategories(categories)) {
            throw new IllegalValueException(Categories.MESSAGE_CONSTRAINTS);
        }
        final Categories modelCategories = new Categories(categories);

        final Set<Tag> modelTags = new HashSet<>(taskTags);
        return new Task(modelName, modelStartDate, modelStartTime, modelEndDate, modelEndTime, modelDescription,
                modelCategories, modelTags);
    }

}

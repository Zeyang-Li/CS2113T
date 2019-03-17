package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setStartDate(task.getStartDate());
        descriptor.setStartTime(task.getStartTime());
        descriptor.setEndDate(task.getEndDate());
        descriptor.setEndTime(task.getEndTime());
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(new StartDate(startDate));
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new StartTime(startTime));
        return this;
    }

    /**
     * Sets the {@code endDate} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(new EndDate(endDate));
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new EndTime(endTime));
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}

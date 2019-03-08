package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.TaskBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Description;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code TaskBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Revise CS2113T"), new StartDate("0315"), new StartTime("1400"), new EndDate("0325"),
                new EndTime("1700"), new Description("Class diagram"),
                getTagSet("CS2113T")),
            new Task(new Name("Do CS2101"), new StartDate("0317"), new StartTime("1000"), new EndDate("0318"),
                new EndTime("1000"), new Description("User Guide"),
                getTagSet("CS2101"))
        };
    }

    public static ReadOnlyTaskBook getSampleTaskBook() {
        TaskBook sampleTb = new TaskBook();
        for (Task sampleTask : getSampleTasks()) {
            sampleTb.addTask(sampleTask);
        }
        return sampleTb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

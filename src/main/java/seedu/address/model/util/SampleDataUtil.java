package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.day.Academic;
import seedu.address.model.day.Cca;
import seedu.address.model.day.Date;
import seedu.address.model.day.Day;
import seedu.address.model.day.Entertainment;
import seedu.address.model.day.Errand;
import seedu.address.model.day.Other;
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
 * Contains utility methods for populating {@code TaskBook} with sample data.
 */
public class SampleDataUtil {

    public static final Day SAMPLE_DAYS = new Day(new Date("15-03-19"), new Academic("3.0"),
            new Entertainment("0.0"), new Cca("0.0"), new Errand("0.0"), new Other("0.0"));

    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Revise CS2113T"), new StartDate("15-03-19"), new StartTime("14.00"),
                new EndDate("15-03-19"), new EndTime("17.00"), new Description("Class diagram"), new Categories("a"),
                getTagSet("CS2113T")),
            new Task(new Name("Do CS2101"), new StartDate("17-03-19"), new StartTime("10.00"), new EndDate("17-03-19"),
                new EndTime("14.00"), new Description("User Guide"), new Categories("a"),
                getTagSet("CS2101"))
        };
    }

    public static Day[] getSampleDays() {
        return new Day[] {
            new Day(new Date("15-03-19"), new Academic("3.0"), new Entertainment("0.0"),
                    new Cca("0.0"), new Errand("0.0"), new Other("0.0")),
            new Day(new Date("17-03-19"), new Academic("2.0"), new Entertainment("0.0"),
                    new Cca("0.0"), new Errand("0.00"), new Other("0.0"))
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

package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CS2113;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task CS2110 = new TaskBuilder().withName("CS2110")
            .withStartDate("01-01-11").withStartTime("01.00").withEndDate("02-01-02").withEndTime("02.00")
            .withDescription("Do sequence").withCategory("a")
            .withTags("CS2110").build();
    public static final Task CS2100 = new TaskBuilder().withName("cs2100")
            .withStartDate("03-03-03").withStartTime("03.00").withCategory("a")
            .withDescription("Do user").withEndDate("04-03-04").withEndTime("04.00")
            .withTags("CS2100").build();

    // Manually added
    public static final Task HOME = new TaskBuilder().withName("Do housework")
            .withStartDate("11-11-11").withStartTime("10.00").withEndDate("12-11-11").withEndTime("11.00")
            .withDescription("Throw rubbish").withCategory("r").build();
    public static final Task ENT = new TaskBuilder().withName("Watch TV")
            .withStartDate("11-11-11").withStartTime("12.00").withEndDate("12-11-11").withEndTime("13.00")
            .withDescription("Discovery").withCategory("e").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task CS2113 = new TaskBuilder().withName(VALID_NAME_CS2113)
            .withStartDate(VALID_STARTDATE_CS2113).withStartTime(VALID_STARTTIME_CS2113)
            .withEndDate(VALID_ENDDATE_CS2113).withEndTime(VALID_ENDTIME_CS2113)
            .withDescription(VALID_DESCRIPTION_CS2113).withCategory(VALID_CATEGORY_CS2113)
            .withTags(VALID_TAG_CS2113).build();
    public static final Task CS2101 = new TaskBuilder().withName(VALID_NAME_CS2101)
            .withStartDate(VALID_STARTDATE_CS2101).withStartTime(VALID_STARTTIME_CS2101)
            .withEndDate(VALID_ENDDATE_CS2101).withEndTime(VALID_ENDTIME_CS2101)
            .withDescription(VALID_DESCRIPTION_CS2101).withCategory(VALID_CATEGORY_CS2101)
            .withTags(VALID_TAG_CS2101).build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code TaskBook} with all the typical tasks.
     */
    public static TaskBook getTypicalTaskBook() {
        TaskBook tb = new TaskBook();
        for (Task task : getTypicalTasks()) {
            tb.addTask(task);
        }
        return tb;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(CS2110, CS2100));
    }
}

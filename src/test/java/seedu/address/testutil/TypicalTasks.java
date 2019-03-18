package seedu.address.testutil;

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

    public static final Task CS2113 = new TaskBuilder().withName("CS2113")
            .withStartDate("11-11-11").withStartTime("11.11").withEndDate("22-22-22").withEndTime("22.22")
            .withDescription("Do sequence diagram")
            .withTags("CS2113").build();
    public static final Task CS2101 = new TaskBuilder().withName("cs2101")
            .withStartDate("33-33-33").withStartTime("33.33")
            .withDescription("Do user guide").withEndDate("44-44-44").withEndTime("44.44").build();

    // Manually added

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task CS2113T = new TaskBuilder().withName(VALID_NAME_CS2113)
            .withStartDate(VALID_STARTDATE_CS2113).withStartTime(VALID_STARTTIME_CS2113)
            .withEndDate(VALID_ENDDATE_CS2113).withEndTime(VALID_ENDTIME_CS2113)
            .withDescription(VALID_DESCRIPTION_CS2113)
            .withTags(VALID_TAG_CS2113).build();
    public static final Task CS2100 = new TaskBuilder().withName(VALID_NAME_CS2101)
            .withStartDate(VALID_STARTDATE_CS2101).withStartTime(VALID_STARTTIME_CS2101)
            .withEndDate(VALID_ENDDATE_CS2101).withEndTime(VALID_ENDTIME_CS2101)
            .withDescription(VALID_DESCRIPTION_CS2101)
            .withTags(VALID_TAG_CS2101).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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
        return new ArrayList<>(Arrays.asList(CS2113, CS2101, CS2113T, CS2100));
    }
}

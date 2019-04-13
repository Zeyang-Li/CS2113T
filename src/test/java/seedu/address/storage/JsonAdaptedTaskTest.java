package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalTasks.CS2100;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Categories;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;
import seedu.address.testutil.Assert;

public class JsonAdaptedTaskTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_STARTDATE = "11.11.11";
    private static final String INVALID_STARTTIME = "11:11";
    private static final String INVALID_ENDDATE = "22.22.22";
    private static final String INVALID_ENDTIME = "22:22";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_CATEGORIES = "b";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = CS2100.getName().toString();
    private static final String VALID_STARTDATE = CS2100.getStartDate().toString();
    private static final String VALID_STARTTIME = CS2100.getStartTime().toString();
    private static final String VALID_ENDDATE = CS2100.getEndDate().toString();
    private static final String VALID_ENDTIME = CS2100.getEndTime().toString();
    private static final String VALID_DESCRIPTION = CS2100.getDescription().toString();
    private static final String VALID_CATEGORIES = CS2100.getCategories().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = CS2100.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(CS2100);
        assertEquals(CS2100, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(INVALID_NAME, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE, VALID_ENDTIME,
                        VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE,
                VALID_ENDTIME, VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, INVALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE, VALID_ENDTIME,
                        VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = StartDate.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, null, VALID_STARTTIME, VALID_ENDDATE, VALID_ENDTIME,
                VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, INVALID_STARTTIME, VALID_ENDDATE, VALID_ENDTIME,
                        VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = StartTime.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, null, VALID_ENDDATE, VALID_ENDTIME,
                VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, INVALID_ENDDATE, VALID_ENDTIME,
                        VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = EndDate.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, null, VALID_ENDTIME,
                VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE, INVALID_ENDTIME,
                        VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = EndTime.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE, null,
                VALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE, VALID_ENDTIME,
                        INVALID_DESCRIPTION, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE,
                VALID_ENDTIME, null, VALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidCategories_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE, VALID_ENDTIME,
                        VALID_DESCRIPTION, INVALID_CATEGORIES, VALID_TAGS);
        String expectedMessage = Categories.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullCategories_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE,
                VALID_ENDTIME, VALID_DESCRIPTION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Categories.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_STARTDATE, VALID_STARTTIME, VALID_ENDDATE, VALID_ENDTIME,
                        VALID_DESCRIPTION, VALID_CATEGORIES, invalidTags);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }

}

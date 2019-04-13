package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.ENDDATE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CS2113;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTasks.CS2101;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Categories;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(CS2101).withTags(VALID_TAG_CS2101).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS2101 + STARTDATE_DESC_CS2101
                + STARTTIME_DESC_CS2101 + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101
                + CATEGORY_DESC_CS2101 + TAG_DESC_CS2101, new AddCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_CS2113 + NAME_DESC_CS2101 + STARTDATE_DESC_CS2101
                + STARTTIME_DESC_CS2101 + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101
                + CATEGORY_DESC_CS2101 + TAG_DESC_CS2101, new AddCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder(CS2101).withTags(VALID_TAG_CS2101, VALID_TAG_CS2113)
                .build();
        assertParseSuccess(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101
                + STARTTIME_DESC_CS2101 + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101
                + CATEGORY_DESC_CS2101 + TAG_DESC_CS2101 + TAG_DESC_CS2113, new AddCommand(expectedTaskMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder(CS2101).withTags().build();
        assertParseSuccess(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101
                + STARTTIME_DESC_CS2101 + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101
                + CATEGORY_DESC_CS2101, new AddCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101,
                expectedMessage);

        // missing startDate prefix
        assertParseFailure(parser, NAME_DESC_CS2101 + VALID_STARTDATE_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101,
                expectedMessage);

        // missing startTime prefix
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + VALID_STARTTIME_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101,
                expectedMessage);

        // missing endDate prefix
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + VALID_ENDDATE_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101,
                expectedMessage);

        // missing endTime prefix
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + VALID_ENDTIME_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + VALID_DESCRIPTION_CS2101 + CATEGORY_DESC_CS2101,
                expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                        + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + VALID_CATEGORY_CS2101,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_CS2101 + VALID_STARTDATE_CS2101 + VALID_STARTTIME_CS2101
                + VALID_ENDDATE_CS2101 + VALID_ENDTIME_CS2101 + VALID_DESCRIPTION_CS2101 + VALID_CATEGORY_CS2101,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101
                + TAG_DESC_CS2101, Name.MESSAGE_CONSTRAINTS);

        // invalid startDate
        assertParseFailure(parser, NAME_DESC_CS2101 + INVALID_STARTDATE_DESC + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101
                + TAG_DESC_CS2101, StartDate.MESSAGE_CONSTRAINTS);

        // invalid startTime
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + INVALID_STARTTIME_DESC
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101
                + TAG_DESC_CS2101, StartTime.MESSAGE_CONSTRAINTS);

        // invalid endDate
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + INVALID_ENDDATE_DESC + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101
                + TAG_DESC_CS2101, EndDate.MESSAGE_CONSTRAINTS);

        // invalid endTime
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + INVALID_ENDTIME_DESC + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101
                + TAG_DESC_CS2101, EndTime.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + INVALID_DESCRIPTION_DESC + CATEGORY_DESC_CS2101
                + TAG_DESC_CS2101, Description.MESSAGE_CONSTRAINTS);

        // invalid categories
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + INVALID_CATEGORY_DESC
                + TAG_DESC_CS2101, Categories.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_CS2101 + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101 + CATEGORY_DESC_CS2101
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + STARTDATE_DESC_CS2101 + STARTTIME_DESC_CS2101
                + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + INVALID_DESCRIPTION_DESC + CATEGORY_DESC_CS2101,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CS2101 + STARTDATE_DESC_CS2101
                + STARTTIME_DESC_CS2101 + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101
                + CATEGORY_DESC_CS2101, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

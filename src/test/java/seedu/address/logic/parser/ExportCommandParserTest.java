package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ExportCommandParser.MESSAGE_INVALID_CATEGORY_FORMAT;
import static seedu.address.logic.parser.ExportCommandParser.MESSAGE_INVALID_FILETYPE_FORMAT;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_invalidArgsLength_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a all.json test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_invalidFilename_throwsParseException() {
        // wrong filetype
        assertParseFailure(parser, "testExportFile.xml",
                String.format(MESSAGE_INVALID_FILETYPE_FORMAT, ExportCommand.MESSAGE_USAGE));

        // no extension
        assertParseFailure(parser, "testExportFile",
                String.format(MESSAGE_INVALID_FILETYPE_FORMAT, ExportCommand.MESSAGE_USAGE));

        // wrong category type
        assertParseFailure(parser, "testExportFile.json t",
                String.format(MESSAGE_INVALID_CATEGORY_FORMAT, ExportCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "testExportFile.json cca",
                String.format(MESSAGE_INVALID_CATEGORY_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validFileType() {
        Path filePath;
        ExportCommand expectedCommand;

        filePath = Paths.get("data", "testExportFile.json");
        expectedCommand = new ExportCommand(filePath);

        // parse user input without whitespaces
        assertParseSuccess(parser, " testExportFile.json", expectedCommand);

        // parse user input with whitespaces
        assertParseSuccess(parser, "  testExportFile.json    ", expectedCommand);
    }

    @Test
    public void parse_validCategoryType() {
        Path filePath;

        filePath = Paths.get("data", "testExportFile.json");

        assertParseSuccess(parser, "testExportFile.json a", new ExportCommand(filePath, "a"));

        assertParseSuccess(parser, "testExportFile.json c", new ExportCommand(filePath, "c"));

        assertParseSuccess(parser, "testExportFile.json e", new ExportCommand(filePath, "e"));

        assertParseSuccess(parser, "testExportFile.json r", new ExportCommand(filePath, "r"));

        assertParseSuccess(parser, "testExportFile.json o", new ExportCommand(filePath, "o"));

    }
}

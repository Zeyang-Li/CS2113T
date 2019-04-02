package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Contains format string for input of each command in Command Line Interface (CLI)
 */
public class CommandFormatString {
    public static final String ADD_COMMAND_FORMATSTRING = PREFIX_NAME + "task_name  "
            + PREFIX_STARTDATE + "dd-mm-yy  "
            + PREFIX_STARTTIME + "hh:mm  "
            + PREFIX_ENDDATE + "dd-mm-yy  "
            + PREFIX_ENDTIME + "hh:mm  "
            + PREFIX_DESCRIPTION + "content  "
            + PREFIX_CATEGORY + "category  "
            + "[" + PREFIX_TAG + "tag]";
    public static final String LIST_COMMAND_FORMATSTRING = "dd-mm-yy";
    public static final String EDIT_COMMAND_FORMATSTRING = PREFIX_NAME + "task_name  "
            + PREFIX_STARTDATE + "dd-mm-yy  "
            + PREFIX_STARTTIME + "hh:mm  "
            + PREFIX_ENDDATE + "dd-mm-yy  "
            + PREFIX_ENDTIME + "hh:mm  "
            + PREFIX_DESCRIPTION + "content  ";
    public static final String FIND_COMMAND_FORMATSTRING = "Keyword";
    public static final String DELETE_COMMAND_FORMATSTRING = "Index";
    public static final String CLEAR_COMMAND_FORMATSTRING = "dd-mm-yy";
}

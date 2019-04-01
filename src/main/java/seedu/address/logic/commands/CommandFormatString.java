package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class CommandFormatString {
	public static final String addCommandFormatString = PREFIX_NAME + "task_name  "
            + PREFIX_STARTDATE + "start_date  "
            + PREFIX_STARTTIME + "start_time  "
            + PREFIX_ENDDATE + "end_date  "
            + PREFIX_ENDTIME + "end_time  "
            + PREFIX_DESCRIPTION + "content  "
            + PREFIX_CATEGORY + "category  "
            + "[" + PREFIX_TAG + "tag]";
    public static final String listCommandFormatString = "Date";
    public static final String editCommandFormatString = PREFIX_NAME + "task name  "
            + PREFIX_STARTDATE + "start_date  "
            + PREFIX_STARTTIME + "start_time  "
            + PREFIX_ENDDATE + "end_date  "
            + PREFIX_ENDTIME + "end_time  "
            + PREFIX_DESCRIPTION + "content  ";
    public static final String findCommandFormatString = "Keyword";
    public static final String deleteCommandFormatString = "Index";
    public static final String clearCommandFormatString = "Date";
}

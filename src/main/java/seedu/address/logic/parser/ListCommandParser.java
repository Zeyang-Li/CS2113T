package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListtdCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;

public class ListCommandParser {

    public String parse(String userInput) throws ParseException {
        String[] arguments = userInput.trim().split("\\s+");
        if(arguments.length == 1){
            if(arguments[0].equals("")){
                return "ListCommand";
            }else if(arguments[0].equals("td")){
                return "ListtdCommand";
            }else if(is_Valid_Date_Format(arguments[0])){
                return arguments[0];
            }else{
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        } else{
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    public boolean is_Valid_Date_Format(String str){
        String[] str_split = str.split("-");
        if(str_split.length == 2 || str_split.length == 3){
            return true;
        }else{
            return false;
        }
    }

}

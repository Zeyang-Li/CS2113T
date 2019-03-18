package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

public class ListDateCommand extends Command{
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_USAGE = "list : list specified tasks" +
            "\nParameters:" +
            "\n1. no parameters: list        list all tasks" +
            "\n2. td: list td      list all the tasks of today" +
            "\n3. DATE: list 25-03-19    list all the tasks which are on 25th March, 2019";
    public static final String MESSAGE_SUCCESS = "Listed tasks on %1$s";
    private String specified_date;
    public ListDateCommand(String date){
        this.specified_date = date;
    }

    private boolean meetRequirement(Task task){
        String[] date_info = specified_date.split("-");
        if(date_info.length == 3){
            final String taskDate = task.getStartDate().value;
            if (taskDate.equals(specified_date)) {
                return true;
            }else{
                return false;
            }
        }else if(date_info.length == 2){

            final String taskDate = task.getStartDate().value;
            String[] split_date = taskDate.split("-");
            if (split_date[1].equals(date_info[0]) && split_date[2].equals(date_info[1]) ) {
                return true;
            }else{
                return false;
            }

        }
        return false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        Predicate<Task> predicate = task -> meetRequirement(task);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, specified_date));
    }
}

package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.task.Task;

public class CategoryExtractor {
    public static List<Task> findTaskOfSpecifiedCategory(List<Task> originalTasks, String category) {
        List<Task> resultTasks = new ArrayList<Task>();
        for(Task task : originalTasks) {
            if (task.getCategory().equals(category)) {
                resultTasks.add(task);
            }
        }
        return resultTasks;
    }

}

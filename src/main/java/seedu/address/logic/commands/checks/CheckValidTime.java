package seedu.address.logic.commands.checks;

import seedu.address.model.task.Task;

/**
 * Checking if endTime of a task is after startTime.
 */
public class CheckValidTime {
    private Task task;
    private boolean isValidTime;

    public CheckValidTime(Task task) {
        this.task = task;
        this.isValidTime = isValidTime(task);
    }

    public boolean getCheck() {
        return isValidTime;
    }

    /**
     * Returns true if endTime of a task is after startTime.
     */
    public boolean isValidTime(Task task) {
        String time = task.getEndTime().value;
        double end = task.getEndTime().getTimeDouble(time);
        time = task.getStartTime().value;
        double start = task.getStartTime().getTimeDouble(time);
        if (end > start) {
            return true;
        }
        return false;
    }
}

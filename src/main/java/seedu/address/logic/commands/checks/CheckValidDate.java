package seedu.address.logic.commands.checks;

import seedu.address.model.task.Task;

/**
 * Checking whether endDate of a task is after startDate.
 */
public class CheckValidDate {
    private Task task;
    private boolean isValidDate;

    public CheckValidDate(Task task) {
        this.task = task;
        this.isValidDate = isValidDate(task);
    }

    public boolean getCheck() {
        return isValidDate;
    }

    /**
     * Returns true if endDate of a task is after startDate.
     */
    private boolean isValidDate(Task task) {
        String[] dateS = task.getStartDate().value.split("-");
        String[] dateE = task.getEndDate().value.split("-");

        if (Integer.parseInt(dateS[1]) > Integer.parseInt(dateE[1])) {
            return false;
        }
        if (Integer.parseInt(dateS[0]) > Integer.parseInt(dateE[0]) &&
                Integer.parseInt(dateS[1]) > Integer.parseInt(dateE[1])) {
            return false;
        }
        if (Integer.parseInt(dateS[2]) > Integer.parseInt(dateE[2])) {
            return false;
        }
        return true;
    }
}

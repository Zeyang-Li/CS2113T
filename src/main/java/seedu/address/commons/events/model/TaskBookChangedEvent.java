package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskBook;

public class TaskBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;
    /**
     * keep track if the task book is changed.
     */
    public TaskBookChangedEvent(ReadOnlyTaskBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks "
                + data.getTaskList().size();
    }
}

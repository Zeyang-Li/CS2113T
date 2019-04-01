package seedu.address.ui.reminder;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import seedu.address.logic.Logic;
import seedu.address.model.task.Task;
import seedu.address.ui.TaskCard;

/**
 * The UI part of remind feature.
 */
public class ReminderPane extends AnchorPane {
    private VBox vbox = new VBox();
    private Logic logic;

    /**
     * The constructor.
     */
    public ReminderPane(Logic logic) {
        this.logic = logic;
        addTask();
    }

    /**
     * Add task to UI.
     */
    public void addTask() {
        int i = 1;
        for (Task task : logic.getFilteredTaskList()) {
            TaskCard taskCard = new TaskCard(task, i++);
            vbox.getChildren().add(taskCard.getRoot());
        }
    }

    /**
     * Get logic.
     */
    public Logic getLogic() {
        return this.logic;
    }

    /**
     * Get view.
     */
    public VBox getView() {
        return this.vbox;
    }
}

package seedu.address.ui.reminder;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    private ObservableList<Task> taskList;
    private ListView<TaskCard> taskListView;
    /**
     * The constructor.
     */
    public ReminderPane(Logic logic, ObservableList<Task> taskList) {
        taskListView = new ListView<>();
        this.logic = logic;
        this.taskList = taskList;
        addTask();
    }

    /**
     * Add task to UI.
     */
    public void addTask() {
        int i = 1;
        for (Task task : taskList) {
            taskListView.getItems().add(new TaskCard(task, i++));
        }
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        vbox.getChildren().addAll(taskListView);
    }

    /**
     * Get logic.
     */
    public Logic getLogic() {
        return this.logic;
    }

    public ListView<TaskCard> getListView() { return this.taskListView; }

    public ObservableList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Get view.
     */
    public VBox getView() {
        return this.vbox;
    }

    /**
     * Travis
     */
    class TaskListViewCell extends ListCell<TaskCard> {
        @Override
        protected void updateItem(TaskCard taskCard, boolean empty) {
            super.updateItem(taskCard, empty);

            if (empty || taskCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(taskCard.getRoot());
            }
        }
    }
}

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label description;
    @FXML
    private Label categories;
    @FXML
    private FlowPane tags;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        startDate.setText(task.getStartDate().value);
        startTime.setText(task.getStartTime().value);
        endDate.setText(task.getEndDate().value);
        endTime.setText(task.getEndTime().value);
        description.setText(task.getDescription().value);
        categories.setText(task.getCategories().fullName);
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }

    /**
     * Set tags to urgent;
     */
    public void setUrgent(int i) {
        //name.set(Font.createFont("Verdana", ))
        if (i == 1) {
            cardPane.setStyle("-fx-background-color: #dc5712;");
        } else if (i == 2) {
            cardPane.setStyle("-fx-background-color: #e58308;");
        } else if (i == 3) {
            cardPane.setStyle("-fx-background-color: #f4d000;");
        } else if (i == 4) {
            cardPane.setStyle("-fx-background-color: #8a977b;");
        } else if (i == 5) {
            cardPane.setStyle("-fx-background-color: #b6c29a;");
        }
    }
}


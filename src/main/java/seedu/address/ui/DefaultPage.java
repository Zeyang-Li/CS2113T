package seedu.address.ui;

import javafx.collections.ObservableList;

import javafx.event.Event;

import javafx.fxml.FXML;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.Task;

/**
 * Default page contains calendar, reminder and timeline.
 */
public class DefaultPage extends UiPart<Region>{
    
    private static final String FXML = "DefaultPage.fxml";
    
    @FXML
    private SplitPane overallPane;
    
    @FXML
    private AnchorPane calendarAnchorPane;
    
    @FXML
    private AnchorPane reminderAnchorPane;

    @FXML
    private AnchorPane timelineAnchorPane;
    
    public DefaultPage(ObservableList<Task> taskList) {
        super(FXML);
        timelineAnchorPane.maxWidthProperty().bind(overallPane.widthProperty().multiply(0.5));
    }
}

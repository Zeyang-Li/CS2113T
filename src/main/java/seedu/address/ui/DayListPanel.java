package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.day.Day;

/**
 * Panel containing the list of tasks.
 */
public class DayListPanel extends UiPart<Region> {
    private static final String FXML = "DayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DayListPanel.class);

    @FXML
    private ListView<Day> dayListView;

    public DayListPanel(ObservableList<Day> dayList, ObservableValue<Day> selectedDay,
                        Consumer<Day> onSelectedDayChange) {
        super(FXML);
        dayListView.setItems(dayList);
        dayListView.setCellFactory(listView -> new DayListViewCell());
        dayListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in day list panel changed to : '" + newValue + "'");
            onSelectedDayChange.accept(newValue);
        });
        selectedDay.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected day changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected day,
            // otherwise we would have an infinite loop.
            if (Objects.equals(dayListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                dayListView.getSelectionModel().clearSelection();
            } else {
                int index = dayListView.getItems().indexOf(newValue);
                dayListView.scrollTo(index);
                dayListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Day} using a {@code DayCard}.
     */
    class DayListViewCell extends ListCell<Day> {
        @Override
        protected void updateItem(Day day, boolean empty) {
            super.updateItem(day, empty);

            if (empty || day == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DayCard(day, getIndex() + 1).getRoot());
            }
        }
    }

}

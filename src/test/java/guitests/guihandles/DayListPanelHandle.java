package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.day.Day;

/**
 * Provides a handle for {@code TaskListPanel} containing the list of {@code TaskCard}.
 */
public class DayListPanelHandle extends NodeHandle<ListView<Day>> {
    public static final String DAY_LIST_VIEW_ID = "#dayListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Day> lastRememberedSelectedDayCard;

    public DayListPanelHandle(ListView<Day> dayListPanelNode) {
        super(dayListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code DayCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public DayCardHandle getHandleToSelectedCard() {
        List<Day> selectedDayList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedDayList.size() != 1) {
            throw new AssertionError("Day list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(DayCardHandle::new)
                .filter(handle -> handle.equals(selectedDayList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Day> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code day}.
     */
    public void navigateToCard(Day day) {
        if (!getRootNode().getItems().contains(day)) {
            throw new IllegalArgumentException("Day does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(day);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code DayCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the day card handle of a day associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public DayCardHandle getDayCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(DayCardHandle::new)
                .filter(handle -> handle.equals(getDay(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Day getDay(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code DayCard} in the list.
     */
    public void rememberSelectedDayCard() {
        List<Day> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedDayCard = Optional.empty();
        } else {
            lastRememberedSelectedDayCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code DayCard} is different from the value remembered by the most recent
     * {@code rememberSelectedDayCard()} call.
     */
    public boolean isSelectedDayCardChanged() {
        List<Day> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedDayCard.isPresent();
        } else {
            return !lastRememberedSelectedDayCard.isPresent()
                    || !lastRememberedSelectedDayCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

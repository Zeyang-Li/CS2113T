package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.day.Day;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class DayCard extends UiPart<Region> {

    private static final String FXML = "DayListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Day day;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label academic;
    @FXML
    private Label cca;
    @FXML
    private Label entertainment;
    @FXML
    private Label errand;
    @FXML
    private Label other;

    public DayCard(Day day, int displayedIndex) {
        super(FXML);
        this.day = day;
        id.setText(displayedIndex + ". ");
        date.setText(day.getDate().value);
        academic.setText(day.getAcademic().getTimeString());
        cca.setText(day.getCca().getTimeString());
        entertainment.setText(day.getEntertainment().getTimeString());
        errand.setText(day.getErrand().getTimeString());
        other.setText(day.getOther().getTimeString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DayCard)) {
            return false;
        }

        // state check
        DayCard card = (DayCard) other;
        return id.getText().equals(card.id.getText())
                && day.equals(card.day);
    }
}

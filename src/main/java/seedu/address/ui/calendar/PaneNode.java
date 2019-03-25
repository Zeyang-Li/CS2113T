package seedu.address.ui.calendar;

import java.time.LocalDate;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


/**
 * This is each single date grid.
 */
public class PaneNode extends AnchorPane {

    private LocalDate day;

    public PaneNode(Node... children) {
        super(children);
    }

    public LocalDate getDay() { return day; }

    public void setDay(LocalDate newDay) { this.day = newDay; }

}

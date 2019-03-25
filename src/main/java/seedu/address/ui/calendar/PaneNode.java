package seedu.address.ui.calendar;

import java.time.LocalDate;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


/**
 * This is each single date grid.
 */
public class PaneNode extends AnchorPane {

    private LocalDate day;

    /**
     * Initialize.
     * @param children
     */
    public PaneNode(Node... children) {
        super(children);
    }

    /**
     * Get today.
     * @return
     */
    public LocalDate getDay() { 
        return day; 
    }

    public void setDay(LocalDate newDay) { this.day = newDay; }

}

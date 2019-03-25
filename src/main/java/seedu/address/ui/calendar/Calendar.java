package seedu.address.ui.calendar;

import java.time.YearMonth;

import java.time.LocalDate;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The real implementation for calendar will be here.
 */
public class Calendar extends Node {
    private ArrayList<PaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private YearMonth shownYearMonth;

    /**
     * Create a calendar with 35 grids (5x7).
     * @param yearMonth
     */
    public Calendar(YearMonth yearMonth) {
        shownYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(340, 200);

        //Fill up the calendar.
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                PaneNode newGrid = new PaneNode();
                newGrid.setPrefSize(48, 40);
                calendar.add(newGrid, j, i);
                allCalendarDays.add(newGrid);
            }
        }

        Text[] weekDays = { new Text("Sun"),
                            new Text("Mon"), new Text("Tues"),
                            new Text("Wed"), new Text("Thur"),
                            new Text("Fri"), new Text("Sat") };

        GridPane weekDayLabel = new GridPane();
        weekDayLabel.setPrefWidth(340);

        int col = 1;
        for (Text day : weekDays) {
            AnchorPane aGrid = new AnchorPane();
            aGrid.setPrefSize(48, 10);
            aGrid.getChildren().add(day);
            weekDayLabel.add(aGrid, col, 0);
            col++;
        }

        Text calendarTitle = new Text(); //Title to be added later
        HBox titleBar = new HBox(calendarTitle);
        showCalendar(yearMonth);
        calendar.setGridLinesVisible(true);
        view = new VBox(titleBar, weekDayLabel, calendar);
    }

    /**
     * Show the calendar.
     * Show from the first Sunday before current month.
     * @param
     */
    public void showCalendar(YearMonth yearMonth) {
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }

        for (PaneNode aGrid : allCalendarDays) {
            if (aGrid.getChildren().size() != 0) {
                aGrid.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            aGrid.setDay(calendarDate);
            aGrid.setTopAnchor(txt, 5.0);
            aGrid.setLeftAnchor(txt, 5.0);
            aGrid.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
    }

    /**
     * Change the current view to previous month, FOR FUTURE DEVELOPMENT.
     */
    public void previousMonth() {
        shownYearMonth = shownYearMonth.minusMonths(1);
        showCalendar(shownYearMonth);
    }

    /**
     * Change the current view to next month, FOR FUTURE DEVELOPMENT.
     */
    public void nextMonth() {
        shownYearMonth = shownYearMonth.plusMonths(1);
        showCalendar(shownYearMonth);
    }

    /**
     * Required by Travis.
     * @return
     */
    public VBox getView() {
        return view;
    }

    public ArrayList<PaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    /*
    public void setAllCalendarDays(ArrayList<PaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
    */

    @Override
    public Node getStyleableNode() {
        return null;
    }
}

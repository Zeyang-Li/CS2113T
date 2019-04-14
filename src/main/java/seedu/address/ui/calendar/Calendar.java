package seedu.address.ui.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.task.Task;


/**
 * The real implementation for calendar will be here.
 */
public class Calendar extends Node {
    private static final int CALENDAR_WIDTH = 350;
    private static final int CALENDAR_HEIGHT = 175;
    private static final int GRID_WIDTH = 48;
    private static final int GRID_HEIGHT = 40;
    private static final double ANCHOR_SIZE = 5;

    private ArrayList<PaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private YearMonth shownYearMonth;
    private int[][][] numOfTasks = new int[5][12][35]; //Assuming 5 years
    private ObservableList<Task> taskList;

    /**
     * Create a calendar with 35 grids (5x7).
     * @param yearMonth
     */
    public Calendar(YearMonth yearMonth, ObservableList<Task> tasks) {
        shownYearMonth = yearMonth;
        this.taskList = tasks;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        GridPane.setMargin(calendar, new Insets(0, 0, 0, 5));
        calendar.setPrefSize(CALENDAR_WIDTH, CALENDAR_HEIGHT);

        //Fill up the calendar.
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                PaneNode newGrid = new PaneNode();
                newGrid.setPrefSize(GRID_WIDTH, GRID_HEIGHT);
                newGrid.setStyle("-fx-background-color: white;");
                calendar.add(newGrid, j, i);
                allCalendarDays.add(newGrid);
            }
        }

        Text[] weekDays = { new Text("Sun"),
                            new Text("Mon"), new Text("Tues"),
                            new Text("Wed"), new Text("Thur"),
                            new Text("Fri"), new Text("Sat") };

        GridPane weekDayLabel = new GridPane();
        weekDayLabel.setPrefWidth(CALENDAR_WIDTH);

        int col = 1;
        for (Text day : weekDays) {
            AnchorPane aGrid = new AnchorPane();
            aGrid.setPrefSize(GRID_WIDTH, GRID_HEIGHT);
            aGrid.getChildren().add(day);
            weekDayLabel.add(aGrid, col, 0);
            col++;
        }

        //Text calendarTitle = new Text(); //Title to be added later
        //HBox titleBar = new HBox(calendarTitle);
        //titleBar.setAlignment(Pos.BASELINE_CENTER);
        weekDayLabel.setAlignment(Pos.BASELINE_CENTER);
        calendar.setAlignment(Pos.BASELINE_CENTER);
        showCalendar(yearMonth);
        calendar.setGridLinesVisible(true);

        //view.getChildren().add(weekDayLabel);
        //view.getChildren().add(calendar);

        view = new VBox(new Text(""), weekDayLabel, calendar);
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
        //Pre process tasks to count # of tasks in a day
        //resetNum();
        //System.out.println(yearMonth.getMonthValue());
        //preProcess(taskList);
        for (PaneNode aGrid : allCalendarDays) {
            if (aGrid.getChildren().size() != 0) {
                aGrid.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            aGrid.setDay(calendarDate);
            aGrid.setTopAnchor(txt, ANCHOR_SIZE);
            aGrid.setLeftAnchor(txt, ANCHOR_SIZE);
            aGrid = setColor(aGrid, yearMonth.getYear(), yearMonth.getMonthValue(), calendarDate.getDayOfMonth());
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
     * Change the current view to next month, FOR FUTURE DEVELOPMENT.
     */
    public void thisMonth() {
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

    /**
     * Set the calendar day to specific colors.
     * @param p
     * @param year
     * @param month
     * @param day
     * @return
     */
    private PaneNode setColor(PaneNode p, int year, int month, int day) {
        if (!checkdate(year - 2017, month, day)) {
            //System.out.println(year - 2017);
            return p;
        }
        if (numOfTasks[year - 2017][month][day] >= 10) {
            p.setStyle("-fx-background-color: #ff0000");
            numOfTasks[year - 2017][month][day] = -1;
        } else if (numOfTasks[year - 2017][month][day] > 5) {
            p.setStyle("-fx-background-color: #00ff00");
            numOfTasks[year - 2017][month][day] = -1;
        } else if (numOfTasks[year - 2017][month][day] > 1) {
            p.setStyle("-fx-background-color: #0000ff");
            numOfTasks[year - 2017][month][day] = -1;
        }
        return p;
    }

    /**
     * set the desired color of each calendar node.
     * @return
     */
    private void preProcess(ObservableList <Task> tasks) {
        for (Task t : tasks) {
            String date = t.getEndDate().toString();
            String day = date.split("-")[0];
            String month = date.split("-")[1];
            String year = date.split("-")[2];
            //System.out.print(Integer.parseInt(year)-17);
            if (!checkdate(Integer.parseInt(year) - 17,
                Integer.parseInt(month) - 1,
                Integer.parseInt(day) - 1)) {
                continue;
            }
            numOfTasks[Integer.parseInt(year) - 17][Integer.parseInt(month)][Integer.parseInt(day)] += 1;
        }
    }

    /**
     * Checks whether the tasks is with in 10 years.
     * @param year
     * @param month
     * @param day
     * @return
     */
    private boolean checkdate(int year, int month, int day) {
        if (year < 0 || year >= 5) {
            return false;
        }
        if (month < 0 || month >= 12) {
            return false;
        }
        if (day < 0 || day >= 31) {
            return false;
        }
        return true;
    }

    /**
     * Reset number of tasks.
     */
    private void resetNum() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                for (int k = 0; k < 35; k++) {
                    numOfTasks[i][j][k] = 0;
                }
            }
        }
    }

    @Override
    public Node getStyleableNode() {
        return null;
    }
}

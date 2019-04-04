package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;

import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.model.task.Task;
import seedu.address.ui.calendar.Calendar;
import seedu.address.ui.reminder.ReminderPane;
import seedu.address.ui.timeline.TimePane;

/**
 * Default page contains calendar, reminder and timeline.
 */
public class DefaultPage extends UiPart<Region> {

    private static final String FXML = "DefaultPage.fxml";
    private String day;
    private ObservableList<Task> all;
    private DayListPanel dayListPanel;

    @FXML
    private SplitPane overallPane;

    @FXML
    private AnchorPane calendarAnchorPane;

    @FXML
    private AnchorPane reminderAnchorPane;

    @FXML
    private StackPane dayListPane;

    @FXML
    private AnchorPane timelineAnchorPane;

    @FXML
    private AnchorPane upperPartAnchorPane;

    private Logic logic;

    public DefaultPage(ObservableList<Task> taskList, Logic logic) {
        super(FXML);
        init();
        this.logic = logic;
        this.all = taskList;

        this.day = getDay();
        this.logic = logic;
        this.dayListPanel = new DayListPanel(logic.getFilteredDayList(), logic.selectedDayProperty(),
                logic::setSelectedDay);

        //Show the calendar
        calendarAnchorPane.getChildren().add(new Calendar(YearMonth.now()).getView());
        timelineAnchorPane.getChildren().add(new TimePane(taskList, day).getView());
        reminderAnchorPane.getChildren().add(new ReminderPane(logic, logic.getRemindTaskList()).getView());
        dayListPane.getChildren().add(dayListPanel.getRoot());

    }

    /**
     * Set each window to proper fixed size.
     */
    private void init() {
        upperPartAnchorPane.maxHeightProperty().bind(overallPane.heightProperty().multiply(0.55));
        timelineAnchorPane.maxHeightProperty().bind(overallPane.heightProperty().multiply(0.45));
        calendarAnchorPane.maxWidthProperty().bind(upperPartAnchorPane.widthProperty().multiply(0.5));
        reminderAnchorPane.maxWidthProperty().bind(upperPartAnchorPane.widthProperty().multiply(0.5));
        dayListPane.maxWidthProperty().bind(upperPartAnchorPane.widthProperty().multiply(0.5));
    }

    /**
     * Set the desired month.
     */
    public void setMonth(String month) {
        Calendar c = new Calendar(YearMonth.now());
        //System.out.println(month);
        if (month.equals("Viewing next month's calendar!")) {
            c.nextMonth();
            //System.out.println(month);
        }
        if (month.equals("Viewing previous month's calendar!")) {
            c.previousMonth();
            //System.out.println(month);
        }
        calendarAnchorPane.getChildren().clear();
        calendarAnchorPane.getChildren().add(c.getView());
    }

    /**
     * Set the desired timeline to that day.
     * @param feedback
     */
    public void setTimeline(String feedback) {
        if (!feedback.split(" ")[0].equals("Timeline")) {
            //System.out.println("not");
            return;
        }
        this.day = feedback.split(" ")[3];
        if (!feedback.split(" ")[3].contains("-")) {
            this.day = feedback.split(" ")[2];
        }
        //System.out.println(day);
        timelineAnchorPane.getChildren().clear();
        timelineAnchorPane.getChildren().add(new TimePane(all, day).getView());
        //System.out.println(day);
    }

    /**
     * This returns the current date.
     * To be updated.
     * @return
     */
    public String getDay() {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YY");
        String formattedToday = formatter.format(today);
        return formattedToday;
    }

    public void setReminder(Logic logic) {
        ReminderPane r = new ReminderPane(logic, logic.getRemindTaskList());
        reminderAnchorPane.getChildren().clear();
        reminderAnchorPane.getChildren().add(r.getView());
    }
}


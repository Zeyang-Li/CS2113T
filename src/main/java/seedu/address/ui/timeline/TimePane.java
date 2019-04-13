package seedu.address.ui.timeline;

import javafx.collections.ObservableList;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import seedu.address.model.task.Task;


/**
 * This is the overall arrangement of timeline pane.
 */
public class TimePane extends AnchorPane {
    private static float LINE_WIDTH = 920.0f;
    private static float LINE_HEIGHT = 10.0f;
    private static float BAR_LINE_HEIGHT = 3.0f;
    private static int GRID_WIDTH = 96;
    private static int GRID_HEIGHT = 10;
    private static int TIMELINE_WIDTH = 800;

    private static String CATEGORY_ACADEMIC = "a";
    private static String CATEGORY_CCA = "c";
    private static String CATEGORY_ENTERTAINMENT = "e";
    private static String CATEGORY_ERRANDS = "r";
    private static String CATEGORY_OTHER = "o";


    private VBox vbox = new VBox();
    private String[] category = {CATEGORY_ACADEMIC,
                                 CATEGORY_CCA,
                                 CATEGORY_ENTERTAINMENT,
                                 CATEGORY_ERRANDS,
                                 CATEGORY_OTHER};

    public TimePane(ObservableList<Task> taskList, String d) {
        showTimeline(taskList, d);
    }

    /**
     * This update the timeline.
     * @param taskList
     * @param d
     */
    public void showTimeline(ObservableList<Task> taskList, String d) {
        vbox.setSpacing(0);
        //=========Set up time points==========
        Text[] timePoints = { new Text(" "), new Text("6:00"),
                              new Text("8:00"), new Text("10:00"),
                              new Text("12:00"), new Text("14:00"),
                              new Text("16:00"), new Text("18:00"),
                              new Text("20:00"), new Text("22:00"),
                              new Text("0:00"), new Text(" ")};

        GridPane timelineLabel = new GridPane();
        timelineLabel.setPrefWidth(TIMELINE_WIDTH);
        //timelineLabel.setGridLinesVisible(true);
        int col = 1;
        for (Text time : timePoints) {
            AnchorPane aGrid = new AnchorPane();
            aGrid.setPrefSize(GRID_WIDTH, GRID_HEIGHT);
            aGrid.getChildren().add(time);
            aGrid.setStyle("-fx-text-inner-color: white;");
            timelineLabel.add(aGrid, col, 0);
            col++;
        }
        //==========Set up a heading rectangle==========
        Rectangle heading = new Rectangle();

        //Setting the properties of the rectangle
        heading.setFill(Color.WHITE);
        heading.setWidth(LINE_WIDTH);
        heading.setHeight(LINE_HEIGHT);

        //==========Set up a bottom rectangle==========
        Rectangle bottom = new Rectangle();

        //Setting the properties of the rectangle
        bottom.setFill(Color.WHITE);
        bottom.setWidth(LINE_WIDTH);
        bottom.setHeight(BAR_LINE_HEIGHT);

        //Setting the height and width of the arc
        //heading.setArcWidth(10.0);
        //heading.setArcHeight(10.0);
        //Add to the view
        vbox.getChildren().add(heading);
        vbox.getChildren().add(timelineLabel);

        //==========Set up each timeline for 4 categories==========
        String[] cate = {CATEGORY_ACADEMIC,
                         CATEGORY_CCA,
                         CATEGORY_ENTERTAINMENT,
                         CATEGORY_ERRANDS,
                         CATEGORY_OTHER};
        PreTask[] filteredDate = filterDate(taskList, d);
        for (int i = 0; i < 5; i++) {
            PreTask[] filteredCate = filterCate(filteredDate, category[i]);
            Timeline t = new Timeline(filteredCate, cate[i]);
            vbox.getChildren().add(t.getOneTimeline());
        }
        vbox.getChildren().add(bottom);
    }

    /**
     * This function filters the tasks to desired date.
     * @param taskList
     * @param day
     * @return
     */
    private PreTask[] filterDate(ObservableList<Task> taskList, String day) {
        PreTask[] filteredDate = new PreTask[1000];
        int count = 0;
        for (Task t : taskList) {
            try {
                t.getCategories();
            } catch (NullPointerException ep) {
                break;
            }
            if (t.getStartDate().toString().equals(day)) {
                filteredDate[count] = new PreTask(t.getName().toString(),
                        t.getCategories().toString(),
                        parse(t.getStartTime().toString()),
                        parse(t.getEndTime().toString()));
                count++;
            }
        }
        return filteredDate;
    }

    /**
     * returns filtered category list.
     * The O(n^2) filtering method could be improved to O(n).
     * @param taskList
     * @param cate
     * @return
     */
    private PreTask[] filterCate(PreTask[] taskList, String cate) {
        PreTask[] filtered = new PreTask[1000];
        int count = 0;
        for (PreTask t : taskList) {
            try {
                t.getCate();
            } catch (NullPointerException ep) {
                break;
            }

            if (t.getCate().equals(cate)) {
                filtered[count] = new PreTask(t.getTitle(),
                        t.getCate(),
                        t.getStart(),
                        t.getEnd());
                count++;
            }
        }
        return filtered;
    }

    /**
     * Thie parse a string to a float, representing time.
     * Called in filteredDate method.
     * @param time
     * @return
     */
    private float parse(String time) {
        return Float.parseFloat(time);
    }

    public VBox getView() {
        return this.vbox;
    }
}

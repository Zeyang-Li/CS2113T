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
    private VBox vbox = new VBox();
    private String[] category = {"a", "c", "e", "r", "o"};

    public TimePane(ObservableList<Task> taskList, String d) {
        showTimeline(taskList, d);
    }

    /**
     * This update the timeline.
     * @param taskList
     * @param d
     */
    public void showTimeline(ObservableList<Task> taskList, String d) {
        vbox.setSpacing(12);
        //=========Set up time points==========
        Text[] timePoints = { new Text(" "), new Text("6:00"),
                              new Text("8:00"), new Text("10:00"),
                              new Text("12:00"), new Text("14:00"),
                              new Text("16:00"), new Text("18:00"),
                              new Text("20:00"), new Text("22:00"),
                              new Text("0:00"), new Text("2:00")};

        GridPane timelineLabel = new GridPane();
        timelineLabel.setPrefWidth(800);
        //timelineLabel.setGridLinesVisible(true);
        int col = 1;
        for (Text time : timePoints) {
            AnchorPane aGrid = new AnchorPane();
            aGrid.setPrefSize(96, 10);
            aGrid.getChildren().add(time);
            aGrid.setStyle("-fx-text-inner-color: white;");
            timelineLabel.add(aGrid, col, 0);
            col++;
        }
        //==========Set up a rectangle==========
        Rectangle heading = new Rectangle();
        heading.setFill(Color.BLACK);

        //Setting the properties of the rectangle
        heading.setX(150.0f);
        heading.setY(75.0f);
        heading.setWidth(850.0f);
        heading.setHeight(10.0f);

        //Setting the height and width of the arc
        //heading.setArcWidth(10.0);
        //heading.setArcHeight(10.0);
        //Add to the view
        vbox.getChildren().add(heading);
        vbox.getChildren().add(timelineLabel);

        //==========Set up each timeline for 4 categories==========
        PreTask[] filteredDate = filterDate(taskList, d);
        for (int i = 0; i < 5; i++) {
            PreTask[] filteredCate = filterCate(filteredDate, category[i]);
            Timeline t = new Timeline(filteredCate);
            vbox.getChildren().add(t.getOneTimeline());
        }
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
        //System.out.println("Sofarsogood 11");
        for (PreTask t : taskList) {
             try {
                 t.getCate();
             } catch (NullPointerException ep) {
                 break;
             }

            //System.out.println(t.getCategories().toString().equals(cate));
            if (t.getCate().equals(cate)) {
                //System.out.println("Sofarsogood 33");

                filtered[count] = new PreTask(t.getTitle(),
                                              t.getCate(),
                                              t.getStart(),
                                              t.getEnd());
                count++;
            }
        }
        return filtered;
    }

    private float parse(String time) {
        return Float.parseFloat(time);
    }

    public VBox getView() {
        return this.vbox;
    }
}

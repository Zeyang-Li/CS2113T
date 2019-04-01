package seedu.address.ui.timeline;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Individual Timeline.
 */
public class Timeline {
    private VBox oneTimeline = new VBox();
    private PreTask[] tasks;
    private Text[] taskNames = {new Text("Cate"), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" ")};

    public Timeline(PreTask[] pre) {
        this.tasks = pre;
        sort();
        oneTimeline.setSpacing(13);
        //==========Set up a seperate line==========
        prepLine();
        //==========Set up Titles==========
        prepTitle();
        //==========Set up Timelines==========
        prepTimeline();
    }

    /**
     * This method will prepare the line.
     */
    private void prepLine() {
        Rectangle heading = new Rectangle();
        heading.setFill(Color.GRAY);

        //Setting the properties of the rectangle
        heading.setWidth(850.0f);
        heading.setHeight(1.0f);

        //Setting the height and width of the arc
        heading.setArcWidth(0.3);
        heading.setArcHeight(0.3);
        //Add to the view
        oneTimeline.getChildren().add(heading);
    }

    /**
     * This method will prepare the name of the task.
     */
    private void prepTitle() {
        GridPane taskNameLine = new GridPane();
        taskNameLine.setPrefWidth(850);
        //taskNameLine.setGridLinesVisible(true);
        int col = 1;
        for (Text n : taskNames) {
            AnchorPane aGrid = new AnchorPane();
            aGrid.setPrefSize(100, 5);
            aGrid.getChildren().add(n);
            aGrid.setStyle("-fx-text-inner-color: white;");
            taskNameLine.add(aGrid, col, 0);
            col++;
        }
        oneTimeline.getChildren().add(taskNameLine);
    }

    /**
     * This method will prepare the real timeline.
     */
    private void prepTimeline() {
    }

    /**
     * Sort the tasks.
     * The sorting function could be improved to quicksork / mergesort etc.
     */
    private void sort() {
        int total = getSize();
        //System.out.println(total);

        for (int i = 0; i < total; i++) {
            for (int j = 0; j < total - i - 1; j++) {
                if (tasks[j].getStart() > tasks[j + 1].getStart()) {
                    PreTask t = tasks[j];
                    tasks[j] = tasks[j + 1];
                    tasks[j + 1] = t;
                }
            }
        }
        for (int i = 0; i < total; i++) {
            int time = (Math.round(tasks[i].getStart()) + 20) % 24;
            if (time > 21) {
                System.out.println(time);
                continue;
            }
            taskNames[(Math.round(tasks[i].getStart()) + 20) % 24] = new Text(tasks[i].getTitle());
            //System.out.println(tasks[i].getStart());
        }
    }

    /**
     * get size
     * @return
     */
    private int getSize() {
        int i;
        for (i = 0; true; i++) {
            try {
                tasks[i].getCate();
            } catch (NullPointerException np) {
                break;
            }
        }
        return i;
    }

    public VBox getOneTimeline() {
        return oneTimeline;
    }
}

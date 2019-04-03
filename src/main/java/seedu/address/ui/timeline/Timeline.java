package seedu.address.ui.timeline;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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

    public Timeline(PreTask[] pre, String c) {
        this.tasks = pre;
        sort();
        oneTimeline.setSpacing(13);
        //==========Set up a seperate line==========
        prepLine();
        //==========Set up Titles==========
        prepTitle(c);
        //==========Set up Timelines==========
        prepTimeline(pre);
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
    private void prepTitle(String c) {
        GridPane taskNameLine = new GridPane();
        taskNameLine.setPrefWidth(850);
        //taskNameLine.setGridLinesVisible(true);
        int col = 1;
        taskNames[0] = new Text(getCate(c));
        for (Text n : taskNames) {
            AnchorPane aGrid = new AnchorPane();
            aGrid.setPrefSize(100, 5);
            n.setStyle("-fx-text-inner-color: white;");
            aGrid.getChildren().add(n);
            taskNameLine.add(aGrid, col, 0);
            col++;
        }
        oneTimeline.getChildren().add(taskNameLine);
    }

    /**
     * This method will prepare the real timeline.
     */
    private void prepTimeline(PreTask[] pre) {
        GridPane time = new GridPane();
        time.setPrefWidth(850);
        //time.setGridLinesVisible(true);
        int[] timeInterval = markTimeInterval(pre);
        
        for (int i = 0; i < timeInterval.length; i++) {
            System.out.print(timeInterval[i]);
            switch (timeInterval[i]) {
                case 1:
                    Rectangle rect1 = new Rectangle();
                    rect1.setFill(Color.WHITE);
                    rect1.setHeight(10);
                    rect1.setWidth(50);
                    time.add(rect1, i, 0);
                    break;
                case 2:
                    Region rect2 = new Region();
                    rect2.setPrefSize(50, 10);
                    rect2.setStyle("-fx-background-color: white; -fx-background-radius: 10 0 0 10");
                    time.add(rect2, i, 0);
                    break;
                case 3:
                    Region rect3 = new Region();
                    rect3.setPrefSize(50, 10);
                    rect3.setStyle("-fx-background-color: white; -fx-background-radius: 0 10 10 0");
                    time.add(rect3, i, 0);
                    break;
                case 5:
                    Rectangle rect4 = new Rectangle();
                    rect4.setFill(Color.WHITE);
                    rect4.setHeight(10);
                    rect4.setWidth(50);
                    rect4.setArcWidth(10.0);
                    rect4.setArcHeight(10.0);
                    time.add(rect4, i, 0);
                    break;
                default:
                    AnchorPane aGrid = new AnchorPane();
                    aGrid.setPrefSize(50, 5);
                    aGrid.getChildren().add(new Text(" "));
                    time.add(aGrid, i, 0);
                    break;
            }
        }
        System.out.println("");
        oneTimeline.getChildren().add(time);
    }

    /**
     * Marks time intervals to fill in.
     * @return
     */
    private int[] markTimeInterval(PreTask[] pre) {
        int timeInterval[] = new int[24];
        int start = 0;
        int end = 0;
        for (PreTask t : pre) {
            try {
                t.getStart();
            } catch (NullPointerException np) {
                //System.out.println("null");
                return timeInterval;
            }
            start = (Math.round(t.getStart()) + 20)%24;
            end = (Math.round(t.getEnd()) + 20) % 24;
            if(start == end) {
                timeInterval[start] = 5;
                continue;
            }
            for (int i = 0; i < end - start; i++) {
                timeInterval[i + start + 1] = 1;
            }
            timeInterval[(start + 1) % 24] = 2;
            timeInterval[(end + 1) % 24] = 3;
        }
        return timeInterval;
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
                //System.out.println(time);
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

    /**
     * Get cate
     * @return
     */
    private String getCate(String s) {
        switch (s) {
        case "a":
            return "Academic ";
        case "c":
            return "CCA      ";
        case "e":
            return "Entertain";
        case "r":
            return "Errands  ";
        default:
            return "Other    ";
        }
    }

    public VBox getOneTimeline() {
        return oneTimeline;
    }
}

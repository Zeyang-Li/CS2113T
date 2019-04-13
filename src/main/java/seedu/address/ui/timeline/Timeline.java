package seedu.address.ui.timeline;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Individual Timeline for each category.
 */
public class Timeline {
    private static final int GRIDSIZE = 100;
    private static final int GRIDHEIGHT = 5;
    private static final int TIMELINE_WIDTH = 50;
    private static final int TIMELINE_HEIGHT = 10;
    private static final double PREF_WIDTH = 850.0f;
    private static final float HEADING_HEIGHT = 1.0f;
    private static final float ARC_SIZE = 0.3f;

    private VBox oneTimeline = new VBox();
    private PreTask[] tasks;
    private String cate;
    private Text[] taskNames = {new Text("Cate"), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" "),
                                new Text(" "), new Text(" "), new Text(" "), new Text(" ")};

    public Timeline(PreTask[] pre, String c) {
        this.tasks = pre;
        this.cate = c;
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
        heading.setWidth(PREF_WIDTH);
        heading.setHeight(HEADING_HEIGHT);

        //Setting the height and width of the arc
        heading.setArcWidth(ARC_SIZE);
        heading.setArcHeight(ARC_SIZE);
        //Add to the view
        oneTimeline.getChildren().add(heading);
    }

    /**
     * This method will prepare the name of the task.
     * This is done by using putting task names into separated grids.
     */
    private void prepTitle(String c) {
        GridPane taskNameLine = new GridPane();
        taskNameLine.setPrefWidth(PREF_WIDTH);
        //taskNameLine.setGridLinesVisible(true);
        int col = 1;
        taskNames[0] = new Text(getCate(c));
        for (Text n : taskNames) {
            AnchorPane aGrid = new AnchorPane();
            aGrid.setPrefSize(GRIDSIZE, GRIDHEIGHT);
            //n.setBoundsType(TextBoundsType.VISUAL);
            n.setStyle("-fx-padding: 0 0 0 0;"
                       + "-fx-font-size: 14px;");
            n.setFill(Color.WHITE);
            n.setTextAlignment(TextAlignment.RIGHT);
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
        time.setPrefWidth(PREF_WIDTH);
        //time.setGridLinesVisible(true);
        int[] timeInterval = markTimeInterval(pre);

        for (int i = 0; i < timeInterval.length; i++) {
            //System.out.print(timeInterval[i]);
            switch (timeInterval[i]) {
            case 1:
                Region rect1 = new Region();
                rect1.setPrefSize(TIMELINE_WIDTH, TIMELINE_HEIGHT);
                time.add(setColor(rect1, timeInterval[i]), i, 0);
                break;
            case 2:
                Region rect2 = new Region();
                rect2.setPrefSize(TIMELINE_WIDTH, TIMELINE_HEIGHT);
                time.add(setColor(rect2, timeInterval[i]), i, 0);
                break;
            case 3:
                Region rect3 = new Region();
                rect3.setPrefSize(TIMELINE_WIDTH, TIMELINE_HEIGHT);
                time.add(setColor(rect3, timeInterval[i]), i, 0);
                break;
            case 4:
                Region rect4 = new Region();
                rect4.setPrefSize(TIMELINE_WIDTH, TIMELINE_HEIGHT);
                time.add(setColor(rect4, timeInterval[i]), i, 0);
                break;
            default:
                AnchorPane aGrid = new AnchorPane();
                aGrid.setPrefSize(TIMELINE_WIDTH, TIMELINE_HEIGHT);
                aGrid.getChildren().add(new Text(" "));
                time.add(aGrid, i, 0);
                break;
            }
        }
        oneTimeline.getChildren().add(time);
    }

    /**
     * Marks time intervals to fill in.
     * @return
     */
    private int[] markTimeInterval(PreTask[] pre) {
        int[] timeInterval = new int[24];
        int start = 0;
        int end = 0;
        for (PreTask t : pre) {
            try {
                t.getStart();
            } catch (NullPointerException np) {
                //System.out.println("null");
                return timeInterval;
            }
            start = (Math.round(t.getStart()) + 20) % 24;
            end = (Math.round(t.getEnd()) + 20) % 24;
            if ((start >= 20 && start <= 23) || (start >= 0 && start <= 1) || (end >= 22 && end <= 23)) {
                continue;
            }
            if ((end - start) <= 1) {
                timeInterval[start] = 4;
                continue;
            }
            for (int i = 0; i < end - start; i++) {
                timeInterval[i + start + 1] = 1;
            }
            timeInterval[(start) % 24] = 2;
            timeInterval[(end) % 24] = 3;
        }
        return timeInterval;
    }

    /**
     * Sort the tasks.
     * The sorting function could be improved to quicksork / mergesort etc.
     */
    private void sort() {
        int total = getSize();
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
            if (time > 19) {
                //System.out.println(time);
                continue;
            }
            taskNames[(Math.round(tasks[i].getStart()) + 20) % 24] = new Text(tasks[i].getTitle());
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

    /**
     * Set the color of each timeline to be different.
     * @param r
     * @return
     */
    private Region setColor(Region r, int i) {
        switch (cate) {
        case "a":
            switch (i) {
            case 1:
                r.setStyle("-fx-background-color: #39b9fd; -fx-background-radius: 0 0 0 0");
                return r;
            case 2:
                r.setStyle("-fx-background-color: #39b9fd; -fx-background-radius: 10 0 0 10");
                return r;
            case 3:
                r.setStyle("-fx-background-color: #39b9fd; -fx-background-radius: 0 10 10 0");
                return r;
            case 4:
                r.setStyle("-fx-background-color: #39b9fd; -fx-background-radius: 10 10 10 10");
                return r;
            default:
                r.setStyle("-fx-background-color: #39b9fd; -fx-background-radius: 0 0 0 0");
                return r;
            }
        case "c":
            switch (i) {
            case 1:
                r.setStyle("-fx-background-color: #3bd979; -fx-background-radius: 0 0 0 0");
                return r;
            case 2:
                r.setStyle("-fx-background-color: #3bd979; -fx-background-radius: 10 0 0 10");
                return r;
            case 3:
                r.setStyle("-fx-background-color: #3bd979; -fx-background-radius: 0 10 10 0");
                return r;
            case 4:
                r.setStyle("-fx-background-color: #3bd979; -fx-background-radius: 10 10 10 10");
                return r;
            default:
                r.setStyle("-fx-background-color: #3bd979; -fx-background-radius: 0 0 0 0");
                return r;
            }
        case "e":
            switch (i) {
            case 1:
                r.setStyle("-fx-background-color: #fc4429; -fx-background-radius: 0 0 0 0");
                return r;
            case 2:
                r.setStyle("-fx-background-color: #fc4429; -fx-background-radius: 10 0 0 10");
                return r;
            case 3:
                r.setStyle("-fx-background-color: #fc4429; -fx-background-radius: 0 10 10 0");
                return r;
            case 4:
                r.setStyle("-fx-background-color: #fc4429; -fx-background-radius: 10 10 10 10");
                return r;
            default:
                r.setStyle("-fx-background-color: #fc4429; -fx-background-radius: 0 0 0 0");
                return r;
            }
        case "r":
            switch (i) {
            case 1:
                r.setStyle("-fx-background-color: #ffa741; -fx-background-radius: 0 0 0 0");
                return r;
            case 2:
                r.setStyle("-fx-background-color: #ffa741; -fx-background-radius: 10 0 0 10");
                return r;
            case 3:
                r.setStyle("-fx-background-color: #ffa741; -fx-background-radius: 0 10 10 0");
                return r;
            case 4:
                r.setStyle("-fx-background-color: #ffa741; -fx-background-radius: 10 10 10 10");
                return r;
            default:
                r.setStyle("-fx-background-color: #ffa741; -fx-background-radius: 0 0 0 0");
                return r;
            }
        case "o":
            switch (i) {
            case 1:
                r.setStyle("-fx-background-color: #ded38c; -fx-background-radius: 0 0 0 0");
                return r;
            case 2:
                r.setStyle("-fx-background-color: #ded38c; -fx-background-radius: 10 0 0 10");
                return r;
            case 3:
                r.setStyle("-fx-background-color: #ded38c; -fx-background-radius: 0 10 10 0");
                return r;
            case 4:
                r.setStyle("-fx-background-color: #ded38c; -fx-background-radius: 10 10 10 10");
                return r;
            default:
                r.setStyle("-fx-background-color: #ded38c; -fx-background-radius: 0 0 0 0");
                return r;
            }
        default:
            switch (i) {
            case 1:
                r.setStyle("-fx-background-color: white; -fx-background-radius: 0 0 0 0");
                return r;
            case 2:
                r.setStyle("-fx-background-color: white; -fx-background-radius: 10 0 0 10");
                return r;
            case 3:
                r.setStyle("-fx-background-color: white; -fx-background-radius: 0 10 10 0");
                return r;
            case 4:
                r.setStyle("-fx-background-color: white; -fx-background-radius: 10 10 10 10");
                return r;
            default:
                r.setStyle("-fx-background-color: white; -fx-background-radius: 0 0 0 0");
                return r;
            }
        }
    }
    public VBox getOneTimeline() {
        return oneTimeline;
    }
}

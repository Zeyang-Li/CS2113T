package seedu.address.ui.timeline;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This is the overall arrangement of timeline pane.
 */
public class TimePane extends AnchorPane {
    VBox vbox = new VBox();

    public TimePane() {
        vbox.setSpacing(10);


        Text[] timePoints = { new Text("6:00"),
                new Text("8:00"), new Text("10:00"),
                new Text("12:00"), new Text("14:00"),
                new Text("16:00"), new Text("18:00"),
                new Text("20:00"), new Text("22:00"),
                new Text("0:00"), new Text("2:00")};

        GridPane timelineLable = new GridPane();
        timelineLable.setPrefWidth(800);

        int col = 1;
        for (Text time : timePoints) {
            AnchorPane aGrid = new AnchorPane();
            aGrid.setPrefSize(70, 20);
            aGrid.getChildren().add(time);
            aGrid.setStyle("-fx-text-inner-color: red;");
            timelineLable.add(aGrid, col, 0);
            col++;
        }
        //---------------------------------------
        Timeline tl1 = new Timeline(150.0f,730.0f);

        vbox.getChildren().add(tl1.getOneTimeline());
        vbox.getChildren().add(timelineLable);

    }

    public VBox getView() {
        return vbox;
    }
}

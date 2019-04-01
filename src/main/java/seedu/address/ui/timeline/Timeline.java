package seedu.address.ui.timeline;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Individual Timeline.
 */
public class Timeline {
    VBox oneTimeline = new VBox();
    
    public Timeline(float start, float length) {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.WHITESMOKE);

        //Setting the properties of the rectangle 
        rectangle.setX(150.0f);
        rectangle.setY(75.0f);
        rectangle.setWidth(length);
        rectangle.setHeight(10.0f);

        //Setting the height and width of the arc 
        rectangle.setArcWidth(10.0);
        rectangle.setArcHeight(10.0);
        oneTimeline.getChildren().add(rectangle);
    }
    
    public VBox getOneTimeline() {
        return oneTimeline;
    }
}

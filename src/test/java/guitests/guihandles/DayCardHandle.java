package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.day.Day;

/**
 * Provides a handle to a day card in the day list panel.
 */
public class DayCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DATE_FIELD_ID = "#date";
    private static final String ACADEMIC_FIELD_ID = "#academic";
    private static final String CCA_FIELD_ID = "#cca";
    private static final String ENTERTAINMENT_FIELD_ID = "#entertainment";
    private static final String ERRAND_FIELD_ID = "#errand";
    private static final String OTHER_FIELD_ID = "#other";

    private final Label idLabel;
    private final Label dateLabel;
    private final Label academicLabel;
    private final Label aName;
    private final Label ccaLabel;
    private final Label cName;
    private final Label entertainmentLabel;
    private final Label eName;
    private final Label errandLabel;
    private final Label errName;
    private final Label otherLabel;
    private final Label oName;

    public DayCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        academicLabel = getChildNode(ACADEMIC_FIELD_ID);
        ccaLabel = getChildNode(CCA_FIELD_ID);
        entertainmentLabel = getChildNode(ENTERTAINMENT_FIELD_ID);
        errandLabel = getChildNode(ERRAND_FIELD_ID);
        otherLabel = getChildNode(OTHER_FIELD_ID);
        aName = getChildNode("Academic: ");
        cName = getChildNode("Cca: ");
        eName = getChildNode("Entertainment: ");
        errName = getChildNode("Errand: ");
        oName = getChildNode("Other: ");
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getAcademic() {
        return academicLabel.getText();
    }

    public String getCca() {
        return ccaLabel.getText();
    }

    public String getEntertainment() {
        return entertainmentLabel.getText();
    }

    public String getErrand() {
        return errandLabel.getText();
    }

    public String getOther() {
        return otherLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code day}.
     */
    public boolean equals(Day day) {
        return getDate().equals(day.getDate().value)
                && getAcademic().equals(day.getAcademic().getTime())
                && getCca().equals(day.getCca().getTime())
                && getEntertainment().equals(day.getEntertainment().getTime())
                && getErrand().equals(day.getErrand().getTime())
                && getOther().equals(day.getOther().getTime());
    }
}

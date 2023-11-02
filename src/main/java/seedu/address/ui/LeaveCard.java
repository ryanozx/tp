package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.leave.Leave;

/**
 * An UI component that displays information of a {@code leave}.
 */
public class LeaveCard extends UiPart<Region> {

    private static final String FXML = "LeaveListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Leave leave;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label dateStart;
    @FXML
    private Label dateEnd;
    @FXML
    private FlowPane status;

    /**
     * Creates a {@code LeaveCode} with the given {@code Leave} and index to display.
     */
    public LeaveCard(Leave leave, int displayedIndex) {
        super(FXML);
        this.leave = leave;
        if (leave == null) {
            throw new IllegalArgumentException("Leave cannot be null.");
        }

        String statusType = leave.getStatus().toString();

        String styleClass;
        switch (statusType) {
        case "PENDING":
            styleClass = "status-pending";
            break;
        case "APPROVED":
            styleClass = "status-approved";
            break;
        case "REJECTED":
            styleClass = "status-rejected";
            break;
        default:
            styleClass = "";
        }

        id.setText(displayedIndex + ". ");
        title.setText(leave.getTitle().toString());
        name.setText("Employee: " + leave.getEmployee().toString());
        description.setText("Description:\n" + leave.getDescription());
        dateStart.setText("Date Start: " + leave.getStart().toString());
        dateEnd.setText("Date End: " + leave.getEnd().toString());
        status.getStyleClass().setAll(styleClass);
        status.getChildren().add(new Label(leave.getStatus().toString()));
    }
}

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
        name.setText(leave.getEmployee().getName().fullName);
        title.setText(leave.getTitle());
        description.setText(leave.getDescription());
        dateStart.setText(leave.getStart().toString());
        dateEnd.setText(leave.getEnd().toString());
        status.getChildren().add(new Label(leave.getStatus().toString()));
    }
}

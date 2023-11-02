package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class LeaveStatusBarFooter extends UiPart<Region> {

    private static final String FXML = "LeaveStatusBarFooter.fxml";

    @FXML
    private Label saveLeavesBookLocationStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public LeaveStatusBarFooter(Path saveLocation) {
        super(FXML);
        saveLeavesBookLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

}

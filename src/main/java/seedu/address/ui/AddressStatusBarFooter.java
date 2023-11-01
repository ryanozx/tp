package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;



/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class AddressStatusBarFooter extends UiPart<Region> {

    private static final String FXML = "AddressStatusBarFooter.fxml";

    @FXML
    private Label saveAddressBookLocationStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public AddressStatusBarFooter(Path saveLocation) {
        super(FXML);
        saveAddressBookLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

}

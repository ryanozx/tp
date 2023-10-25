package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class LeaveListPanel extends UiPart<Region> {
    private static final String FXML = "LeaveListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LeaveListPanel.class);

    @FXML
    private ListView<Leave> leaveListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public LeaveListPanel(ObservableList<Leave> leaveList) {
        super(FXML);
        leaveListView.setItems(leaveList);
        leaveListView.setCellFactory(listView -> new LeaveListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Leave} using a {@code LeaveCard}.
     */
    class LeaveListViewCell extends ListCell<Leave> {
        @Override
        protected void updateItem(Leave leave, boolean empty) {
            super.updateItem(leave, empty);

            if (empty || leave == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LeaveCard(leave, getIndex() + 1).getRoot());
            }
        }
    }

}

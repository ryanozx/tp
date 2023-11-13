package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_END;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_START;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_STATUS_APPROVED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_STATUS_REJECTED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_TITLE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Status;
import seedu.address.testutil.EditLeaveDescriptorBuilder;

public class EditLeaveDescriptorTest {

    private static EditLeaveDescriptor desc = new EditLeaveDescriptorBuilder().withTitle(VALID_LEAVE_TITLE)
        .withDescription(VALID_LEAVE_DESCRIPTION).withStart(Date.of(VALID_LEAVE_DATE_START))
        .withEnd(Date.of(VALID_LEAVE_DATE_END)).withStatus(Status.of(VALID_LEAVE_STATUS_APPROVED)).build();

    @Test
    public void equals() {
        EditLeaveDescriptor descriptorWithSameValues = new EditLeaveDescriptor(desc);
        assertTrue(desc.equals(descriptorWithSameValues));

        assertTrue(desc.equals(desc));

        assertFalse(desc.equals(null));

        assertFalse(desc.equals(5));

        EditLeaveDescriptor editDesc = new EditLeaveDescriptorBuilder(desc).withTitle("Other title").build();
        assertFalse(desc.equals(editDesc));

        editDesc = new EditLeaveDescriptorBuilder(desc).withDescription("Other description").build();
        assertFalse(desc.equals(editDesc));

        // check that start date and end date is not the same
        assertFalse(VALID_LEAVE_DATE_START.equals(VALID_LEAVE_DATE_END));

        editDesc = new EditLeaveDescriptorBuilder(desc).withStart(Date.of(VALID_LEAVE_DATE_END)).build();
        assertFalse(desc.equals(editDesc));

        editDesc = new EditLeaveDescriptorBuilder(desc).withEnd(Date.of(VALID_LEAVE_DATE_START)).build();
        assertFalse(desc.equals(editDesc));

        editDesc = new EditLeaveDescriptorBuilder(desc).withStatus(Status.of(VALID_LEAVE_STATUS_REJECTED)).build();
        assertFalse(desc.equals(editDesc));
    }

    @Test
    public void toStringMethod() {
        EditLeaveDescriptor editPersonDescriptor = new EditLeaveDescriptor();
        String expected = EditLeaveDescriptor.class.getCanonicalName() + "{title="
            + editPersonDescriptor.getTitle().orElse(null) + ", description="
            + editPersonDescriptor.getDescription().orElse(null) + ", start="
            + editPersonDescriptor.getStart().orElse(null) + ", end="
            + editPersonDescriptor.getEnd().orElse(null) + ", status="
            + editPersonDescriptor.getStatus().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}

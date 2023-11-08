package seedu.address.testutil;

import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Status.StatusType;
import seedu.address.model.leave.Title;

/**
 * A utility class to help with building EditLeaveDescriptor objects.
 */
public class EditLeaveDescriptorBuilder {

    private EditLeaveDescriptor descriptor;

    public EditLeaveDescriptorBuilder() {
        descriptor = new EditLeaveDescriptor();
    }

    public EditLeaveDescriptorBuilder(EditLeaveDescriptor descriptor) {
        this.descriptor = new EditLeaveDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLeaveDescriptor} with fields containing {@code person}'s details
     */
    public EditLeaveDescriptorBuilder(Leave leave) {
        descriptor = new EditLeaveDescriptor();
        descriptor.setTitle(leave.getTitle());
        descriptor.setStart(leave.getStart());
        descriptor.setEnd(leave.getEnd());
        descriptor.setStatus(leave.getStatus());
        descriptor.setDescription(leave.getDescription());
    }

    /**
     * Sets the {@code Title} of the {@code EditLeaveDescriptor} that we are building
     */
    public EditLeaveDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Start} of the {@code EditLeaveDescriptor} that we are building
     */
    public EditLeaveDescriptorBuilder withStart(Date start) {
        descriptor.setStart(start);
        return this;
    }
    /**
     * Sets the {@code End} of the {@code EditLeaveDescriptor} that we are building
     */
    public EditLeaveDescriptorBuilder withEnd(Date end) {
        descriptor.setEnd(end);
        return this;
    }


    /**
     * Sets the {@code Status} of the {@code EditLeaveDescriptor} that we are building
     */
    public EditLeaveDescriptorBuilder withStatus(Status status) {
        descriptor.setStatus(status);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditLeaveDescriptor} that we are building
     */
    public EditLeaveDescriptorBuilder withStatus(StatusType status) {
        descriptor.setStatus(Status.of(status));
        return this;
    }

    /**
     * Sets the {@code Start} of the {@code EditLeaveDescriptor} that we are building
     */
    public EditLeaveDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    public EditLeaveDescriptor build() {
        return descriptor;
    }
}

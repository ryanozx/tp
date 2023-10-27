package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.leave.Status.StatusType;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Status.of(null));
    }

    @Test
    public void constructor_validStatusType() {
        assertTrue(Status.of("PENDING").getStatus().equals(StatusType.PENDING));
        assertTrue(Status.getDefault().getStatus().equals(StatusType.PENDING));
        assertTrue(Status.of("APPROVED").getStatus().equals(StatusType.APPROVED));
        assertTrue(Status.of("REJECTED").getStatus().equals(StatusType.REJECTED));
    }

    @Test
    public void toStringMethod() {
        assertTrue(Status.of("PENDING").toString().equals("PENDING"));
        assertTrue(Status.of("APPROVED").toString().equals("APPROVED"));
        assertTrue(Status.of("REJECTED").toString().equals("REJECTED"));
    }

    @Test
    public void equalsMethod() {
        // Status status = new Status(StatusType.PENDING);
        Status status = Status.getDefault();

        // same values -> returns true
        assertTrue(status.equals(Status.of("PENDING")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // null -> returns false
        assertFalse(status.equals(null));

        // different types -> returns false
        assertFalse(status.equals(5.0f));

        // different values -> returns false
        assertFalse(status.equals(Status.of("APPROVED")));
        assertFalse(status.equals(Status.of("REJECTED")));
    }

    @Test
    public void hashcodeMethod() {
        Status status = Status.getDefault();

        // same values -> returns true
        assertTrue(status.hashCode() == Status.of("PENDING").hashCode());

        // different values -> returns false
        assertFalse(status.hashCode() == Status.of("APPROVED").hashCode());
        assertFalse(status.hashCode() == Status.of("REJECTED").hashCode());
    }
}

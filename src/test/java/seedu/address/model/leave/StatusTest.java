package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.leave.Status.StatusType;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Status.of((StatusType) null));
    }

    @Test
    public void constructor_validStatusType() {
        assertEquals(Status.of("PENDING").getStatusType(), StatusType.PENDING);
        assertEquals(Status.getDefault().getStatusType(), StatusType.PENDING);
        assertEquals(Status.of("APPROVED").getStatusType(), StatusType.APPROVED);
        assertEquals(Status.of("REJECTED").getStatusType(), StatusType.REJECTED);
        assertEquals(Status.of(StatusType.APPROVED).getStatusType(), StatusType.APPROVED);
        assertEquals(Status.of(StatusType.PENDING).getStatusType(), StatusType.PENDING);
        assertEquals(Status.of(StatusType.REJECTED).getStatusType(), StatusType.REJECTED);
    }

    @Test
    public void toStringMethod() {
        assertEquals("PENDING", Status.of("PENDING").toString());
        assertEquals("APPROVED", Status.of("APPROVED").toString());
        assertEquals("REJECTED", Status.of("REJECTED").toString());
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
    public void isValidStatus() {
        assertTrue(Status.isValidStatus("APPROVED"));
        assertTrue(Status.isValidStatus("PENDING"));
        assertTrue(Status.isValidStatus("REJECTED"));

        assertFalse(Status.isValidStatus(""));
        assertFalse(Status.isValidStatus(" "));
        assertFalse(Status.isValidStatus("INVALID"));
        assertFalse(Status.isValidStatus("approved"));
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

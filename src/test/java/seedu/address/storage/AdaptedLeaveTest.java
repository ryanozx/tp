package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.AdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Title;

public class AdaptedLeaveTest {
    private static final String VALID_START = ALICE_LEAVE.getStart().toString();
    private static final String VALID_END = ALICE_LEAVE.getEnd().toString();
    private static final String VALID_TITLE = ALICE_LEAVE.getTitle().toString();
    private static final String VALID_DESCRIPTION = ALICE_LEAVE.getDescription().toString();
    private static final String EMPTY_DESCRIPTION = "";
    private static final String VALID_STATUS = ALICE_LEAVE.getStatus().toString();
    private static final String VALID_EMPLOYEE = ALICE_LEAVE.getEmployee().getName().toString();
    private static final Leave VALID_LEAVE = ALICE_LEAVE;

    private static final String INVALID_START = "2020/01/01";
    private static final String INVALID_END = "2020/01/01";
    private static final String EMPTY_TITLE = "";
    private static final String INVALID_TITLE = "title*#";
    private static final String INVALID_STATUS = "invalid";
    private static final String INVALID_DESCRIPTION = "description*#";
    private static final String INVALID_EMPLOYEE = "R@chel";

    static class MockAdaptedLeave extends AdaptedLeave {
        public MockAdaptedLeave(String start, String end, String title, String description,
                                 String status, String employee) {
            super(start, end, title, description, status, employee);
        }

        public MockAdaptedLeave(Leave source) {
            super(source);
        }
    }

    @Test
    public void constructor_nullLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MockAdaptedLeave(null));
    }

    @Test
    public void constructor_nullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MockAdaptedLeave(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MockAdaptedLeave(
                VALID_START, VALID_END, VALID_TITLE, VALID_DESCRIPTION, VALID_STATUS, INVALID_EMPLOYEE));
    }

    @Test
    public void toModelType_leaveParameterConstructor_returnsLeave() throws Exception {
        AdaptedLeave leave = new MockAdaptedLeave(ALICE_LEAVE);
        assertEquals(VALID_LEAVE, leave.toModelType());
    }

    @Test
    public void toModelType_stringParameterConstructor_returnsLeave() throws Exception {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, VALID_TITLE,
                VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        assertEquals(ALICE_LEAVE, leave.toModelType());
    }

    @Test
    public void toModelType_emptyDescription_returnsLeave() throws Exception {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, VALID_TITLE,
                EMPTY_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        assertTrue(VALID_LEAVE.isSameLeave(leave.toModelType()));
    }

    @Test
    public void toModelType_invalidStart_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(INVALID_START, VALID_END, VALID_TITLE,
                        VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_nullStart_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(null, VALID_END, VALID_TITLE,
                VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "start");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidEnd_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, INVALID_END, VALID_TITLE,
                VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_nullEnd_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, null, VALID_TITLE,
                VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "end");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_endBeforeStart_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_END, VALID_START, VALID_TITLE,
                VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Range.MESSAGE_END_BEFORE_START_ERROR;
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, INVALID_TITLE,
                VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_emptyTitle_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, EMPTY_TITLE,
                VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, null,
                VALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "title");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, VALID_TITLE,
                INVALID_DESCRIPTION, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, VALID_TITLE,
                null, VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "description");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, VALID_TITLE,
                VALID_DESCRIPTION, INVALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        AdaptedLeave leave = new MockAdaptedLeave(VALID_START, VALID_END, VALID_TITLE,
                VALID_DESCRIPTION, null, VALID_EMPLOYEE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "status");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }
}

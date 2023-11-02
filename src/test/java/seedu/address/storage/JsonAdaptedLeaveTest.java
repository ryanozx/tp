package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Title;
import seedu.address.storage.JsonAdaptedLeave.Employee;
import seedu.address.storage.JsonAdaptedLeave.Name;

public class JsonAdaptedLeaveTest {

    private static final String VALID_START = ALICE_LEAVE.getStart().toString();
    private static final String VALID_END = ALICE_LEAVE.getEnd().toString();
    private static final String VALID_TITLE = ALICE_LEAVE.getTitle().toString();
    private static final String VALID_DESCRIPTION = ALICE_LEAVE.getDescription().toString();
    private static final String EMPTY_DESCRIPTION = "";
    private static final String VALID_STATUS = ALICE_LEAVE.getStatus().toString();
    private static final Employee VALID_EMPLOYEE = new Employee(new Name(ALICE.getName().toString()));
    private static final Leave VALID_LEAVE = ALICE_LEAVE;

    private static final String INVALID_START = "2020/01/01";
    private static final String INVALID_END = "2020/01/01";
    private static final String EMPTY_TITLE = "";
    private static final String INVALID_TITLE = "title*#";
    private static final String INVALID_STATUS = "invalid";
    private static final String INVALID_DESCRIPTION = "description*#";


    @Test
    public void toModelType_createsEqualLeaveObject_success() throws Exception {
        JsonAdaptedLeave leave = new JsonAdaptedLeave(VALID_LEAVE);
        assertEquals(VALID_LEAVE, leave.toModelType());

        JsonAdaptedLeave leave2 = JsonAdaptedLeave.of(VALID_START, VALID_END, VALID_TITLE, VALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        assertEquals(VALID_LEAVE, leave2.toModelType());
    }

    @Test
    public void toModelType_nullStart_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(null, VALID_END, VALID_TITLE, VALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "start");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidStart_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(INVALID_START, VALID_END, VALID_TITLE, VALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        assertThrows(IllegalValueException.class, leave::toModelType);
    }

    @Test
    public void toModelType_nullEnd_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, null, VALID_TITLE, VALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "end");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidEnd_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, INVALID_END, VALID_TITLE, VALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        assertThrows(IllegalValueException.class, leave::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, VALID_END, null, VALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "title");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_emptyTitle_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, VALID_END, EMPTY_TITLE, VALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        assertThrows(IllegalValueException.class, Title.MESSAGE_CONSTRAINTS, leave::toModelType);
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, VALID_END, INVALID_TITLE, VALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        assertThrows(IllegalValueException.class, Title.MESSAGE_CONSTRAINTS, leave::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, VALID_END, VALID_TITLE, null,
                VALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "description");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_emptyDescription_success() throws Exception {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, VALID_END, VALID_TITLE, EMPTY_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        assertTrue(VALID_LEAVE.isSameLeave(leave.toModelType()));
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, VALID_END, VALID_TITLE, INVALID_DESCRIPTION,
                VALID_STATUS, VALID_EMPLOYEE);
        assertThrows(IllegalValueException.class, Description.MESSAGE_CONSTRAINTS, leave::toModelType);
    }

    @Test
    public void toModelType_nullEmployee_throwsIllegalValueException() {
        assertThrows(NullPointerException.class, () -> JsonAdaptedLeave.of(VALID_START, VALID_END, VALID_TITLE,
                VALID_DESCRIPTION, VALID_STATUS, null));
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, VALID_END, VALID_TITLE, VALID_DESCRIPTION,
                null, VALID_EMPLOYEE);
        String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "status");
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedLeave leave = JsonAdaptedLeave.of(VALID_START, VALID_END, VALID_TITLE, VALID_DESCRIPTION,
                INVALID_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }
}

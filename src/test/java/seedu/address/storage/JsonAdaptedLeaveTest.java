package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Status;

public class JsonAdaptedLeaveTest {
    
    // @Test
    // public void toModelType_leaveParameterConstructor_returnsLeave() throws Exception {
    //     Leave leave = new JsonAdaptedLeave(ALICE_LEAVE).toModelType();
    //     assertEquals(ALICE_LEAVE, leave);
    // }

    // @Test
    // public void toModelType_stringParameterConstructor_returnsLeave() throws Exception {
    //     JsonAdaptedPerson employee = new JsonAdaptedPerson(ALICE_LEAVE.getEmployee());
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave(ALICE_LEAVE.getStart().toString(),
    //             ALICE_LEAVE.getEnd().toString(), ALICE_LEAVE.getTitle(), ALICE_LEAVE.getDescription(),
    //             ALICE_LEAVE.getStatus(), employee);
    //     assertEquals(ALICE_LEAVE, leave.toModelType());
    // }

    // @Test
    // public void toModelType_nullPerson_throwsIllegalValueException() {
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave(ALICE_LEAVE.getStart().toString(),
    //             ALICE_LEAVE.getEnd().toString(), ALICE_LEAVE.getTitle(), ALICE_LEAVE.getDescription(),
    //             ALICE_LEAVE.getStatus(), null);
    //     String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "employee");
    //     assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    // }

    // @Test
    // public void toModelType_nullStart_throwsIllegalValueException() {
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave(null, ALICE_LEAVE.getEnd().toString(),
    //             ALICE_LEAVE.getTitle(), ALICE_LEAVE.getDescription(), ALICE_LEAVE.getStatus(),
    //             new JsonAdaptedPerson(ALICE_LEAVE.getEmployee()));
    //     String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "start");
    //     assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    // }

    // @Test
    // public void toModelType_invalidStart_throwsIllegalValueException() {
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave("2020/01/01", ALICE_LEAVE.getEnd().toString(),
    //             ALICE_LEAVE.getTitle(), ALICE_LEAVE.getDescription(), ALICE_LEAVE.getStatus(),
    //             new JsonAdaptedPerson(ALICE_LEAVE.getEmployee()));
    //     assertThrows(DateTimeParseException.class, leave::toModelType);
    // }

    // @Test
    // public void toModelType_nullEnd_throwsIllegalValueException() {
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave(ALICE_LEAVE.getStart().toString(), null,
    //             ALICE_LEAVE.getTitle(), ALICE_LEAVE.getDescription(), ALICE_LEAVE.getStatus(),
    //             new JsonAdaptedPerson(ALICE_LEAVE.getEmployee()));
    //     String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "end");
    //     assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    // }

    // @Test
    // public void toModelType_invalidEnd_throwsIllegalValueException() {
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave(ALICE_LEAVE.getStart().toString(), "2020/01/01",
    //             ALICE_LEAVE.getTitle(), ALICE_LEAVE.getDescription(), ALICE_LEAVE.getStatus(),
    //             new JsonAdaptedPerson(ALICE_LEAVE.getEmployee()));
    //     assertThrows(DateTimeParseException.class, leave::toModelType);
    // }

    // @Test
    // public void toModelType_nullTitle_throwsIllegalValueException() {
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave(ALICE_LEAVE.getStart().toString(),
    //             ALICE_LEAVE.getEnd().toString(), null, ALICE_LEAVE.getDescription(), ALICE_LEAVE.getStatus(),
    //             new JsonAdaptedPerson(ALICE_LEAVE.getEmployee()));
    //     String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "title");
    //     assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    // }

    // @Test
    // public void toModelType_nullStatus_throwsIllegalValueException() {
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave(ALICE_LEAVE.getStart().toString(),
    //             ALICE_LEAVE.getEnd().toString(), ALICE_LEAVE.getTitle(), ALICE_LEAVE.getDescription(), null,
    //             new JsonAdaptedPerson(ALICE_LEAVE.getEmployee()));
    //     String expectedMessage = String.format(JsonAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT, "status");
    //     assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    // }

    // @Test
    // public void toModelType_invalidStatus_throwsIllegalValueException() {
    //     JsonAdaptedLeave leave = new JsonAdaptedLeave(ALICE_LEAVE.getStart().toString(),
    //             ALICE_LEAVE.getEnd().toString(), ALICE_LEAVE.getTitle(), ALICE_LEAVE.getDescription(), "invalid",
    //             new JsonAdaptedPerson(ALICE_LEAVE.getEmployee()));
    //     String expectedMessage = Status.MESSAGE_CONSTRAINTS;
    //     assertThrows(IllegalArgumentException.class, expectedMessage, leave::toModelType);
    // }
}

package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.leave.exceptions.EndBeforeStartException;

public class RangeTest {
    private static final Date FIRST_DATE = Date.of("2020-10-30");
    private static final Date SECOND_DATE = Date.of("2020-10-31");
    @Test
    public void createNonNullRange_invalidLeave_throwsEndBeforeStartException() {
        assertThrows(EndBeforeStartException.class, () -> Range.createNonNullRange(
                SECOND_DATE, FIRST_DATE));
    }

    @Test
    public void createNonNullRange_nullStartOrEnd_throwsNullPointerException() {
        // null start date
        assertThrows(NullPointerException.class, () -> Range.createNonNullRange(null, SECOND_DATE));
        // null end date
        assertThrows(NullPointerException.class, () -> Range.createNonNullRange(FIRST_DATE, null));
    }

    @Test
    public void createNonNullRange_validLeave_returnsRange() {
        // start date before end date
        Range expectedDiffRange = Range.createNonNullRange(FIRST_DATE, SECOND_DATE);
        assertEquals(expectedDiffRange.getStartDate().get(), FIRST_DATE);
        assertEquals(expectedDiffRange.getEndDate().get(), SECOND_DATE);

        // start date same as end date
        Range expectedSameRange = Range.createNonNullRange(FIRST_DATE, FIRST_DATE);
        assertEquals(expectedSameRange.getStartDate().get(), FIRST_DATE);
        assertEquals(expectedSameRange.getEndDate().get(), FIRST_DATE);
    }

    @Test
    public void createNullableRange_invalidLeave_throwsEndBeforeStartException() {
        assertThrows(EndBeforeStartException.class, () -> Range.createNullableRange(
                SECOND_DATE, FIRST_DATE));
    }

    @Test
    public void createNullableRange_validLeave_returnsRange() {
        // start date before end date
        Range expectedDiffRange = Range.createNullableRange(FIRST_DATE, SECOND_DATE);
        assertEquals(expectedDiffRange.getStartDate().get(), FIRST_DATE);
        assertEquals(expectedDiffRange.getEndDate().get(), SECOND_DATE);

        // start date same as end date
        Range expectedSameRange = Range.createNullableRange(FIRST_DATE, FIRST_DATE);
        assertEquals(expectedSameRange.getStartDate().get(), FIRST_DATE);
        assertEquals(expectedSameRange.getEndDate().get(), FIRST_DATE);

        // start date provided, null end date
        Range expectedStartRange = Range.createNullableRange(FIRST_DATE, null);
        assertEquals(expectedStartRange.getStartDate().get(), FIRST_DATE);
        assertFalse(expectedStartRange.getEndDate().isPresent());

        // end date provided, start date null
        Range expectedEndRange = Range.createNullableRange(null, SECOND_DATE);
        assertFalse(expectedEndRange.getStartDate().isPresent());
        assertEquals(expectedEndRange.getEndDate().get(), SECOND_DATE);

        // no start and end date provided
        Range expectedNullRange = Range.createNullableRange(null, null);
        assertFalse(expectedNullRange.getStartDate().isPresent());
        assertFalse(expectedNullRange.getEndDate().isPresent());
    }

    @Test
    public void equals() {
        Range defaultRange = Range.createNullableRange(FIRST_DATE, SECOND_DATE);
        Range diffStart = Range.createNullableRange(SECOND_DATE, SECOND_DATE);
        Range diffEnd = Range.createNullableRange(FIRST_DATE, FIRST_DATE);

        // same object -> return true
        assertEquals(defaultRange, defaultRange);
        // diff types -> return false
        assertFalse(defaultRange.equals("1"));

        // same start and end -> return true
        Range defaultRangeCopy = Range.createNullableRange(FIRST_DATE, SECOND_DATE);
        assertEquals(defaultRange, defaultRangeCopy);
        // diff start -> return false
        assertNotEquals(defaultRange, diffStart);
        // diff end -> return false
        assertNotEquals(defaultRange, diffEnd);

        Range nullStart = Range.createNullableRange(null, SECOND_DATE);
        Range nullStartDiffEnd = Range.createNullableRange(null, FIRST_DATE);
        // null start vs non-null start -> return false
        assertNotEquals(defaultRange, nullStart);
        // null start, same end -> return true
        Range nullStartCopy = Range.createNullableRange(null, SECOND_DATE);
        assertEquals(nullStart, nullStartCopy);
        // null start, diff end -> return false
        assertNotEquals(nullStart, nullStartDiffEnd);

        Range nullEnd = Range.createNullableRange(FIRST_DATE, null);
        Range nullEndDiffStart = Range.createNullableRange(SECOND_DATE, null);
        // null end vs non-null end -> return false
        assertNotEquals(defaultRange, nullEnd);
        // null end, same start -> return true
        Range nullEndCopy = Range.createNullableRange(FIRST_DATE, null);
        assertEquals(nullEnd, nullEndCopy);
        // null end, diff start -> return false
        assertNotEquals(nullEnd, nullEndDiffStart);

        Range nullRange = Range.createNullableRange(null, null);
        // null end vs non-null end -> return false
        assertNotEquals(nullRange, nullStart);
        // null start vs non-null start -> return false
        assertNotEquals(nullRange, nullEnd);
        // null start and null end -> return true
        Range nullRangeCopy = Range.createNullableRange(null, null);
        assertEquals(nullRange, nullRangeCopy);
    }
}

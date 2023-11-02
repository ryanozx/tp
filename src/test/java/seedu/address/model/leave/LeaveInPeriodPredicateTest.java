package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.leave.exceptions.EndBeforeStartException;
import seedu.address.testutil.LeaveBuilder;


public class LeaveInPeriodPredicateTest {
    private static final Date FIRST_DATE = Date.of("2023-10-20");
    private static final Date SECOND_DATE = Date.of("2023-10-27");
    private static final Date THIRD_DATE = Date.of("2023-11-03");
    private static final Date FOURTH_DATE = Date.of("2023-11-10");

    private static final Date FIFTH_DATE = Date.of("2023-11-17");
    private static final Date SIXTH_DATE = Date.of("2023-11-24");

    @Test
    public void equals_startAndEndDateNonNull() {
        LeaveInPeriodPredicate firstPredicate = generatePredicate(FIRST_DATE, SECOND_DATE);
        LeaveInPeriodPredicate secondPredicate = generatePredicate(FIRST_DATE, THIRD_DATE);
        LeaveInPeriodPredicate thirdPredicate = generatePredicate(SECOND_DATE, THIRD_DATE);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // different types -> returns false
        assertFalse(firstPredicate.equals("1"));

        // same start and end date -> returns true
        LeaveInPeriodPredicate firstPredicateCopy = generatePredicate(FIRST_DATE, SECOND_DATE);
        assertEquals(firstPredicate, firstPredicateCopy);

        // same start, different end -> returns false
        assertNotEquals(secondPredicate, firstPredicate);

        // different start, same end -> returns false
        assertNotEquals(thirdPredicate, secondPredicate);
    }

    /**
     * Generates a LeaveInPeriodPredicate that can take in null start and end dates
     * @param startDate Start date of period / null
     * @param endDate End date of period / null
     * @return LeaveInPeriodPredicate containing specified date range
     */
    public static LeaveInPeriodPredicate generatePredicate(Date startDate, Date endDate) {
        Range dateRange = Range.createNullableRange(startDate, endDate);
        return new LeaveInPeriodPredicate(dateRange);
    }

    @Test
    public void equals_startNull() {
        LeaveInPeriodPredicate defaultPredicate = generatePredicate(null, SECOND_DATE);
        LeaveInPeriodPredicate nonNullStartPredicate = generatePredicate(FIRST_DATE, SECOND_DATE);
        LeaveInPeriodPredicate diffEndPredicate = generatePredicate(null, FIRST_DATE);

        // same end date -> returns true
        LeaveInPeriodPredicate defaultPredicateCopy = generatePredicate(null, SECOND_DATE);
        assertEquals(defaultPredicate, defaultPredicateCopy);

        // compare with non-null start date -> returns false
        assertNotEquals(nonNullStartPredicate, defaultPredicate);

        // diff end date -> returns false
        assertNotEquals(diffEndPredicate, defaultPredicate);
    }

    @Test
    public void equals_endNull() {
        LeaveInPeriodPredicate defaultPredicate = generatePredicate(FIRST_DATE, null);
        LeaveInPeriodPredicate nonNullEndPredicate = generatePredicate(FIRST_DATE, SECOND_DATE);
        LeaveInPeriodPredicate diffStartPredicate = generatePredicate(SECOND_DATE, null);

        // same start date -> returns true
        LeaveInPeriodPredicate defaultPredicateCopy = generatePredicate(FIRST_DATE, null);
        assertEquals(defaultPredicate, defaultPredicateCopy);

        // compare with non-null end date -> returns false
        assertNotEquals(nonNullEndPredicate, defaultPredicate);

        // diff start date -> returns false
        assertNotEquals(diffStartPredicate, defaultPredicate);
    }

    @Test
    public void equals_startAndEndNull() {
        LeaveInPeriodPredicate defaultPredicate = generatePredicate(null, null);
        LeaveInPeriodPredicate nonNullStartPredicate = generatePredicate(FIRST_DATE, null);
        LeaveInPeriodPredicate nonNullEndPredicate = generatePredicate(null, SECOND_DATE);

        // start and end both null -> returns true
        LeaveInPeriodPredicate defaultPredicateCopy = generatePredicate(null, null);
        assertEquals(defaultPredicate, defaultPredicateCopy);

        // compare with non-null start date -> returns false
        assertNotEquals(nonNullStartPredicate, defaultPredicate);

        // compare with non-null end date -> returns false
        assertNotEquals(nonNullEndPredicate, defaultPredicate);
    }

    @Test
    public void constructor_validInputs_doesNotThrow() {
        // no dates supplied
        assertDoesNotThrow(() -> generatePredicate(null, null));
        // only start date supplied
        assertDoesNotThrow(() -> generatePredicate(FIRST_DATE, null));
        // only end date supplied
        assertDoesNotThrow(() -> generatePredicate(null, SIXTH_DATE));
        // start and end date supplied, start before end
        assertDoesNotThrow(() -> generatePredicate(FIRST_DATE, SIXTH_DATE));
        // start and end date supplied, start equals end
        assertDoesNotThrow(() -> generatePredicate(FIRST_DATE, FIRST_DATE));
    }

    @Test
    public void constructor_endBeforeStart_throwsException() {
        assertThrows(EndBeforeStartException.class, () ->
                generatePredicate(SECOND_DATE, FIRST_DATE));
    }

    @Test
    public void test_noStartAndEndDate_returnsTrue() {
        LeaveInPeriodPredicate predicate = generatePredicate(null, null);
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(SECOND_DATE)
                .build()));
    }

    @Test
    public void test_hasStartAndEndDate_returnsTrue() {
        LeaveInPeriodPredicate predicate = generatePredicate(
                SECOND_DATE, FIFTH_DATE);

        // start before period, end within period -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(FOURTH_DATE)
                .build()));
        // start before period, end date is same -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(FIFTH_DATE)
                .build()));
        // start before period, end after period -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(SIXTH_DATE)
                .build()));

        // start date is same, end date within range -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(SECOND_DATE).withEnd(FOURTH_DATE)
                .build()));
        // start date is same, end date is same -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(SECOND_DATE).withEnd(FIFTH_DATE)
                .build()));
        // start date is same, end after period -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(SECOND_DATE).withEnd(SIXTH_DATE)
                .build()));

        // start within range, end within range -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(THIRD_DATE).withEnd(FOURTH_DATE)
                .build()));
        // start date within range, end date is same -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(THIRD_DATE).withEnd(FIFTH_DATE)
                .build()));
        // start within period, end after period -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(THIRD_DATE).withEnd(SIXTH_DATE)
                .build()));

        // Boundary tests - we only require an intersection of at least one day
        // start before period, end date same as query start date -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(SECOND_DATE)
                .build()));
        // start and end date and query start date are the same -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(SECOND_DATE).withEnd(SECOND_DATE)
                .build()));
        // start date same as query end date, end after period -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIFTH_DATE).withEnd(SIXTH_DATE)
                .build()));
        // start and end and query end date are the same -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIFTH_DATE).withEnd(FIFTH_DATE)
                .build()));
    }

    @Test
    public void test_hasStartAndEndDate_returnsFalse() {
        LeaveInPeriodPredicate latePredicate = generatePredicate(THIRD_DATE, SIXTH_DATE);
        // end date before query start date -> return false
        assertFalse(latePredicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(SECOND_DATE)
                .build()));

        LeaveInPeriodPredicate earlyPredicate = generatePredicate(FIRST_DATE, FOURTH_DATE);
        // start date after query end date -> returns false
        assertFalse(earlyPredicate.test(new LeaveBuilder()
                .withStart(FIFTH_DATE).withEnd(SIXTH_DATE)
                .build()));
    }

    @Test
    public void test_hasStartDate_returnsTrue() {
        LeaveInPeriodPredicate predicate = generatePredicate(THIRD_DATE, null);

        // start date before query start, end date on query start -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(THIRD_DATE)
                .build()));
        // start date before query start, end date after query start -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(FOURTH_DATE)
                .build()));
        // start date on query start, end date after query start -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(THIRD_DATE).withEnd(SIXTH_DATE)
                .build()));
        // start date after query start -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FOURTH_DATE).withEnd(SIXTH_DATE)
                .build()));

        // Boundary test
        // start date and end date same as query start date -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(THIRD_DATE).withEnd(THIRD_DATE)
                .build()));
    }

    @Test
    public void test_hasStartDate_returnsFalse() {
        LeaveInPeriodPredicate predicate = generatePredicate(THIRD_DATE, null);

        // end date is before query start -> returns false
        assertFalse(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(SECOND_DATE)
                .build()));
    }

    @Test
    public void test_hasEndDate_returnsTrue() {
        LeaveInPeriodPredicate predicate = generatePredicate(null, FOURTH_DATE);

        // end date before query end -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(THIRD_DATE)
                .build()));
        // start date before query end, end date same as query end -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(FOURTH_DATE)
                .build()));
        // start date before query end, end date after query end -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FIRST_DATE).withEnd(FIFTH_DATE)
                .build()));
        // start date on query end, end date after query end -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FOURTH_DATE).withEnd(SIXTH_DATE)
                .build()));

        // Boundary test
        // start date and end date and query end are the same -> returns true
        assertTrue(predicate.test(new LeaveBuilder()
                .withStart(FOURTH_DATE).withEnd(FOURTH_DATE)
                .build()));
    }

    @Test
    public void test_hasEndDate_returnsFalse() {
        LeaveInPeriodPredicate predicate = generatePredicate(null, FOURTH_DATE);

        // start date after query end -> returns false
        assertFalse(predicate.test(new LeaveBuilder()
                .withStart(FIFTH_DATE).withEnd(SIXTH_DATE)
                .build()));
    }
}

package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.CsvAdaptedPerson.ADDRESS_HEADER;
import static seedu.address.storage.CsvAdaptedPerson.EMAIL_HEADER;
import static seedu.address.storage.CsvAdaptedPerson.NAME_HEADER;
import static seedu.address.storage.CsvAdaptedPerson.PHONE_HEADER;
import static seedu.address.storage.CsvAdaptedPerson.TAGS_HEADER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.CsvMissingFieldException;
import seedu.address.commons.util.GetValuer;

public class CsvAdaptedPersonTest {
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<AdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(AdaptedTag::new)
            .collect(Collectors.toList());

    private static final List<String> VALID_TAG_NAMES = VALID_TAGS.stream()
            .map(AdaptedTag::getTagName)
            .collect(Collectors.toList());

    private static final String VALID_TAGS_CSV_STRING = String.join(
            CsvAdaptedPerson.TAG_DELIMITER, VALID_TAG_NAMES);

    @Test
    public void getCsvValues_validFields() {
        String tagRep = String.join(CsvAdaptedPerson.TAG_DELIMITER, VALID_TAG_NAMES);
        String[] expectedValues = {VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, tagRep};

        CsvAdaptedPerson person = new CsvAdaptedPerson(BENSON);
        assertEquals(person.getCsvValues(), Arrays.asList(expectedValues));
    }

    @Test
    public void getHeader() {
        String[] expectedHeaders = {NAME_HEADER, CsvAdaptedPerson.PHONE_HEADER,
            CsvAdaptedPerson.EMAIL_HEADER, CsvAdaptedPerson.ADDRESS_HEADER, CsvAdaptedPerson.TAGS_HEADER};
        assertEquals(CsvAdaptedPerson.getHeader(), Arrays.asList(expectedHeaders));
    }

    /**
     * Mock CsvRow that returns Benson's values when the corresponding headers are queried. All headers' values are
     * present.
     */
    class MockCsvRow implements GetValuer {
        private final boolean hasName;
        private final boolean hasPhone;
        private final boolean hasEmail;
        private final boolean hasAddress;
        private final boolean hasTags;

        /**
         * Constructs a MockCsvRow object with all headers' values present.
         */
        public MockCsvRow() {
            this.hasName = true;
            this.hasPhone = true;
            this.hasEmail = true;
            this.hasAddress = true;
            this.hasTags = true;
        }

        /**
         * Constructs a MockCsvRow object. Boolean values are used to control whether the corresponding
         * fields' values are present in the row
         * @param hasName If the row contains the name
         * @param hasPhone If the row contains the phone number
         * @param hasEmail If the row contains the email address
         * @param hasAddress If the row contains the address
         * @param hasTags If the row contains the tags
         */
        public MockCsvRow(boolean hasName, boolean hasPhone, boolean hasEmail, boolean hasAddress,
                          boolean hasTags) {
            this.hasName = hasName;
            this.hasPhone = hasPhone;
            this.hasEmail = hasEmail;
            this.hasAddress = hasAddress;
            this.hasTags = hasTags;
        }

        /**
         * Returns values associated with the corresponding column.
         *
         * @param field Name of column to query for
         * @return String value associated with column
         * @throws CsvMissingFieldException if queried field has no associated value
         */
        public String getValue(String field) throws CsvMissingFieldException {
            if (hasName && field.equals(NAME_HEADER)) {
                return VALID_NAME;
            }
            if (hasPhone && field.equals(PHONE_HEADER)) {
                return VALID_PHONE;
            }
            if (hasEmail && field.equals(EMAIL_HEADER)) {
                return VALID_EMAIL;
            }
            if (hasAddress && field.equals(ADDRESS_HEADER)) {
                return VALID_ADDRESS;
            }
            if (hasTags && field.equals(TAGS_HEADER)) {
                return VALID_TAGS_CSV_STRING;
            }
            throw new CsvMissingFieldException(field);
        }
    }

    @Test
    public void deserialisePerson_allFieldsPresent_returnsCsvAdaptedPerson() throws Exception {
        GetValuer mockCsvRow = new MockCsvRow();
        CsvAdaptedPerson person = CsvAdaptedPerson.deserialisePerson(mockCsvRow);
        assertEquals(person.toModelType(), BENSON);
    }

    @Test
    public void deserialisePerson_missingName_throwsException() {
        GetValuer mockCsvRow = new MockCsvRow(false, true, true, true, true);
        assertThrows(CsvMissingFieldException.class, () ->
                CsvAdaptedPerson.deserialisePerson(mockCsvRow));
    }

    @Test
    public void deserialisePerson_missingPhone_throwsException() {
        GetValuer mockCsvRow = new MockCsvRow(true, false, true, true, true);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(PHONE_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedPerson.deserialisePerson(mockCsvRow));
    }

    @Test
    public void deserialisePerson_missingEmail_throwsException() {
        GetValuer mockCsvRow = new MockCsvRow(true, true, false, true, true);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(EMAIL_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedPerson.deserialisePerson(mockCsvRow));
    }

    @Test
    public void deserialisePerson_missingAddress_throwsException() {
        GetValuer mockCsvRow = new MockCsvRow(true, true, true, false, true);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(ADDRESS_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedPerson.deserialisePerson(mockCsvRow));
    }

    @Test
    public void deserialisePerson_missingTags_throwsException() {
        GetValuer mockCsvRow = new MockCsvRow(true, true, true, true, false);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(TAGS_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedPerson.deserialisePerson(mockCsvRow));
    }
}

package seedu.address.storage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.CsvMissingFieldException;
import seedu.address.commons.util.CsvParsable;
import seedu.address.commons.util.GetValuer;
import seedu.address.model.person.Person;

/**
 * CSV-friendly version of {@link Person}
 */
class CsvAdaptedPerson extends AdaptedPerson implements CsvParsable {
    /**
     * Initialises fields in the CsvAdaptedPerson base class
     */
    public static final String TAG_DELIMITER = ",";

    public static final String NAME_HEADER = "Name";
    public static final String PHONE_HEADER = "Phone";
    public static final String EMAIL_HEADER = "Email";
    public static final String ADDRESS_HEADER = "Address";
    public static final String TAGS_HEADER = "Tags";

    /**
     * Constructs a CsvAdaptedPerson
     * @param name Name of person
     * @param phone Phone number of person
     * @param email Email address of person
     * @param address Address of person
     * @param tags Tags associated with person
     */
    private CsvAdaptedPerson(String name, String phone, String email, String address, List<AdaptedTag> tags) {
        super(name, phone, email, address, tags);
    }

    /**
     * Converts a given {@code Person} into this class
     */
    public CsvAdaptedPerson(Person source) {
        super(source);
    }

    /**
     * Returns list of string values of the fields in Person
     * @return List of string values of fields
     */
    @Override
    public List<String> getCsvValues() {
        String serialisedTags = serialiseTags();
        String[] fieldValues = {name, phone, email, address, serialisedTags};
        return Arrays.asList(fieldValues);
    }

    /**
     * Serialises the tags into a single string - a different delimiter has to be used to avoid problems in
     * deserializing the Person string
     * @return CSV string representation of tags
     */
    private String serialiseTags() {
        List<String> tagNames = tags.stream().map(AdaptedTag::getTagName).collect(Collectors.toList());
        return String.join(TAG_DELIMITER, tagNames);
    }

    /**
     * Constructs a CsvAdaptedPerson object from an object where values are queried using getValue()
     * @param row An object that contains values associated with a Person, queried using getValue()
     * @return A CsvAdaptedPerson object with field values from the CsvRow
     * @throws CsvMissingFieldException if the CsvRow does not contain the field requested
     */
    public static CsvAdaptedPerson deserialisePerson(GetValuer row) throws CsvMissingFieldException {
        String name = row.getValue(NAME_HEADER);
        String phone = row.getValue(PHONE_HEADER);
        String email = row.getValue(EMAIL_HEADER);
        String address = row.getValue(ADDRESS_HEADER);
        List<AdaptedTag> tags = deserialiseTags(row.getValue(TAGS_HEADER));

        return new CsvAdaptedPerson(name, phone, email, address, tags);
    }

    /**
     * Returns a list of the column headers
     * @return List of the column headers
     */
    public static List<String> getHeader() {
        String[] headers = {NAME_HEADER, PHONE_HEADER, EMAIL_HEADER, ADDRESS_HEADER, TAGS_HEADER};
        return Arrays.asList(headers);
    }

    /**
     * Deserializes the CSV string representation of the tags
     * @param tags CSV string representation of tags
     * @return List of tags
     */
    private static List<AdaptedTag> deserialiseTags(String tags) {
        return Arrays.stream(tags.split(TAG_DELIMITER))
                .filter(tagName -> !Objects.equals(tagName, ""))
                .map(AdaptedTag::new).collect(Collectors.toList());
    }
}

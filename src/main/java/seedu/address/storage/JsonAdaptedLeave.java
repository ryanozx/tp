package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Status;

/**
 * Jackson-friendly version of {@link Leave}.
 */
public class JsonAdaptedLeave {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Leave's %s field is missing!";

    private final String start;
    private final String end;
    private final String title;
    private final String description;
    private final String status;
    private final String employee;

    /**
     * Constructs a {@code JsonAdaptedLeave} with the given leave details.
     */
    @JsonCreator
    public JsonAdaptedLeave(@JsonProperty("start") String start, @JsonProperty("end") String end,
            @JsonProperty("title") String title, @JsonProperty("description") String description,
            @JsonProperty("status") String status, @JsonProperty("employee") String employee) {
        this.start = start;
        this.end = end;
        this.title = title;
        this.description = description;
        this.status = status;
        this.employee = employee;
    }

    /**
     * Converts a given {@code Leave} into this class for Jackson use.
     */
    public JsonAdaptedLeave(Leave source) {
        start = source.getStart().toString();
        end = source.getEnd().toString();
        title = source.getTitle();
        description = source.getDescription();
        status = source.getStatus();
        employee = source.getEmployee().getName().toString();
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getEmployee() {
        return employee;
    }
    /**
     * Converts this Jackson-friendly adapted leave object into the model's {@code Leave} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted leave.
     */
    public Leave toModelType() throws IllegalValueException {
        if (start == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start"));
        }
        if (end == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end"));
        }
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }
        if (employee == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "employee"));
        }
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "status"));
        }

        final Date modelStart = Date.of(start);
        final Date modelEnd = Date.of(end);
        final String modelTitle = title;
        final String modelDescription = description;
        final Status modelStatus = Status.of(status);
        // final Person modelEmployee = employee.toModelType();
        return null;
        // return new Leave(modelEmployee, modelTitle, modelStart, modelEnd, modelDescription, modelStatus);
    }
}

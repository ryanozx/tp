package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.leave.Leave;

/**
 * Jackson-friendly version of {@link Leave}.
 */
public class JsonAdaptedLeave extends AdaptedLeave {
    /**
     * Converts a given {@code Leave} into this class for Jackson use.
     */
    public JsonAdaptedLeave(Leave source) {
        super(source);
    }
    private JsonAdaptedLeave(String start, String end, String title, String description,
                             String status, String employee) {
        super(start, end, title, description, status, employee);
    }
    /**
     * Constructs a {@code JsonAdaptedLeave} with the given leave details. The purpose of this static method
     * is to enable checking of the Employee instance to ensure it is non-null before initialising the base
     * AdaptedLeave instance.
     */
    @JsonCreator
    public static JsonAdaptedLeave of(@JsonProperty("start") String start, @JsonProperty("end") String end,
                                      @JsonProperty("title") String title,
                                      @JsonProperty("description") String description,
                                      @JsonProperty("status") String status,
                                      @JsonProperty("employee") Employee employee) {
        requireNonNull(employee);
        return new JsonAdaptedLeave(start, end, title, description, status, employee.getName().getFullName());
    }

    /**
     * Helper class to access nested field in serialized JSON Leave object
     */
    public static class Employee {
        private final Name name;

        /**
         * Constructs an Employee instance
         * @param name Name of employee
         */
        @JsonCreator
        public Employee(@JsonProperty("name") Name name) {
            requireNonNull(name);
            this.name = name;
        }
        public Name getName() {
            return name;
        }
    }

    /**
     * Helper class to access nested field in serialized JSON Leave object
     */
    public static class Name {
        private final String fullName;
        @JsonCreator
        public Name(@JsonProperty("fullName") String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }
    }
}

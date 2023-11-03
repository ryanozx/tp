package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Interface for objects that implement toModelType(), which converts an object from
 * an adapted variant to the models' variant
 * @param <T> Type of object in the model
 */
public interface ToModelTyper<T> {
    T toModelType() throws IllegalValueException;
}

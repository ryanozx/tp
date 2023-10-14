package seedu.address.commons.controllers;

import java.io.File;
import java.util.Optional;

import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Interface for a handler that handles file dialogs (e.g. open file dialog)
 */
public interface FileDialogHandler {
    Optional<File> openFile(String title, ExtensionFilter... extensions);
}

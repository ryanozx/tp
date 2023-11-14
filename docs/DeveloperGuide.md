<frontmatter>
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
</frontmatter>

# AB-3 Developer Guide



<!-- * Table of Contents -->
<page-nav-print></page-nav-print>

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* [Address Book 3](https://se-education.org/addressbook-level3/): HRMate is built on top of AB3
* [JavaFX](https://openjfx.io/): The GUI framework used in HRMate
* [Jackson](https://github.com/FasterXML/jackson): JSON parsing library used to read and write HRMate's JSON data files
* [MarkBind](https://markbind.org/): Used to generate HRMate's project site

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280"></puml>

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574"></puml>

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300"></puml>

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"></puml>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `LeaveListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` and `Leave` objects residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"></puml>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command"></puml>

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"></puml>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450"></puml>


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the leaves book data as well i.e., all `Leaves` objects (which are contained in a `UniqueLeavesList` object).
* stores the currently 'selected' `Person` objects (e.g., results of `find`) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list changes.
* stores the currently 'selected' `Leave` objects (e.g. results of `find-leave`) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Leave>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list changes.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450"></puml>

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550"></puml>

The `Storage` component,
* can save the address book data, leaves book data and user preference data in either JSON or CSV format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage`, `LeavesBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

<puml src="diagrams/AddressBookStorageClassDiagram.puml" width="550"></puml>
<puml src="diagrams/LeavesBookStorageClassDiagram.puml" width="550"></puml>

Both `AddressBookStorage` and `LeavesBookStorage` contain JSON and CSV implementations. These implementations exist separately
as their methods invoke methods from different Util files - the JSON implementation invokes JsonUtil methods, while the
CSV implementation invokes CsvUtil methods. `SerializableAddressBook`, `SerializableLeavesBook`, `AdaptedPerson` and
`AdaptedLeave` have been abstracted out to promote code reusability, with the use of generics where possible to enforce
type safety.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.



### The Person class

ManageHR keeps track of employees within the company with the use of `Person` and `UniquePersonList`. The `UniquePersonList` serves as a container for the `Person` objects,
while enforcing the constraints that no 2 employees have the same name.

The `Person` class contains the following attributes.
<puml src="diagrams/PersonObjectDiagram.puml", width="550"></puml>

1. `Name`: The name of the employee.
2. `Phone`: The phone number of the employee.
3. `Email`: The email address of the employee.
4. `Address`: The address of the employee.
5. `Tags`: The customised tag added by the user.

### Find an Employee by tags
`FindAllTagCommand` and `FindSomeTagCommand` are implemented similar to `FindCommand`.
They use `TagsContainAllTagsPredicate` and `TagsContainAllTagsPredicate` respectively as predicate to filter the employee list. And then update the displayed employee list of the model

The following sequence diagram shows how `FindAllTagCommand` executes.
<puml src="diagrams/FindAllTagCommandDiagram.puml", width="550"></puml>

#### Design considerations:
`FindAllTagCommand` matches employees with all specified tags while `FindSomeTagCommand` matches employees with any of the specified tags.
The nuance difference is made to cater to users' needs for efficient searching.
### Adding tag feature

#### Implementation
`AddTagCommand` is implemented similar to `EditCommand`.
A new `Person` is created with the information from the old `Person`.
The tags are then added before replacing the old `Person` with the new `Person`.

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/AddTagActivityDiagram.puml", width="550"></puml>

#### Design considerations:

**Aspect: How AddTagCommand executes:**
* **Alternative 1 (current choice):** Builts a new Person.
    * Pros: Easy to implement (using `EditCommand` as reference), immutability allows for potential redo and undo commands.
    * Cons: Memory intensive, costly in terms of time.
* **Alternative 2:** Add tags to `Person`.
    * Pros: Memory efficient
    * Cons: Mutable `Person` can affect implementation of potential redo and undo commands.

### The Leave class

ManageHR keeps track of the leaves of employees within the company with the use of `Leave` and `UniqueLeaveList`. The `UniqueLeaveList` serves as a container for the `Leave` objects,
while enforcing the constraints that no 2 leaves can have same start date and end date for the same employee.

The `Leave` class contains the following attributes.
<puml src="diagrams/LeaveObjectDiagram.puml", width="550"></puml>>

1. `ComparablePerson`: The employee.
2. `Title`: The title of the leave.
3. `Description`: The description of the leave.
4. `Date start`: The start date of the leave.
5. `Date end`: The end date of the leave.
5. `Status`: The status of the leave.

`Date start` must be earlier than or the same as the `Date end`.
All the attributes except Description and Status are compulsory fields for adding a leave.

### Add A Leave application feature

The add-leave command allows the HR manager to add a leave record for a specific employee. This feature enhances the HRMate system by providing a way to manage and track employee leaves efficiently.
Fields compulsory to enter for `add-leave` as `String` type include:
1. `index`: The index of the person for the leave application. It will be parsed as `Index` type to `AddLeaveCommand` object and converted to `Person` type based on the displayed list when a new `Leave` object created.
2. `title`: The title of the leave, it will be parsed as `Title` type to `AddLeaveCommand` object and to the newly created `Leave` object.
3. `date start`: The start date of the leave in "yyyy-MM-dd" format. It will be parsed together with `date end` as `Range` type to `AddLeaveCommand` object and to the newly created `Leave` object.
4. `date end`: The end date of the leave in "yyyy-MM-dd" format. It will be parsed together with `date start` as `Range` type to `AddLeaveCommand` object and to the newly created `Leave` object.
5. `description`: The description of the leave. It is optional and will be parsed as `NONE` if no description field exists. Otherwise it will be parsed as `Description` type to `AddLeaveCommand` object and to the newly created `Leave` object.

* CommandException due to unfounded index in the list will be thrown and handled in `AddLeaveCommand`.
* InvalidValueExceptions thrown due to illegal arguments, and EndBeforeStartExceptions thrown due to the end date occurring before the start date, will be handled by ParserException in `AddLeaveCommandParser`.
* CommandException due to duplicated leaves that have exact start and end dates will be thrown and handled at the line `model.addLeave(toAdd)` in `AddLeaveCommand#execute`.

The activity diagram for adding a leave is as followed:
<puml src="diagrams/AddLeaveCommandActivityDiagram.puml", width="550"></puml>

Here is an example usage of the `add-leave` feature:
1. The user uses the `find` command to filter for employees named Martin.
2. The user enters the command `add-leave 1 title/Sample Leave 1 start/2023-11-01 end/2023-11-01` with Martin being index 1.
3. A record of leave with specified title and dates for Martin is created.

#### Design considerations
The command follows a structured format to ensure ease of use and to minimize errors. The use of an index ensures that the leave is associated with a specific employee. The format of the command is designed to be clear and straightforward.

### Import file

The import feature allows users to import employee records in CSV format, increasing portability of
the user's data. The import feature can provide a means of mass adding employee records, without having to use the `add`
command repeatedly. 

Here is an example usage of the import feature:
1. User executes the `import` command.
2. User navigates to the file to import using the file dialog that opens.
3. User selects the file and clicks on the Open button of the file dialog.
4. All contacts in the address book will be overwritten by the contents of the imported file

The CSV file is read into a CsvFile object, which is then converted into a CsvSerializableAddressBook object by reading each
row and the corresponding values for each column. The CsvSerializableAddressBook is then converted into an AddressBook instance,
which replaces the current AddressBook instance in the app.

#### Design considerations
The choice of using a file dialog for the user to select the file, as opposed to having the user type the file name in
the command, is to minimise the likelihood of the user misspelling the file or accidentally typing the wrong path due to
the inclusion/exclusion of parent directories.

#### Proposed extensions
1. Implement autosave ability when importing files
- Due to the overwriting ability of the import command, all contacts that were in HRMate previously would be lost
after executing the import command. As such, an autosave ability could be added, utilising the already implemented
export feature, whereby the current contents of the address book is exported just prior to overwriting the address book.
2. Implement non-overwriting option for importing files
- This gives user more granular control over their files, since they can combine employee records stored in different files.
- Flags can be added to the import command to determine import policy
  - Overwrite existing address book
  - If there is a record with the same name in the current address book and imported file, replace it with the one in the imported file
  - If there is a record with the same name in the current address book and imported file, keep the one in the current address book
3. Enable importing of leaves
- A flag can be supplied to the import command to determine the type of file to be imported
  - By default, it assumes that the imported file contains the address book
  - One flag can be used to indicate that the imported file contains the leaves book
  - Another flag can be used to indicate that the user would like to import both address book and leaves book. This will trigger
  two file dialogs. The reason for providing this option is that the order of importation is specific - the leaves book cannot
  be imported before the address book, as leaves require a valid reference to an existing employee in the address book.

### Export feature

The export feature enables users to export employee records into CSV format, which can then be opened in other spreadsheet
applications. It allows users to select filtered data to export, providing greater granularity in control over file content.

Here is an example usage of the `export` feature:
1. The user uses the `find-some-tag` command to filter for employees with the `full time` tag
2. The user enters the command `export fulltimers`
3. A file will be created in `{home folder of HRMate}/exports`, with the name `fulltimers.csv`. This file contains employees
with the `full time` tag.

The export command works by retrieving the filtered person list in the address book, which contains a list of employee records
that are currently visible in the address book panel. A CsvSerializableAddressBook is constructed from this filtered person list,
which is then serialized into a CsvFile object. CsvUtil then writes the CsvFile instance into a CSV file.

#### Design considerations
Unlike the import command, the use of a file dialog in saving the file was not adopted as it was deemed unnecessary. Saving all
records into the same `export` folder provides users with an easy-to-find folder to locate their files.

#### Proposed extension
1. The export command will export both address book and leave book together. The leave book can be saved under the name
`{address book save name}_leaves.csv` to indicate its association with the address book save file. 

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0"></puml>

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1"></puml>

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2"></puml>

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3"></puml>


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/UndoSequenceDiagram.puml" alt="UndoSequenceDiagram"></puml>

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4"></puml>

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5"></puml>

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="550"></puml>

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_




--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a sizeable number of employees' information
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage HR information faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …                             | I want to …                                | So that I can …                                                        |
|----------|------------------------------------|--------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | new user                           | see usage instructions                     | refer to instructions when I forget how to use the App                 |
| `* * *`  | HR manager                         | add a new employee                         |                                                                        |
| `* * *`  | HR manager                         | delete an employee                         | remove employees who no longer work here                               |
| `* * *`  | HR manager                         | find an employee by name                   | locate details of employees without having to go through the entire list |
| `* * *`  | organised HR manager               | add/delete a tag to an employee            | change the label of an employee                                        |
| `* * *`  | organised HR manager               | view all my tags                           | filter by them                                                         |
| `* * *`  | organised HR manager               | find employees by tags                     | find specific category of employees for higher level workflows         |
| `* * *`  | organised HR manager               | add a new leave application of an employee | keep track of the leaves applications                                  |
| `* *`    | HR manager                         | hide private contact details               | minimize chance of someone else seeing them by accident                |
| `* *`    | HR manager                         | import/export records in CSV format        | open the records in other apps                                         |
| `*`      | HR manager of a large organisation | sort employees by name                     | locate an employee easily                                              |


### Use cases

(For all use cases below, the **System** is the `HRMate` and the **Actor** is the `HR Manager`, unless specified otherwise)

**Use case: Add/delete a tag from an employee**

**MSS**

1.  User requests to find employee by name
2.  HRMate shows a list of employees with the same name
3.  User requests to add/delete a tag from a specific employee in the list
4.  HRMate adds/deletes tag from specified employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given tag is invalid. (already there for add, not there for delete)

    * 3a1. HRMate shows an error message.

      Use case resumes at step 2.


**Use case: Delete an employee**

**MSS**

1.  User requests to list employees
2.  HRMate shows a list of employees
3.  User requests to delete a specific employee in the list
4.  HRMate deletes the employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HRMate shows an error message.

      Use case resumes at step 2.

**Use case: Import employee records**

**MSS**

1. User requests to import employee records
2. User selects CSV file from file dialog
3. HRMate adds records in CSV file inside its list of employee records
4. HRMate displays a message indicating successful importing of file

    Use case ends.

**Extensions**

* 2a. User cancels the file selection operation.

  * 2a1. HRMate closes the file selection window and aborts the operation.

    Use case ends.

* 3a. File is not of CSV file type

  * 3a1. HRMate displays an error message.

    Use case ends.

* 3b. File is corrupted, unable to read employee records

  * 3b1. HRMate discards all data read in.
  * 3b2. HRMate displays an error message.

    Use case ends.

**Use case: Export employee records**

**MSS**

1. User requests to export employee records.
2. User supplies a name and path to save the records.
3. HRMate creates the CSV file containing the employee records with the specified name at the specified location.
4. HRMate displays a message indicating the file has been created.

    Use case ends.

**Extensions**

* 2a. User does not supply a name
  * 2a1. HRMate displays an error message

    Use case ends.

* 2b. User does not supply a path/supplies an invalid path
  * 2b1. HRMate creates the CSV file containing the employee records with the specified name at the default location
    (same folder where the save files are located)

    Use case resumes at step 3.

* 3a. HRMate is unable to create the file due to errors (e.g. permission errors)
  * 3a1. HRMate aborts the file creation operation.
  * 3a2. HRMate displays an error message.

    Use case ends.

**Use case: List all tags**

**MSS**

1.  User requests to view all tags
2.  HRMate shows a list of all available tags

    Use case ends.

**Extensions**

* 2a. There is no existing tag in the system.

    * 2a1. HRMate notifies the user of no existing tag in the system.

      Use case ends.

**Use case: List employees with at least one specified tags**

**MSS**

1.  User requests to find employees who match at least one of the specified tags
2.  HRMate shows a list of employees who match at least one of the specified tags

    Use case ends.

**Extensions**

* 2a. The specified tags do not exist in the system.

    * 2a1. HRMate notifies the user of invalid tags.

      Use case resumes at step 1.
* 2b. User does not provide any tags.

    * 2b1. HRMate notifies the user of missing parameters.

      Use case resumes at step 1.

**Use case: List employees with all specified tags**

**MSS**

1.  User requests to view all tags
2.  HRMate shows a list of all available tags

    Use case ends.

**Extensions**

* 2a. There is no existing tag in the system.

    * 2a1. HRMate notifies the user of no existing tag in the system.

      Use case resumes at step 1.


**Use case: Add a leave application**

**MSS**

1. The HR manager requests to find an employee by name.
2. HRMate shows a list of employees with the same name.
3. The HR manager requests to add a leave application for the selected employee 
4. HRMate adds the leave application to the employee's record based on the provided information.

    Use case ends.

**Extensions:**

* 2a. The list of employees with the provided name is empty.
* 
    * 2a1. HRMate informs the HR manager that no employees with the given name were found. 
  
      Use case ends.
  
* 3a. The provided employee index is invalid.

    * 3a1. HRMate shows an error message indicating that the employee name is not valid.
      
      Use case resumes at step 2.

* 4a. The provided leave application details are invalid. 

    * 4a1. HRMate shows an error message specifying the issue with the provided details (e.g., date format, missing fields).

      Use case resumes at step 3.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  A user with above average typing speed (60 or more words per minute) for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
3.  Should have an intuitive and user-friendly interface, ensuring that HR managers can easily navigate and use its features without extensive training.
4.  HRMate should be capable of handling a growing volume of employee records (up to 1000 persons) without a substantial decrease in performance. It should efficiently manage and store data as the number of employees and records increases over time.
5.  HRMate should complete operations involving a single records within 500ms, and operations involving listing, searching, and filtering within 2s.

## **Appendix: Effort**

HRMate is built for extensibility. Not much effort is needed to implement new commands that are variations of existing features, but effort is needed to add new entities.

### Challenges Faced
The first challenge given is understanding the code infrastructure. HRMate uses many design patterns like Facade pattern, Command pattern and MVC pattern. Without knowledge of these patterns, HRMate would seem complicated and difficult to understand.
Overall the understanding that the `Model` is a Facade for `AddressBook` and `LeavesBook`, input commands follow the Command pattern, and that `AddressBook` and `LeavesBook` are the models, JavaFX is used for the view and `ModelManager` functions as the controller greatly help in the understanding of HRMate's infrastructure.
Another significant challenge is the implementation of the leaves module. Given HRMate's immutable design philosophy, some design choices like replacing stale `Leave` with new instances of `Leave` with the updated `Person` are chosen. Care must be taken to update `Leave` everytime a `Person` is replaced like in the `EditCommand`, `AddTagCommand` and `DeleteCommand`. When adding a new potential module like `Report`, we anticipate that care must be taken when the associated `Person` or `Leave` is replaced.

### Effort Required
To illustrate the difference in effort, the effort needed to create `AddTagCommand` and the `Leave` module will be compared.

Given that `AddTagCommand` is a specific case of `EditCommand`, much of the logic is similar to `EditCommand`. In this case, `AddTagCommand` copies a `Person`, adds the new `Tag` before calling `Model#SetPerson`.

Adding a new command is easy compared to the implementation of the `Leave` module. We took inspiration from the `Person` class and created wrapper classes for the fields like `Title`, `Description` and `Date`. One of the significant challenges were the added constraint of comparing two fields, `start` and `end`. This logic was not present in `Person`. For example `Name` validations do not concern `Tag` validations and vice versa. In `Leave` case, `start` affects the validations for `end`, as `end` must be on or after `start`. To resolve this, we created `Leave`'s constructor to use `Range`, whose creation mandates `start` be on or before `end`. This ensures that the created `Leave` adheres to the constraint of `start` being before or on `end`. We anticipate that the creation of other entities like `Report` to require the creation of new wrapper classes, or even validation classes to enforce validations that span across multiple fields.

Efforts were also spent on ensuring data validity for `Person` and `Leave` when the associated object is modified. As mentioned above, one of the challenges faced was the immutable principle that AB3 used. When a `Person` is edited through `EditCommand`, `AddTagCommand` or `DeleteTagCommand`, the old `Person` is replaced by the newly created `Person`. This results in some `Leave` pointing to the stale `Person`. Thus, `Model#SetPerson` and `Model#DeletePerson` must be amended to edit `LeavesBook` as well to avoid any `Leave` pointing to any stale `Person`. We expect that such methods like `Model#SetPerson`, `Model#DeletePerson`, `Model#SetLeave`, `Model#DeleteLeave` must be amended when adding new modules if the new entities is reliant on information from pre-existing entities.

### Achievements of HRMate
 * Successfully implemented second entity, `Leave` alongside `Person` with information consistency even when `Person` is edited or deleted.
 * Created new way to import data using csv for both `Leave` and `Person`, opening up changing in app data to users who might not understand json.
 * Added 19 more commands while increasing code coverage by 2%.
   
### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>Note: These instructions only provide a starting point for testers to work on;
testers are expected to do more exploratory testing.</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.


### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a person after applying a filter

    1. Prerequisites: Filter to the second person using `find PERSON_NAME` where `PERSON_NAME` is the name of the second person.

    2. Test case: `delete 1`<br>
        Expected: First visible contact is delete from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    3. Test case: `delete 2`<br>
       Expected: No person is delete. Error details shown in the status message. Status bar remains the same.

    4. Follow up actions: `list`<br>
       Expected: Only the previously deleted person is deleted.
       
### Finding all tags matched
1. Finding Employees with All Tags in a Valid Scenario
   1. Prerequisites:
      1. Have a dataset with employees having different tags, specifically `remote`, `full time`, `part time` and `on-site`. 
      2. List all employees using the list command to identify available tags. 
   2. Test Case: find-all-tag t/remote t/full time 
      Expected: GUI Changes: A dedicated interface section displays a list of employees with both tags `remote` and `full time`. Status message indicates the number of matched employees. Verify that employees with additional tags are also displayed.
   3. Test Case: find-all-tag
      Expected: Error message indicates an invalid command format.Status bar remains the same.
   4. Test Case: find-all-tag t/Nonexistent tag
      Expected: GUI Changes: A dedicated interface section displays no employee. Status message indicates 0 matched employee.
   5. Test Case: find-all-tag t/REMOTE
      Expected: GUI Changes: A dedicated interface section displays no employee. Status message indicates 0 matched employee.
   6. Test Case: find-all-tag t/123!
      Expected: Error message indicates illegal tag names. Status bar remains the same.
   7. Test Case: find-all-tag t/re
      Expected:Employees with tag named `re` are displayed. Verify that employees with additional tags are also displayed.
### Finding some tags matched
1. Finding Employees with All Tags in a Valid Scenario
    1. Prerequisites:
        1. Have a dataset with employees having different tags, specifically `remote`, `full time`, `part time` and `on-site`.
        2. List all employees using the list command to identify available tags.
    2. Test Case: find-some-tag t/remote t/full time
       Expected: GUI Changes: A dedicated interface section displays a list of employees with either tags `remote` and `full time`. Status message indicates the number of matched employees. Verify that employees with additional tags are also displayed.
    3. Test Case: find-some-tag
       Expected: Error message indicates an invalid command format.Status bar remains the same.
    4. Test Case: find-some-tag t/Nonexistent tag
       Expected: GUI Changes: A dedicated interface section displays no employee. Status message indicates 0 matched employee.
    5. Test Case: find-some-tag t/REMOTE
       Expected: GUI Changes: A dedicated interface section displays no employee. Status message indicates 0 matched employee.
    6. Test Case: find-some-tag t/123!
       Expected: Error message indicates illegal tag names. Status bar remains the same.
    7. Test Case: find-some-tag t/re
       Expected:Employees with tag named `re` are displayed. Verify that employees with additional tags are also displayed.

### Adding a leave
1. Adding a leave while all leaves are being shown

   1. Prerequisites: List all leaves using the list-leaves command. Multiple leaves in the list.

   2. Test case: add-leave 1 title/Vacation start/2023-11-15 end/2023-11-20<br>
   Expected: The leave is added to the list. Details of the added leave shown in the status message. Timestamp in the status bar is updated.

   3. Test case: add-leave 0 title/Conference start/2023-12-01 end/2023-12-03 d/Attending conference<br>
   Expected: No leave is added. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect add-leave commands to try: add-leave, add-leave x, ... (where x is larger than the list size)<br>
   Expected: Similar to previous.
### Saving data

1. Dealing with missing/corrupted data files

    1. Open `./data/addressbook.json` and delete the first character `{`. Then open HRMate. <br>
       Expected: HRMate opens an empty address book.

    2. Add a person using `add` and a leave using `add-leave`, then `exit`. Open `./data/leavesbook.json` and edit the fullName of the name of the employee of a leave. Reopen HRMate.<br>
       Expected: HRMate restores all data except for the edited leave.

### Data integrity between `Person` and `Leave`

1. Dealing with edits to `Person`

    1. Add a `Person` using `add` and a `Leave` using `add-leave`. Edit the `Person` name using `edit`. <br>
       Expected: The Employee under the created `Leave` is edited also.

2. Dealing with deletes to `Person`

    1. Add a `Person` using `add` and a `Leave` using `add-leave`. Delete the `Person` with `delete`. <br>
       Expected: The previously created `Leave` is deleted also.

----------------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

1. `export-all` would export both `Person` and `Leave`. HR Managers usually wants to move both `Person` and `Leave` to another application, so being able to export both in a single csv sheet would help them do so.

2. find-all-tags and find-any-tags searches for tags case insensitive. Since tags are used for categorising, HR Managers might see no difference between Intern and intern. Hence allowing case insensitive search in find-all-tags and find-any-tags would help HR Managers find employees more easily.

3. Mergable `import`. Sometimes, HR Managers want to move data from one app to HRMate without removing all current employees. Allow HRMate to merge a csv file, with an option to overwrite current data or overwrite csv data when a same employee is encountered, would help HR Managers achieve this.

4. Filtered `clear`. Allowing HR Managers to select a group of employees to delete would be good. For example, they could use `find-any-tags t/intern`, `clear` to delete all interns. Subsequently, `list` would show all non intern employees.


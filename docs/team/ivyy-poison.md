---
  layout: default.md
  title: "Ivan's Project Portfolio Page"
---

### Project: HRMate

HRMate is a desktop address book application that aims to streamline HR processes, by offering an intuitive, CLI-based
contact management system with specialised functionalities for HR tasks. It has a GUI created with JavaFX, and is
written in Java.

Given below are my contributions to the project.

* **New feature**: Added the `delete-tag` command 
  * What it does: Allows HR managers to delete a tag (or a set of tags) from an entry in the address book
  * Justification: This feature improves the user experience by allowing users to delete tags from entries
    without having to use the edit command, which is more cumbersome and less intuitive.
  * Pull Request: [[PR#52]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/52)

* **New feature**: Create the `Leave`, `LeavesBook` and `LeavesBookStorage` classes
  * What it does: Allows HR managers to keep track of employees' leave applications, and perform operations such
    as adding, deleting, approving and rejecting leave applications.
  * Justification: This feature, while not directly functional or command-related, serves as the backbone of a key
    feature of HRMate, which is the ability to keep track of employees' leave applications. This feature is also
    essential for the implementation of the various Leaves commands.
  * Notable contributions:
    * Leave class - This class represents a leave application, and contains information such as the employee's name,
      the start and end dates of the leave, the status of the leave application, and a description and title containing the
      reason for the leave.
    * LeavesBook class - This class represents the collection of leave applications, to be displayed to the user. The list enforces a 
      uniqueness constraint, ensuring that there are no duplicate leave applications in the list. It also provides an API for the
      addition, deletion and modification of leave applications in the list, which is used by the Leaves commands.
    * LeavesBookStorage class - This class handles the reading and writing of leave applications into a json file stored in the /data
      folder. A serializer and deserializer is used to convert the data into a json format, and vice versa. The serializer also checks
      for invalid data, such as the case whereby an employee's name tied to a leave application cannot be found in the address book.
      This enforces a level of data integrity, and prevents the addition of invalid data into the json file.
    * Integrated the Leave functionality into HRMate by extending the StorageManager and ModelManager functionalities to include
      the LeavesBookStorage and LeavesBook classes.
  * Pull Request: [[PR#62]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/62) [[PR#64]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/64)

* **Code coverage**
  * Created unit tests for the following classes: `Person`, `DeleteTagCommand`, `AddressBookParser`, `DeleteTagCommandParser` [[PR#52]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/52)
  * Created unit tests for the following classes: `Date`, `Leave`, `Status` [[PR#62]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/62)
  * Created unit tests for the following classes: `LeavesBook`, `StorageManager`, `ModelManager`, `UniqueLeaveList`, 
    `JSonLeavesBookStorage` [[PR#64]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/64)
  * Created unit tests for the following classes: `Description`, `Title`, `JsonAdaptedLeave`, `JsonSerializableLeavesBook` [[PR#86]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/86)
  * Improved code coverage of following classes: `PersonEntry`, `UserPref`, `Tag`, `AddressBook` [[PR#159]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/159)

* **Documentation**
  * Add documentation for the following commands in User Guide: `view-tag`, `add-tag` and `delete-tag` [[PR#41]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/41)
  * Add documentation for the following commands in User Guide: `find-leave-range`, `find-leave-status`, `find-all-leave`
    `find-leave` and `delete-leave` [[PR#183]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/183)
  * Reorder User Guide by rearranging commands into feature categories to allow for more intuitive navigation [[PR#194]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/194)
  * Add About section for HRMate to explain key terms and key features of application [[PR#195]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/195)
  * Add edits such as removal of excessive anchor links, and rewording of certain sections in User Guide, among others [[PR#194]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/194) [[PR#195]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/195) [[PR#199]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/199) [[PR#204]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/204)

* **Reviewing PRs** 
  * Review import/ export Developer Guide proposed implementation [[PR#43]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/43)
  * Review implementation of import / export feature of address book [[PR#53]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/53)
  * Review implementation of CSV Parser [[PR#58]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/58)
  * Review implementation of import / export feature of leaves book [[PR#103]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/103)
  * Review refactoring of exception handling by ParserUtil [[PR#165]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/165)
  * Review implementation of `find-leave-status` and `find-leave-range` commands [[PR#90]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/90) [[PR#91]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/91)

* **Miscellaneous**
  * Fixed bugs reported during Practical Exam Dry Run [[PR#158]](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/158)

Code contributed: [RepoSense Link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=ivyy-poison&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22)

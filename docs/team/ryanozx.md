<frontmatter>
  layout: default.md
  title: "Ryan's Project Portfolio Page"
</frontmatter>

### Project: HRMate

HRMate is a desktop address book application that aims to streamline HR processes, by offering an intuitive, CLI-based
contact management system with specialised functionalities for HR tasks. It has a GUI created with JavaFX, and is
written in Java.

Given below are my contributions to the project.

* **Code contributions:** [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=ryanozx&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)
* **Community**
  * Reviewed PRs: [GitHub link](https://github.com/AY2324S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+reviewed-by%3Aryanozx)
  * Reported bugs for other teams during PE-D
* **Documentation**
  * User Guide:
    * Add documentation for the features `clear`, `exit`, `import`, `export`, `import-leave`, `export-leave`
  * Developer Guide:
    * Add implementation and user stories for the features `import`, `export`, and `find-leave-range`
    * Update UML diagrams and descriptions in Design section
  * Managed conversion of documentation into MarkBind format
* **Enhancements to existing features**
  * Add method in FileUtil to support writing a stream of lines into a file ([#58](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/58))
* **New Feature**: Import and export commands ([#53](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/53), [#58](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/58), [#101](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/101))
  * Provides ability for users to import and export HRMate data in CSV format for greater portability
  * This feature provides greater interchangeability with other spreadsheet applications like Excel, and allows users to save
    filtered results that can be either stored elsewhere or sent to others
  * Highlights: 
    * Created abstract classes for AdaptedPerson, AdaptedLeave, SerializableAddressBook and SerializableLeavesBook
    * Created CsvUtil to handle the reading and writing of CSV files
    * Created CsvFile to support the ability to query CSV files for values in a specified column by column name
* * **New Feature**: Find leaves by time and status ([#90](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/90), [#91](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/91))
  * Provides ability for users to filter leaves by either the time period or the leave status
  * This feature allows users to search for leaves more intelligently e.g. what leaves are occurring at a certain period, which leaves
    have not been approved yet
  * Highlights:
    * Created Range class to guarantee that the start dates will not occur after the end dates, and provided the option
      of whether to enforce non-null start and end dates
* **Bug Fixes**:
  * Fix bug where changing an employee's name does not update the name in the leave application ([#178](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/178))
* Refactor various parts of the codebase to improve code readability and promote code reuse ([#165](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/165) and others)
* **Project Management**
  * Set up Issues tracker in the repo
  * Set up milestones v1.1 - v1.4 and assigned issues to rest of the team
  * Managed release of v1.3.2 and v1.4
  * Helped triage bug reports to determine further action for milestone v1.4

<frontmatter>
  layout: default.md
  title: "Qin Nanxin's Portfolio Page"
</frontmatter>

### Project: HRMate

HRMate is a desktop address book application that aims to streamline HR processes, by offering an intuitive, CLI-based contact management system with specialised functionalities for HR tasks. It has a GUI created with JavaFX, and is written in Java with about 10 kLoC.

Given below are my contributions to the project.

* **Documentation**
  * User Guide:
    * Amended introduction and quick start sections [#190](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/190) [#198](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/198)
    * Add and update the section Important things to note. [#181](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/181) [#198](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/198)
    * Add documentation for the features `list`, `view-tag`, `approve-leave`, `reject-leave`, and `edit-leave`. [#181](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/181)
    * Add warnings and reminders about the properties for various parameters. [#189](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/189)
    * Updated or removed error messages as the application is changed. [#198](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/198)
    * Proofread the user guide and amend spelling and grammatical errors. [#198](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/198)
    * Double-check that the user guide's described behaviour is consistent with the application's actual results. [#198](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/198)
  * Developer Guide:
    * Add implementation and user stories for the commands `view-tag` [#80](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/80)

* **Feature Development**
  * Developed the `view-tag` [#57](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/57), `approve-leave` [#83](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/83), `reject-leave` [#85](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/85), `find-leave` [#96](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/96), and `find-all-leave` [#98](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/98) commands.
  * Wrote unit tests for the above commands (in the same PRs).
  * Updated the UI to include the Employee's name in the leave list. [#93](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/93)
  * Updated Model, Model Manager and Leave classes components to integrate with newly implemented features. [#83](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/83)[#85](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/85)[#96](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/96)[#98](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/98)
  * Fix bugs:
    * `clear` command clears both the employee and leave application lists instead of only the employee list (approved but not merged); [#168](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/168)
    * The `find-all-tag` command now outputs the correct results as described in the user guide. [#170](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/170)

* **Community**
  * Reviewed 16 PRs: [Reviewed PRs](https://github.com/AY2324S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+reviewed-by%3Ainfibeyond)
  * Raised 14 bugs during Practical Exam Dry Run.

* **Project Management**
  * Update and document non-feature-specific sections in the user guide. (Introduction, Quick Start, Important things to note, etc.)
  * Create the application icon. (used in CS2101)

* **Code contributions:** [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=infibeyond&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22)

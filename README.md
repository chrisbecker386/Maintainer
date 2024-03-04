# :construction_worker_man: Maintainer App

A reminder and maintenance guide application for all machines in the household.
Regular maintenance prevents repairs and prolongs the life of our machines.

## :clipboard: Content

- [Project goals](#dart-project-goals)
    - [MVP1](#baby-mvp1)
    - [MVP2](#child-mvp2)
- [Info](#compass-info)
    - [ERD](#card_index-erd)
    - [Screens](#calling_screens)
    - [AppIcon](#milkyway-appicon)
- [Utilities](#computer-utilities)
    - [Makefile](#information_source-makefile)

## :dart: Project goals

The goal of this app is to create an app, that helps to maintain machines.

### :baby: :white_check_mark: MVP1
- sections, machines and task are be able to create :white_check_mark:
- An Overview Screen guides to the open tasks and settings :white_check_mark:
- On a SettingsScreen it is configurable how often notifications should appear :white_check_mark:

### :child: :black_square_button: MVP2
- a few predefined section, machines and tasks should be be selectable and setup(first app start) :black_square_button:
- each task should came with its own notification :black_square_button:
- Splash Screen :black_square_button:
- Status Report :black_square_button:

## :compass: Info

### :card_index: ERD

![ERD](../main/images/ER_Diagram.png)

### :calling: Screens
| Overview | Single Section | Single Machine |
|-|-|-|
| ![Overview](../main/images/Overview.png) | ![Single Section](../main/images/SingleSectionScreen.png) | ![Single Machine](../main/images/SingleMachineScreen.png) |
| Single Task | Task Finished | Section Creation |
| ![Single Task](../main/images/SingleTaskScreen.png) | ![Task Finished](../main/images/Finished.png) | ![Section Creation](../main/images/SectionCreation.png) |
| Task Creation A | Task Creation B | Task Set Date |
| ![Task Creation A](../main/images/TaskCreationPartA.png) | ![Task Creation B](../main/images/TaskCreationPartB.png) | ![Task Set Date](../main/images/TaskCreationSetDate.png) |
| Task Set Time | Task Add Step ||
| ![Task Set Time](../main/images/TaskCreationSetTime.png) | ![Task Add Step](../main/images/TaskCreationAddNewStep.png) ||

### :milky_way: AppIcon
[app icon from](https://www.flaticon.com/free-icons/maintenance")
Maintenance icons created by Iconjam - Flaticon

## :computer: Utilities

### :information_source: Makefile

### check format

```
    make check
```

### resolve format issues

```
    make format
```

### run all tests

```
    make testAll
```

### run unit test debug

```
    make testDebugUnit
```

### run unit test relase

```
    make testReleaseUnit
```

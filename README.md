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
- [Utilities](#computer-utilities)
    - [Makefile](#information_source-makefile)

## :dart: Project goals

The goal of this app is to create an app, that helps to maintain machines.

### :baby: MVP1

- a few predefined section, machines and tasks should be be selectable (first app start)
- sections, machines and task should be able to create, update and remove
- An Overview Screen should guide to the open tasks
- On a SettingsScreen should be configurable how often notifications should appear

### :child: MVP2

- Splash Screen
- Status Report

## :compass: Info

### :card_index: ERD

![ERD](../main/images/ER_Diagram.png)

### :calling: Screens
| Overview | Task in progress | Task completed |
|-|-|-|
|![Overview](../main/images/Overview.png)|![Task in Progress](../main/images/Task_In_Progress.png)|![Task Completed](../main/images/Task_Completed.png)|
| Creation | | |
|![Creation](../main/images/Creation_Section.png)| | |

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

   
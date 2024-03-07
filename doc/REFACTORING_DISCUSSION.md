
## Refactoring Lab Discussion
### TEAM 07
### NAMES Noah Loewy, Abhishek Chatuat, Yash Gangavarapu, Bodhi Ravipati


## Principles and Patterns

### Interface-Segregation Principle

* Restatement of Principle or Slogan:

Slogan: If you don't need it, don't rely on it!

* Uses in project (current or aspirational)

One example of the Interface Segregation Principle in our project is the use of

### Dependency Inversion Principle

* Restatement of Principle or Slogan

Abstractions should be independent of concrete definitions

* Uses in project (current or aspirational)

One example of this is the Command Interface in the model. The command interface is independent of its concrete implementations. Regardless of the kind of command you are building, the factory will call a new command and call the same public method execute, which is formatted the same across all concrete implementations.

### Design Pattern 1

* Name:

Factory Pattern

* Classes collaborating to implement it

CommandFactory, Command

* Differences from the canonical Design Pattern

The canonical Factory Pattern typically involves an interface or an abstract class defining a method for creating objects, with concrete classes implementing this method to instantiate objects of different types. However, in this implementation, there isn't a separate interface or abstract class defining the factory method. Instead, the createCommand method in the CommandFactory class directly creates instances of Command objects using reflection

* Trade-offs

Instantiating using reflection and the factory pattern allows for dynamic creation of objects based on runtime information. This makes the factory adaptable to changes, as new command classes can be added without modifying the factory code. The use of reflection can make the code less intuitive and harder to debug, since you get Invocation Exceptions as opposed to the specific ones.


### Design Pattern 2

* Name: Listener

* Classes collaborating to implement it

SlogoListener, MainScreen, called by the commands

* Differences from the canonical Design Pattern

The canonical Observer/Listener Pattern has a subject and observer, with the subject having a list of observer. Here, there is only one observer, the Main Screen, which implements Slogolistener and is able to act when changes are made. It is notified of these changes by the commands, so they can be executed in the front end on the fly. the Subject (or Observable) and the Observer.

* Trade-offs

The Observer Pattern promotes less tight coupling between the observed and observer objects. The model doesn't need to have direct knowledge of the observers, allowing for easier maintenance and scalability. This allows us to maintain some sense of model view separation, as the model does not know that the view is implementing it.  A downside is that it can lead to a lot of observer methods needing to be implemented, which can make the API harder to maintain.



# Refactoring Plan

## Issues in Current Code

### Method or Class: Mainscreen
* Design issues
  The class is 900 lines long. This is a violation of Single Responsibility Principle
* Design issue
  There is tight coupling with the Controller class. It is problematc that the Controller has a Mainscreen instance variable, and the  Mainscreen has a Controller instance variable.

### Method or Class: Ask Command and Askwith Command
* Design issues
  DRY. The code is repetitive and they share a lot of functionalities. One fix could be to make them both extend the same abstract class, which will allow for users in the future to only need to modify things once.
* Design issue
  Long methods. It could be helpful to break down the execute method into private helper methods, perhaps one for getting the turtles that are "asked", and another to actually have them execute the command.



## Refactoring Plan

* What are the code's biggest issues?
  The primary issue we see are the Mainscreen. It is really poorly formatted code, even though the functionality is there. There's a minor issue with the way deeply nested commands involving multiple turtles are dealt with, but this is a much lower priority.
* Which issues are easy to fix and which are hard?
  The main screen is probably the hardest to fix, as these changes are the most widespread and have aggregated overtime. Changes like the removing repeated code and fixing the boolean "hack" are smaller as they have not had time to aggregate, but they are also much less critical.
* What are good ways to implement the changes "in place"?
  One good way to implement the changes in place would be to use branches. We can make feature branches as opposed to branches with our names on it, so we can tinker with fixes and then only merge them if they work, without affecting everything else.


## Refactoring Work

* Issue chosen: Fix and Alternatives

Main Screen. We are currently trying to break it up into separate classes. We are doing this by outlining the different functionalities and components, and then making new classes for all of them. Due to the large magnitude of this problem, we did not have the time to address anything else in lab.
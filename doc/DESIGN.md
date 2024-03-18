# DESIGN Document for SLOGO
### Team 5
### Names: Noah Loewy, Bodhaansh Ravipati, Abhishek Chataut, Yash Gangavarapu


## Team Roles and Responsibilities

* Team Member #1: **Noah Loewy**

Responsible for design, and entirety of the implementation, testing, documenting, and debugging of 
all classes in the model package. This includes the ```Command```, ```Parser```, ```Node```,
```Listener```, ```Model```, ```SlogoException``` classes, as well as all of their concrete
implementations. Helped with view animation and turtle override as well.

* Team Member #2: **Bodhaansh Ravipati**

Responsible for majority of design, implementation, documenting, and debugging of the View. 
Developed the ```Screen```, ```Controller```, ```StartScreen```, ```MainScreen```, and all 
front-end related features during the basic portion of the project. Implemented multiple turtles,
interative tells, and interactive variable editing.

* Team Member #3: **Abishek Chatuat**

Responsible for implementing and debugging the ```NoCodeSlogo``` features. Refactored the 900 
line ```MainScreen``` class into well-organized helper classes, leading to creation of 
```HistoryBox```, ```InputBox```, and ```ComboChoice```. Wrote all test cases for the View 
package. 

* Team Member #4: **Yash Gangavarapu**

Responsible for handling configuration files and help XMLs. Wrote and documented all configuration 
files and code relating to the ```Help``` and ```Save``` classes, which allowed for handling of 
preferences, the help documentation, and loading/saving of files. Also played an integral role 
in initial design stages.


## Design Goals

* Goal #1: **Model-View Separation**: we wanted to employ Design Patterns such as the Observer 
  Pattern to completely encapsulate the model and its underlying components from the view. This 
  enables a clear separation of concerns, and also makes the model significantly easier to test, 
  as it is not reliant on external components.

* Goal #2: **Interaction Through APIs**: we wanted to ease the burden of communication between 
  classes and each other by designing strong APIs. This allowed us to treat other portions of 
  the code as "black-boxes" without worrying about underlying implementation details.

* Goal #3: **Reduce Repeated Code Via Abstractions**: a major goal of ours was extendability, 
  and we attempted to achieve this by adding abstractions wherever possible and necessary. This 
  significantly simplifies our code base, and also creates a very understandable hierarchical 
  structure.  


#### How were Specific Features Made Easy to Add

* Feature #1

* Feature #2

* Feature #3


## High-level Design

#### Core Classes and Abstractions, their Responsibilities and Collaborators

* Class #1: **MainScreen**

The Main Screen contains all the components of the IDE that are visible to the user, as well as 
options for text and button input from the user. This class is responsible for setting up the
application window, initializing various user interface components such as the command input box,
variable display, and turtle graphics pane, and managing the animation timeline. Additionally, it
implements the ```SlogoListener``` interface to respond to updates from the SLogo model, including
turtle movements, variable changes, and user-defined commands, and then makes those changes visible
to the user.

* Class #2: **Model / Parser Classes**

The model class is the one directly being called by the view. However, all of its public methods are
void, so it only can interact with the view through listeners. The parser class, which is only
called by the model in one particular place, converts a user defined command into a tree of Nodes,
which is then evaluated in the manner of a depth-first traversal.

* Class #3: **Command Interface and its implementations**

The Command Interface is a functional interface with more than 50 implementations, one for each
command. Its sole method is ```execute```, which takes in a modelState (to perhaps be modified), and
a list of arguments (as commands are parameterized). The execution of a command represents
completing a task or subtask requested in the user interface, by updating the corresponding model
state in the backend, and updating all listeners (in this case, the front end), of any changes to
the model state. Each Command is created in the encapsulated ```CommandFactory``` class.

* Class #4: **Node Abstract Class and its implementations**

The Node class and its concrete implementations represent the different types of syntax in Slogo.
Each node has an evaluate method, which is implemented differently based on the type of Node. For
example, ```VariableNodes``` involve accessing the variable's name from a map, ```CommandNode```
involves creating a ```Command``` from the ```CommandFactory``` and executing it,
and ```CommandCreatorNode``` involves adding a new type of Command to the ```ModelState``


## Assumptions or Simplifications

* Decision #1

* Decision #2

* Decision #3

* Decision #4


## Changes from the Original Plan

* Change #1

* Change #2

* Change #3

* Change #4


## How to Add New Features

#### Features Designed to be Easy to Add

* Feature #1

* Feature #2

* Feature #3

* Feature #4


#### Features Not Yet Done

* Feature #1: **Replay Animation**

Since each turtle stores both its state history and its path history, undoing and redoing animations 
can be done by simply pushing and popping items off of these stacks. Alternatively, the actual
animation objects can be stored along with the old states, so that replaying animations involves
recreating the animation sequence from scratch using the stored data. This approach offers more 
flexibility and control over the replay process.

* Feature #2

* Feature #3

# Cell Society API Lab Discussion
### Noah, Abhishek, Bodhi, Yash
### 5

## Current Model API

### External

* Identified Public Classes/Met hods

- Classes
    - All subclasses of Simulation,Neighborhood, as well as super class.

- Methods
    - ResetParams method
    - Simulation transition method

### Internal

* Identified Classes/Methods

- Classes
  Grid class
  Simulation classes
- Shape classes

- Shape Interface
- Simulation class

- Methods
  Shape.getVertices(), Cell.getTransition(), Cell.setParameters()., agentHere(), setNextState() Grid class public methods


## Wish Model API

### External

* As few classes and methods as possible should be external to limit user error. The View should have access to as little model data as possible.

* Abstractions and their Methods

Shape Interface should be public external, because the View needs it for shape construction and the model needs it to get neighbors.

* Services and their Contract

The Simulation constructor should remain external to allow the View to function. The setParams() function should also remain external to allow the View to dynamically modify data.

Transition in Simulation should remain external to allow the UI to project future states.

### Internal

* Most of user data in a class should be handled in itself. As few methods as possible should be made public and accessible to other classes, especially in different folders.

* Abstractions and their Methods

* Services and their Contract

Shape.getVertices() should stay internal, Cell.setParameters(),
All the cell class methods should be internal except getCurrentState() and getVertices().

Grid class and its public methods should stay internal, because the View can access its day through an iterator instead of all the implementation detail methods.

Everything else in the Simulation class must remain internal except the constructor and setParams() to prevent user error.

All the subclasses of every abstraction should be moved to internal. None of these should be external, because the UI does not inheritance details.

**agentHere(), setNextState() should not be either internal or external.

## API Task Description

### External

* English
    * We want programmers to think of the process of using the API as a step-by-step process.
      The API should be easy to use and understand so that the user can know what to use and when to use
      within their own code.
    * We would use the external API in Cel Society to create the view and to set how the simulation should look and
      play out for the user.

* Java
    * Creating general simulation - public void makeSimulation()
    * Calling the update or transition function to update the states -

### Internal

* English
    * Within cell society, we would use the internal API to create new simulations, and initialize and
      set the parameters for it, determining what the actual changes for each cell should be.

* Java
    * We would call the concrete implementation of Simulations
    * Creating new Neighborhood, Shape objects
    * Transition function where logic of a "step in time" actually occurs

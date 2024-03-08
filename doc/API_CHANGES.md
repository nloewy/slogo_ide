## SLogo API Changes

### Team: SLOGO 07

### Names: Noah L, Bodhi R, Abhishek C, Yash G

### Changes Made

#### External Model API

* Method changed: (REMOVED)

```java
  File loadSlogo(String path);

File readSlogo(String path);
```

I grouped the above together as they were done for the same reason.

* Why was the change made?

The change was made to move the file reading functionality out of the external model API and into
the view / controller. This decision was inspired by the concept of model-view separation, as we saw
no need for the model layer should not be responsible for handling external file operations. By
relocating these methods, the model layer remains focused on its core responsibilities (parsing and
execution). These files being read/written are either pre-parsing/execution, or
post-parsing/execution, so there is no need for the model to know its implementation.

* Major or Minor (how much they affected your team mate's code)

This is a minor change as these methods were not implemented when the change was made. Furthermore,
the only change was that the controller/view would now be calling an internal method, as opposed to
an external method, but the methods structure remained the same.

* Better or Worse (and why)

Better. By removing file reading and writing from the external model API and placing it in the view
or controller, the codebase becomes more modular, maintainable, and scalable. Additionally, it
aligns with the SOLID design principles we discussed in class, as well as encapsulation and model
view separation.

* Method changed: (ADDED TWO PARAMETERS TO SIGNATURE)

```java
public record TurtleRecord(int id, double x, double y, boolean pen, boolean visible, double heading,
                           int bgIndex, int penColorIndex)
```

* Why was the change made?
  The change was made to accommodate the need to include additional parameters (bgIndex and
  penColorIndex) in the TurtleRecord to pass necessary information from the model to the listener (
  view). We did not expect this information to be stored in the model, due to the fact that colors
  are typically considered UI/View. However, because these can be altered through parseable
  commands, the model needs to play a role here, so these fields need to be added.

* Major or Minor (how much they affected your team mate's code)
  This is a major change because it affects the API call that my teammates working on the view have
  been relying on. They now need to update their code to handle the two new fields (bgIndex and
  penColorIndex) when interacting with the TurtleRecord. However, they are still using the
  TurtleRecord object, so any calls to it would not change, and their previous code handling the
  other fields would not change either. So, this change would only require adding new code, not
  adjusting pre-existing code.

* Better or Worse (and why)
  By adding the bgIndex and penColorIndex parameters to the TurtleRecord constructor, the view can
  now access important information that has been parsed by the model, allowing for it to update the
  UI properly. Although it is burdensome to have to add more code to the view to account for this,
  it is necessary because it enhances the API's overall functionality.

#### Internal Model API

* Method changed: (ADDED)

```java
void onSetActiveTurtles(List<Integer> ids);

void onUpdatePalette(Map<Integer, List<Integer>> palette);
```

    * Why was the change made?

These changes were made to enhance the internal API to facilitate communication between the
observer/view and the model. The addition of the onSetActiveTurtles method allows the observer/view
to be notified when the set of active turtles changes, enabling it to update the palette colors
accordingly. Similarly, the onUpdatePalette method allows the model to provide the observer/view
with the updated palette information. The need for this sort of functionality was not forseen during
the basic requirements.

    * Major or Minor (how much they affected your team mate's code)

These are major changes as they involve adding two new methods to the InternalAPI (or in this case
the SlogoListener) interface. Any class that implements this interface would need to be updated to
provide implementations for these new methods. But, this was not too significant, since we could set
them to dummy (empty) methods at first to keep the code working, and then add the changes to their
implementation later.

    * Better or Worse (and why)

In the end, I believe this change was necessary for our team. By adding these methods to the
SlogoListener (part of internal API), the communication between the observer/view and the model
becomes more flexible, and the listener is able to be synchronized with the model, so that all of
the changes are made at the same time.

* Method changed: (ADDED)

```java
protected List<Integer> informTurtles(Node ids, ModelState modelState, SlogoListener myListener);

protected double runLoop(double start, double end, double increment, Node commands,
    ModelState modelState, String variableName);
```

* Why was the change made?

The changes were made to reduce the amount of DRY code in the model. I noticed that multiple
commands implemented similar functionality, such as looping structures or informing turtles.
Therefore, I added abstract classes and protected methods to the API in order to more effectively
encapsulate these shared behaviors. This addition was primarily done with the goal of reducing DRY
code.

* Major or Minor (how much they affected your team mate's code)

These changes are extremely minor because they primarily impact the internal implementation of the
model and a select few commands that extend the abstract class. While these changes do create new
abstract classes and protected methods that are in the internal API, they do not directly affect
anything pertaining to the view, as the view should never be aware of any command classes.

* Better or Worse (and why)

The changes are better for the codebase overall. By refactoring common functionality into abstract
classes and protected methods, the code becomes more modular and easier to maintain. Additionally,
it makes the code more extendable, so it follows the open-closed principle.

* Note:
  There were a few other changes made to the internal API that happened after change, but before I
  became aware we needed to document these. I spoke to Professor Duvall in lecture and he said it'd
  be better to not focus too much time on documenting these, but I wanted to briefly justify the
  changes.
 * Factory Class Addition: Following the design principle of separation of concerns, I introduced
   a separate factory class responsible for instantiating command objects. Initially, this
   functionality resided as a private method within the CommandNode class. However, to adhere more
   closely to the Single Responsibility Principle (SRP), I separated this responsibility into its
   own class for better encapsulation of information and improved maintainability.
* PaletteInitializer Class Addition: Similar to the Factory Class addition, the palette
  initialization process was initially done through a private method within the ModelState class.
  However, to try and keep the Parser as just a parser (single responsibility principle), and to
  encapsulate this functionality, I made a separate PaletteInitializer class. This separation of 
  ensures that the model state does not need to be aware of how the initial palette is received, 
  just that it exists.
* TurtleCommandNode Addition: By introducing another layer to the hierarchy of nodes, I aimed to
  adhere to the Open/Closed Principle (OCP) by making the system more extensible. This addition made 
  how commands are executed when they affect a singular, or multiple turtles indistinguishable to 
  clients Instead of managing these with conditional statements within the CommandNode class, the 
  addition of TurtleCommandNode adds to the abstraction heirarchy, without affecting any code of my
  teammates. The only change required was the parser needing to identify what type of CommandNode 
  would need to be instantiated.

`

#### Internal View API

* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)


* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)


# slogo

### Team: SLOGO 07
### Names: Noah L, Bodhi R, Abhishek C, Yash G



This project implements a simplified version of the Logo coding language.

### Timeline

 * Start Date: 2/22/24

 * Finish Date: 3/8/24

 * Hours Spent: 250+



### Attributions

 * Resources used for learning (including AI assistance)
 1. [Design Patterns class resource](https://www.oodesign.com_)
 2. [Examples of Observer Pattern](https://www.tutorialspoint.com/design_pattern/observer_pattern.htm)
 3. [Class Website](https://courses.cs.duke.edu/spring24/compsci308/classwork/)
 4. [In Class Reflection Example](https://coursework.cs.duke.edu/compsci308_2024spring/example_reflection)

 * Resources used directly (including AI assistance)

1. [Chat GPT](https://chat.openai.com/), a Large Language Model, was used to write some of the documentation for the individual commands.
2. [The teaching team](https://courses.cs.duke.edu/spring24/compsci308/admin/), namely Professor Duvall and Luke, were used to discuss overarching design principles

### Running the Program

 * Main class: 
   * src/main/java/slogo/Main.java

 * Data files needed: 
   * Path to Turtle Images: src/main/resources/turtleimages/
   * Path to Language Properties:src/main/resources/slogo/languages/
   * Path to Palette Properties:src/main/resources/slogo/palette/
   * Path to Help Documentation:data/helpXmls/
   * Path to Preferences XMLs: data/preferencesXmls/

 * Interesting data files:
    * In the examples data files there are a lot of cool and interesting data files that can be
   implemented in the program displaying the variety of different featurs that the program can do.
    * One of them that is interesting is the 'random_range.slogo' file that shows the random range
   command. However, in addition to the random range command, there are many other commands that 
   it works with to draw a completely random picture every time including making variables, to, fd,
   rt, and repeat commands.
   
    * The commands XML file is also interesting. This file is exceptionally long, but it maintains
   all the information necessary about all the commands that the program can run from the basic 
   movement commands to queries to booleans. This file is used to display the help documentation.
   There is also a comandos.xml file that is in spanish that is used to display the help
   documentation in spanish.

 * Key/Mouse inputs: 
   * The program mainly works through mouse inputs. The user can click on the buttons to run and 
   start the working IDE. It also needs the mouse input to access the buttons throughout within
   the program when its running such as the load, save, help, and submit/execute buttons. Also for
   dropdown menus. 
   * It uses mouse inputs also to directly interact with the turtle. The user can click on buttons
   to move the turtle and rotate it by a certain amount determined by them. The user can also hover
   over the turtle to get turtle specific information. If the user clicks on the turtle, the user
   will run a tell command.
   * Key inputs are important to type out and run commands. Also pressing enter when the user is 
   done typing out the command one way to execute a command.

### Notes/Assumptions

 * Assumptions or Simplifications:
   * Some assumptions that were made were that the center Turtle Pane would be constantly the same
   size. We didn't account for the possibility of the user wanting to change the size of the window.
   
 * Known Bugs: 
   * When exceptionally complex Slogo files are uploaded or implemented into the program, the 
   will work but take a second to process and start running.
   * When creating a new IDE from an already running IDE, it does not save or transfer over any
   set preferences for turtle image or pen color.
   * Reset button works and resets the IDE clearing all commands and drawings. However instead of
   resetting the current ide completely, it makes a new IDE and removes the old one.
   * When speed slider is set to max, complex commands are not completely executed and the turtle
   will skip over some parts of it.

 * Features implemented:  (NOT EXHAUSTIVE)
   * All commands from basic and change
   * NoSlogo Update via History/Variables/Buttons/Help Documentation / etc
   * Load/Save Files
   * Nearly 600 passing junit tests
   * Multiple workspaces 
   * Election themed turtle display

 * Features unimplemented:
   * Replay animation
   * Update pen size
   

 * Noteworthy Features: 
   * Parser/View can handle User Defined Commands 
   * Parser/View can handle multiple turtles
   * Partial Execution For Erroneous Commands
   * Gradual Line Animation
   * Step through Animation
   * Errors Displayed in Multiple Languages

### Assignment Impressions

This project was a great learning experience. It was a great way to learn about the different APIs
and interfaces specifically. It was really key to with expanding our experience and skills with
Model View Separation as well considering we know implemented a controller. The testing part, while
being a bit of a challenge, was also something great to practice and get better at. It was new but
pretty cool to see how it all worked and how it all came together. Overall, it was a good project
to work together on and learn to create clean and extendable code.



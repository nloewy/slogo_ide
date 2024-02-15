# SLogo API Design Lab Discussion
### Noah, Abhishek, Bodhi, Yash
### Team 5


## Planning Questions

* What behaviors (methods) should the Turtle have and what service should it provide?
    * The turtle should be able to have movement, going forward and back. It should turn. There turtle
      should leave behind, essentially drawing a line as it moves.
* When does parsing need to take place and what does it need to start properly?
    * Parsing needs to take place when the user inputs a command. It needs to start and move
      how the user inputs. Parsing requires a valid string or text with the right commands which we will
      use to have the turtle move.
* What is the result of parsing (not the details of the algorithm) and who receives it?
    * The result is a command that model will receive and execute causing the turtle to do.
* When are errors detected and how are they reported?
    * One error that is detected is when the user inputs a command that is not valid. This would be
      reported to the user immediately and they can go back. This would be also reported for the model and
      displayed in the view.
* What do different commands need to know, when do they know it, and how do they get it?
    * The different commands need to know the current state of the turtle and the environment. The command would then
      cause that to be updated. The commands would know this when the user inputs a command and the model receives it.
* What behaviors does the result of a command need to have to be used by the View?Â
    * The result of a command needs to have the behavior of updating the view. The view needs to be updated
      when the command is executed. The view needs to be updated to reflect the changes in the model.
* How is the View updated after a command has completed execution?
    * The view is updated after a command because the model will be updated and the view will be 
  updated to reflect the changes in the model.
    * The model gets the view, and then the view will know based on the model how to go and draw the result.
* What value would Controller(s) have in mediating between the Model and View?
    * The controller could be used when the user inputs a command, the controller will then send 
  that to the model, and then the model will update the view. The controller could also be used to 
  update the model based on the view and vice versa.

## Model APIs

### External

* Goals

* Contract

* Services



### Internal

* Goals

* Contract

* Services


### Alternate Designs Considered




## Design

### Model Design CRCs

This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](order_crc_card.png "Order Class")

This class's purpose or value is to represent a customer's order:

|Order| |
|---|---|
|boolean isInStock(OrderLine)         |OrderLine|
|double getTotalPrice(OrderLine)      |Customer|
|boolean isValidPayment (Customer)    | |
|void deliverTo (OrderLine, Customer) | |

This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```

This class's purpose or value is to manage something:
```java
public class Something {
     // sums the numbers in the given data
     public int getTotal (Collection<Integer> data)
	 // creates an order from the given data
     public Order makeOrder (String structuredData)
 }
```



### Use Cases

* The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

* The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

* The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

* The user changes the color of the environment's background.

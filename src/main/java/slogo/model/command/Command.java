package slogo.model.command;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.node.Node;


/**
 * The Command interface represents a generic command to be executed in the turtle graphics
 * environment. The purpose of this interface is to define a common structure for all Slogo
 * commands, allowing for the details of each individual command to be abstracted away. Command
 * objects are created using reflection.
 * <p>
 * This interface is a functional interface, meaning that it only provides a single method, execute,
 * which accepts a list of nodes representing the arguments for the command. It often calls the Node
 * evaluate method, as well as model state getters/setters, and calls the listener.
 * <p>
 * In the context of Slogo, the arguments of a node are its children node.
 * <p>
 * Implementations of this interface define the behavior of specific commands
 * <p>
 * We assume the execute method expects a list of nodes representing the arguments for the command.
 * If the provided arguments are invalid or incompatible with the command's logic, it may throw
 * exceptions. These exceptions often come in the form of InvocationTargetExceptions (through the
 * reflection), but are often IllegalOperandExceptions or InsufficientArgumentExceptions that were
 * not caught during parsing.
 * <p>
 * To use the Command interface, you can create concrete implementations for specific commands. For
 * example:
 * <pre>{@code
 * public class SumCommand implements Command {
 *     public double execute(List<Node> arguments) {
 *         return arguments.get(0).evaluate() + arguments.get(1).evaluate();
 *     }
 * }
 * }</pre>
 */

@FunctionalInterface
public interface Command {

  /**
   * Executes the command with the given arguments, updating the ModelState and calling any
   * listeners as needed.
   *
   * @param arguments a list of nodes representing the arguments for the command
   * @param index
   * @return the result of executing the command
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */

  double execute(List<Node> arguments, int index) throws InvocationTargetException, IllegalAccessException;
}

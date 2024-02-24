/**
 * package slogo.model.command.control;
 * <p>
 * import java.lang.reflect.InvocationTargetException; import java.util.List; import java.util.Map;
 * import java.util.function.Function; import slogo.model.ModelState; import slogo.model.Node;
 * import slogo.model.Turtle; import slogo.model.command.Command;
 * <p>
 * public class MakeCommand extends Command {
 * <p>
 * private final Turtle myTurtle; private final Map<String, Double> myVariables;
 * <p>
 * public MakeCommand(Turtle turtle, Map<String, Double> variables) { myTurtle = turtle; myVariables
 * = variables; }
 * <p>
 * public Function<ModelState, Double> execute(List<Node> arguments) throws
 * InvocationTargetException, IllegalAccessException {
 * <p>
 * return 0; }
 * <p>
 * /** public void notifyListener(SlogoListener listener, double value) {
 * super.notifyListener(listener, value); }
 * <p>
 * }
 */

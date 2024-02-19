
/*
 * SLOGO-59
 */
public class InvalidCommand {

    /*
     * Tied to a button in View that sends 
     * the input String to the Controller.
     */
    public void submitCommands() {
        //Takes in the raw String of commands
        controller.pushCommands(commands);

        // Generates a list of Commands from the raw text
        parser.parse(commands);

        // THROWS InvalidCommandException

        // Submits an alert saying there was an error
        new Alert("Invalid Command");
    }
}

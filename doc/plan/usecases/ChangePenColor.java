
/*
 * SLOGO-71
 * @author Bodhaandh Ravipati
 */
public class ChangePenColor {

    /*
     * This method is called as an event handler to a button in View. 
     * There will be a dropdown, and the selection will be passed to 
     * this method.
     */
    public void switchColor(Color newColor) {

        // Instance method in Controller that does the following
        controller.setPenColor(newColor);

        // method called on the instance of View in controller
        view.setPenColor(newColor);
    }
}

public class LightModeToDark {

    /*
     * Internal method in View, 
     * tied as an event handler to 
     * a switch theme button.
     */
    public void switchTheme() {

        // Static method in main that does the following
        Main.switchTheme();

        // current theme is a variable handled in Main
        scene.getStylesheets().remove(currentTheme);

        // new theme such as a dark mode
        scene.getStylesheets().add(newTheme);
    }
}

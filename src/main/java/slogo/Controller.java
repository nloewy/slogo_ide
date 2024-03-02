package slogo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.stage.Stage;
import slogo.model.SlogoModel;
import slogo.model.api.InvalidCommandException;
import slogo.model.api.InvalidTokenException;
import slogo.model.api.Model;
import slogo.view.View;
import slogo.view.pages.StartScreen;

//TODO add method to add user
//defined commands call it in parse try catch
public class Controller {
    private Stage stage;
    private String currentLanguage = "English";
    private Consumer<String> parse;
    private List<View> windows = new ArrayList<>();

    public Controller(Stage stage) throws IOException {
        this.stage = stage;
        openStartScreen();
    }

    public void openStartScreen() throws FileNotFoundException {
        StartScreen startScreen = new StartScreen(this);
        startScreen.setUp();
        stage.setScene(startScreen.getScene());
        stage.show();
    }

    public void openBlankIDESession() throws IOException {
        Stage newStage = new Stage();
        newStage.setMaximized(true);
        View view = new View(this, stage);
        Model model = new SlogoModel(view);
        windows.add(view);

        parse = t -> {
            try {
                model.parse(t);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvalidCommandException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvalidTokenException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        };

        view.run(parse);
    }

    public void handleCommand(String command) {
//         Here, implement command handling logic
//        if ("MOVE UP".equalsIgnoreCase(command)) {
//            view.moveTurtleUp(); If it was in view
//        But for otherwise it run another class that will get from model side

//        }
    }

    public void setCurrentLanguage(String language) {
        this.currentLanguage = language;
        for (View view : windows) {
            view.setLanguage(currentLanguage);
        }
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void loadSession() {
        System.out.println("To Be Implemented! Need to figure out how to move "
            + "file choosing from screen to controller");
    }

    public void openNewXMLSession() {
        System.out.println("To Be Implemented!");
    }

}

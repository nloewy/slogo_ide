package slogo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import slogo.model.SlogoModel;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.api.Model;
import slogo.model.api.SlogoException;
import slogo.view.ViewInternal;
import slogo.view.pages.MainScreen;
import slogo.view.pages.StartScreen;

//TODO add method to add user
//defined commands call it in parse try catch
public class Controller {

  private final Stage stage;
  private String currentLanguage = "English";
  private String currentTheme = "LightMode.css";
  private final List<Consumer<String>> languageObservers = new ArrayList<>();
  private Consumer<String> parse;
  private final List<MainScreen> windows = new ArrayList<>();
  private File turtleImage;
  private String uploadedCommand;

  public Controller(Stage stage) throws IOException {
    this.stage = stage;
    openStartScreen();
  }

  public void setTurtleImage(File i) {
    turtleImage = i;
  }

  public void openStartScreen() throws FileNotFoundException {
    StartScreen startScreen = new StartScreen(stage, this);
    startScreen.setUp();
    setCurrentLanguage(currentLanguage);
    stage.setScene(startScreen.getScene());
    stage.show();
  }

  public void addLanguageObserver(Consumer<String> observer) {
    if (observer != null) {
      languageObservers.add(observer);
    }
  }

  private void updateLanguageObservers() {
    for (Consumer<String> observer : languageObservers) {
      observer.accept(currentLanguage);
    }
  }

  public String getCurrentLanguage() {
    return currentLanguage;
  }

  public void setCurrentLanguage(String language) {
    this.currentLanguage = language;
    updateLanguageObservers();
  }

  public void updateCurrentTheme(Scene scene) {
    scene.getStylesheets().clear();
    scene.getStylesheets()
        .add(Objects.requireNonNull(ViewInternal.class.getResource(currentTheme)).toExternalForm());
  }

  public void setCurrentTheme(String theme, Scene scene) {
    this.currentTheme = theme;
    updateCurrentTheme(scene);
  }

  public void openNewIDESession(String slogoContent) throws IOException {
    Stage newStage = new Stage();
    newStage.setMaximized(false);
    MainScreen mainScreen = new MainScreen(newStage, this);
    if (turtleImage != null) {
      mainScreen.setTurtleImage(turtleImage);
    }
    Model model = new SlogoModel(mainScreen, currentLanguage);
    windows.add(mainScreen);

    parse = t -> {
      try {
        model.parse(t);
      } catch (ClassNotFoundException | InvocationTargetException |
               NoSuchMethodException | InstantiationException | IllegalAccessException |
               NoSuchFieldException | InvalidOperandException |
               SlogoException e) {
        new Alert(AlertType.ERROR, e.getMessage()).show();
      }
    };

    mainScreen.addParser(parse, slogoContent);
    setCurrentLanguage(currentLanguage);
  }

  public void loadSession(String type) {
    // This method may need to use the Model api -- loadXML and others
    // This opens a file chooser. Once the file is chosen, it opens a new session
    // The file is not currently used for anything
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open SLogo File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("SLogo Files", "*.slogo"));
    File selectedFile = fileChooser.showOpenDialog(stage);
    if (selectedFile != null) {
      System.out.println("Selected file: " + selectedFile.getAbsolutePath());
      try {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
          String line;
          while ((line = br.readLine()) != null) {
            contentBuilder.append(line).append("\n");
          }
        }
        String slogoContent = contentBuilder.toString();
        System.out.println(slogoContent);

        if (type.equals("new")) {
          openNewIDESession(slogoContent);
        } else {
          setSlogoContent(slogoContent);
        }
//                openFileIDESession(slogoContent);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void setSlogoContent(String slogoContent) {
    uploadedCommand = slogoContent;
  }
  public String getSlogoContent() {
    return uploadedCommand;
  }



  public void openNewXMLSession() {
    System.out.println("To Be Implemented!");
  }

  public Map<String, Map<String, String>> getCommandDetailsFromXML() {
    Map<String, Map<String, String>> commandDetails = new HashMap<>();
    try {
      File inputFile = new File("data/helpXmls/commands.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("command");
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          String commandName = eElement.getElementsByTagName("canonicalName").item(0).getTextContent();
          String description = eElement.getElementsByTagName("description").item(0).getTextContent();
          String example = eElement.getElementsByTagName("example").item(0).getTextContent();
          Element helpDocumentation = (Element) eElement.getElementsByTagName("helpDocumentation").item(0);
          String parameters = helpDocumentation.getElementsByTagName("parameters").item(0).getTextContent();
          String returnValue = helpDocumentation.getElementsByTagName("returnValue").item(0).getTextContent();
          Map<String, String> details = new HashMap<>();
          details.put("description", description);
          details.put("example", example);
          details.put("parameters", parameters);
          details.put("returnValue", returnValue);
          commandDetails.put(commandName, details);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return commandDetails;
  }


}

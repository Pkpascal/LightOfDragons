package project.lightofdragons;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class LaunchGame extends Application {
  @Override
  public void start(Stage stage) throws IOException {

    FXMLLoader fxmlLoader = new FXMLLoader(LaunchGame.class.getResource("gameView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Light of Dragons");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> {
      event.consume();
      logout(stage);
    });
  }

  public static void main(String[] args) {
    GameController game = new GameController();
     launch();
  }

  public static void logout(Stage stage){

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close");
    alert.setHeaderText("You're about to Close the game");
    alert.setContentText("Are you sure?");

    if(alert.showAndWait().get() == ButtonType.OK) {
      stage.close();
    }
  }

  public static void saveGame(){
    //TODO Save game
  }

  public static void loadGame(){
    //TODO load game
  }
}
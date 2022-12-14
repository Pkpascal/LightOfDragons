package project.lightofdragons;

import Classes.BattleField;
import Classes.Dice;
import Classes.Field;
import Classes.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class GameController implements Initializable {

  @FXML
  GridPane UIbattlefield;

  @FXML
  Label outputLabel, pl1Label, pl2Label, pl1ColorLabel, pl2ColorLabel;
  @FXML
  Button moveButton, upgradeButton, magicButton, newDiceButton, endTurn, magicPoints;
  @FXML
  FlowPane exilePane, player1FP, player2FP;
  @FXML
  //Player Points Label
  Label sPLabel1, sPLabel2, mPLabel1, mPLabel2;
  @FXML
  MenuButton anleitungButton;

  Stage anlStage;
  ImageView anl1Image;
  ImageView anl2Image;
  ImageView anl3Image;
  ImageView anl4Image;
  ImageView anl5Image;

  {
    try {
      anl1Image = new ImageView(new Image(
          new FileInputStream("src/main/resources/project/lightofdragons/DWanleitung1.JPG")));
      anl2Image = new ImageView(new Image(
          new FileInputStream("src/main/resources/project/lightofdragons/DWanleitung2.jpg")));
      anl3Image = new ImageView(new Image(
          new FileInputStream("src/main/resources/project/lightofdragons/DWanleitung3.jpg")));
      anl4Image = new ImageView(new Image(
          new FileInputStream("src/main/resources/project/lightofdragons/DWanleitung4.jpg")));
      anl5Image = new ImageView(new Image(
          new FileInputStream("src/main/resources/project/lightofdragons/DWanleitung5.jpg")));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  BattleField battleField = new BattleField();

  Field selectedField;

  public Field getSelectedField() {
    return selectedField;
  }

  Dice selectedDice;

  Player player1 = new Player();
  Player player2 = new Player();
  Player aktPlayer;

  StackPane[][] panes = new StackPane[7][7];

  public GridPane getUIbattlefield() {
    return UIbattlefield;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    player1.setGame(this);
    player2.setGame(this);
    player1.setColor(Settings.pl1Color);
    player2.setColor(Settings.pl2Color);
    pl1ColorLabel.setStyle("-fx-background-color: red "); //TODO
    pl2ColorLabel.setStyle("-fx-background-color: black ");
    outputLabel.setText("");


    aktPlayer = player1;
    pl1Label.setStyle("-fx-text-fill :yellow");
    updateLabels();

    for (int i = 1; i <= Settings.maxDice; i++) {
      player1FP.getChildren().add(new Rectangle(25, 25, player1.getColor()));
      player2FP.getChildren().add(new Rectangle(25, 25, player2.getColor()));
    }

    for (int x = 0; x < 7; x++) {
      for (int y = 0; y < 7; y++) {
        panes[x][y] = new StackPane();
        panes[x][y].setAlignment(Pos.CENTER);
        int finalY = y;
        int finalX = x;
        panes[x][y].setOnMouseClicked(pc -> {
          if (battleField.getBattlefield()[finalX][finalY].getDice() != null &&
              battleField.getBattlefield()[finalX][finalY].getDice().getPlayer()
                  .equals(aktPlayer)) {
            deselectAll();
            selectedDice = battleField.getBattlefield()[finalX][finalY].getDice();
            panes[finalX][finalY].setStyle("-fx-border-color : blue");
          } else {
            deselectField();
            selectedField = battleField.getBattlefield()[finalX][finalY];
            panes[finalX][finalY].setStyle("-fx-border-color : blue");
          }

        });

      }
    }
    for (int x = 0; x < 7; x++) {
      for (int y = 0; y < 7; y++) {
        UIbattlefield.add(panes[x][y], x, y);
      }
    }

    moveButton.setOnMouseClicked(mB -> UIMove());
    newDiceButton.setOnMouseClicked(nD -> newDice());
    magicButton.setOnMouseClicked(aB -> magicButton());
    upgradeButton.setOnMouseClicked(uB -> upgradeButton());
    endTurn.setOnMouseClicked(eT -> endTurn());
    magicPoints.setOnMouseClicked(mP -> magPointUpgrade());

    anleitungButton.setOnMouseClicked(aB -> showAnleitung());

    moveButton.setDisable(true);
    upgradeButton.setDisable(true);
    magicButton.setDisable(true);


    exilePane.setVgap(5);
    exilePane.setHgap(5);

    //Anleitungs Stage

    anlStage = new Stage();
    anlStage.setOnCloseRequest(event -> {
      event.consume();
      anlStage.close();
    });
    ScrollPane scrollPane = new ScrollPane();

    VBox main = new VBox();
    main.setFillWidth(true);
    main.setAlignment(Pos.CENTER);

    scrollPane.setContent(main);

    Scene scene = new Scene(scrollPane, 600, 550);
    anlStage.setScene(scene);
    anl1Image.setFitWidth(600);
    anl1Image.setFitHeight(600);
    anl2Image.setFitWidth(600);
    anl2Image.setFitHeight(600);
    anl3Image.setFitWidth(600);
    anl3Image.setFitHeight(600);
    anl4Image.setFitWidth(600);
    anl4Image.setFitHeight(400);
    anl5Image.setFitWidth(600);
    anl5Image.setFitHeight(600);

    main.getChildren().addAll(anl1Image, anl2Image, anl3Image, anl4Image, anl5Image);

  }


  private void deselectField() {
    if (selectedField != null) {
      panes[selectedField.getXCoord()][selectedField.getYCoord()].setStyle(
          "-fx-border-color : null ");
      selectedField = null;
    }
  }

  private void deselectAll() {
    if (selectedField != null) {
      panes[selectedField.getXCoord()][selectedField.getYCoord()].setStyle(
          "-fx-border-color : null ");
      selectedField = null;
    }
    if (selectedDice != null) {
        panes[selectedDice.getField().getXCoord()][selectedDice.getField().getYCoord()].setStyle(
            "-fx-border-color : null ");
        selectedDice = null;
    }
  }

  private void showAnleitung() {
    anlStage.show();
  }

  private void newDice() {
    if (selectedField != null) {
      int x = selectedField.getXCoord();
      int y = selectedField.getYCoord();

      if (y == 0 || y == 6) {
        if (selectedField.getDice() == null) {
          aktPlayer.newDice(x, y);
          disableAll(true);
        } else {
          outputLabel.setText("Place already taken");
        }
      } else {
        outputLabel.setText("Placement only in first row Allowed");
      }
    } else {
      outputLabel.setText("No field selected");
      return;
    }


  }

  private void UIMove() {

    if (selectedField != null && selectedDice != null) {

      int x2 = selectedField.getXCoord();
      int y2 = selectedField.getYCoord();

      if (aktPlayer.equals(selectedDice.getPlayer())) {
        disableAll(selectedDice.moveValid(x2, y2));
        deselectAll();
      } else {
        outputLabel.setText("Not your Die");
      }

    } else {
      outputLabel.setText("No die or field selected");
    }
  }

  public Pane[][] getPanes() {
    return panes;
  }

  public Label getOutputLabel() {
    return outputLabel;
  }

  public void setBattleField(BattleField battleField) {
    this.battleField = battleField;
  }

  public BattleField getBattleField() {
    return battleField;
  }

  public void removeDiceOn(int x, int y) {
    panes[x][y].getChildren().clear();
  }

  public void magicButton() {

    if (selectedDice != null) {  //field empty?

      if (aktPlayer.equals(selectedDice.getPlayer())) { //players Dice?

        if (selectedDice.hasMagic()) { //dice has Magic ability?
          selectedDice.magic(); //activates magic ability
          disableAll(true);
          deselectAll();
        } else {
          outputLabel.setText("Dice has no magic");
        }
      } else {
        outputLabel.setText("Not your dice");
      }
    } else {
      outputLabel.setText("No dice on this Field");
    }
  }

  /**
   * allows the aktPlayer to upgrade the selected die
   */
  public void upgradeButton() {

    if (selectedDice != null) {
      if (aktPlayer.equals(selectedDice.getPlayer())) {

        disableAll(!selectedDice.upgrade());  //Only disables input if upgrade was succesfull
        deselectAll();
      } else {
        outputLabel.setText("Not your dice");
      }
    } else {
      outputLabel.setText("No dice on this Field");
    }
  }

  private void magPointUpgrade() {
    if (selectedDice != null) {
      if (aktPlayer.equals(selectedDice.getPlayer())) {
        if(aktPlayer.getmPoints()>0) {

          selectedDice.upgrade();  //Only disables input if upgrade was succesfull
          deselectAll();
        }else{
          outputLabel.setText("Not enough Magic Points");
        }
      } else {
        outputLabel.setText("Not your dice");
      }
    } else {
      outputLabel.setText("No dice on this Field");
    }
  }
  /**
   * changes the aktPlayer and the Labels showing the active Players
   * checks for status of game (starting or main phase)
   */
  public void endTurn() {
    if (!player1.canCreateNewDie() && !player2.canCreateNewDie()) {
      if (aktPlayer.equals(player1)) {
        pl1Label.setStyle("-fx-text-fill :black");
        pl2Label.setStyle("-fx-text-fill :yellow");
        aktPlayer = player2;
      } else {
        pl1Label.setStyle("-fx-text-fill :yellow");
        pl2Label.setStyle("-fx-text-fill :black");
        aktPlayer = player1;
      }
      disableAll(false);
      deselectAll();
      outputLabel.setText("");
      checkRuins();
    } else {
      if (aktPlayer.equals(player1) && player2.canCreateNewDie()) {
        pl1Label.setStyle("-fx-text-fill :black");
        pl2Label.setStyle("-fx-text-fill :yellow");
        aktPlayer = player2;
        disableAll(false);
      } else if (aktPlayer.equals(player2) && player1.canCreateNewDie()) {
        pl1Label.setStyle("-fx-text-fill :yellow");
        pl2Label.setStyle("-fx-text-fill :black");
        aktPlayer = player1;
        disableAll(false);
      }
      deselectAll();
      selectedDice = null;
      selectedField = null;
      disableAll(false);
      outputLabel.setText("");
      checkRuins();

    }
  }

  public FlowPane getExilePane() {
    return exilePane;
  }

  /**
   * Disables the Buttons depending on the Status of the game (Starting and Moving Phase)
   * makes sure a Player cannot do more than 1 action per turn
   */
  public void disableAll(Boolean dis) {
    if (aktPlayer.canCreateNewDie()) {
      newDiceButton.setDisable(dis);
      moveButton.setDisable(true);
      upgradeButton.setDisable(true);
      magicButton.setDisable(true);
    } else {
      moveButton.setDisable(dis);
      upgradeButton.setDisable(dis);
      magicButton.setDisable(dis);
    }
    updateLabels();
  }

  //Updates the Labels for the two players
  public void updateLabels() {
    sPLabel1.setText("" + player1.getsPoints());
    sPLabel2.setText("" + player2.getsPoints());
    mPLabel1.setText("" + player1.getmPoints());
    mPLabel2.setText("" + player2.getmPoints());
  }

  /**
   * Ends the game
   *
   * @param player the player that won
   */
  public void endGame(Player player) {
    disableAll(true);
    magicPoints.setDisable(true);
    endTurn.setDisable(true);
    if (player.equals(player1)) {
      outputLabel.setText("Player 1 won");
    } else {
      outputLabel.setText("Player 2 won");
    }
  }

  //Checks the 3 ruins fields for their dice.
  public void checkRuins() {
    if (Settings.ruinsActive) {   //Settings for winning with ruins
      if (battleField.getBattlefield()[5][1].getDice() != null &&   //A die on every ruin field
          battleField.getBattlefield()[3][3].getDice() != null
          && battleField.getBattlefield()[5][1].getDice() != null &&
          battleField.getBattlefield()[1][5].getDice() != null) {
        if (battleField.getBattlefield()[5][1].getDice().getPlayer() //every die from same player
            .equals(battleField.getBattlefield()[3][3].getDice().getPlayer())
            && battleField.getBattlefield()[5][1].getDice().getPlayer()
            .equals(battleField.getBattlefield()[1][5].getDice().getPlayer())) {
          endGame(battleField.getBattlefield()[5][1].getDice().getPlayer());
        }
      }
    }
  }

  public void newDiceCreated() {
    if (aktPlayer == player1) {
      player1FP.getChildren().remove(0, 1);
    } else {
      player2FP.getChildren().remove(0, 1);
    }
  }

}
package Classes;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.lightofdragons.GameController;
import project.lightofdragons.Settings;

public class Player {
  Dice[] dices = new Dice[15];
  int diceCount = 0;
  int mPoints=0;
  int sPoints=0;
  int startingRankCount = 0;
  boolean canCreateNewDie = true;
  GameController game;
  Color color;

  public Player() {
  }

  public int getmPoints() {
    return mPoints;
  }

  public void setmPoints(int mPoints) {
    this.mPoints = mPoints;
  }

  public int getsPoints() {
    return sPoints;
  }

  public void setsPoints(int sPoints) {
    this.sPoints = sPoints;
    if(sPoints>= Settings.winningPoints){
      game.endGame(this);
    }
  }

  public void newDice(int x, int y){
    if (diceCount< Settings.maxDice) {

      Stage stage = new Stage();

      FlowPane main = new FlowPane();
      main.setAlignment(Pos.CENTER);

      Scene scene = new Scene(main);
      stage.setScene(scene);

      Label rankLabel = new Label();
      rankLabel.setText("Rank of Dice :");
      TextField input = new TextField();
      Button accept = new Button();
      accept.setText("Accept");
      accept.setOnMouseClicked(aB -> {
        int inputRank = Integer.parseInt(input.getText());
        if(startingRankCount+inputRank <= Settings.startingPoints){
          startingRankCount +=inputRank;
          dices[diceCount] = new Dice(this,inputRank);
        dices[diceCount].setField(x,y);
        diceCount++;
      stage.hide();
      if(startingRankCount== Settings.startingPoints){
        canCreateNewDie = false;
      }
        }
        else{
          game.getOutputLabel().setText("Rank to high");
        }
      });
      main.getChildren().addAll(rankLabel,input,accept);
      stage.show();
    }
    else{
      game.getOutputLabel().setText("No more Dice");
    }
  }


  public GameController getGame(){
    return game;
  }
  public void setGame(GameController ui){
    game = ui;
  }

  public Color getColor() {
    return color;
  }
  public void setColor(Color col){
    color = col;
    for(Dice die : dices){
      if(die!= null) {
      DropShadow diecolor = new DropShadow();
      diecolor.setColor(this.getColor());
        die.getInterfaceDice().setEffect(diecolor);
      }
    }
  }
  public boolean canCreateNewDie(){
    return canCreateNewDie;
  }
}

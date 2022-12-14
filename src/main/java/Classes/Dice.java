package Classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.lightofdragons.GameController;

public class Dice {
  private int rank;
  Player player;
  BattleField battleOn;
  public Field standsOn;
  private int xCoord;
  private int yCoord;
  GameController game;
  ImageView interfaceDice;
  Image rank1Image;
  Image rank2Image;
  Image rank3Image;
  Image rank4Image;
  Image rank5Image;
  Image rank6Image;
  {
    try {
       rank1Image = new Image(new FileInputStream("src/main/resources/project/lightofdragons/diceRank1.jpg"));
       rank2Image = new Image(new FileInputStream("src/main/resources/project/lightofdragons/dice rank 2.jfif"));
       rank3Image = new Image(new FileInputStream("src/main/resources/project/lightofdragons/dice rank 3.jfif"));
       rank4Image = new Image(new FileInputStream("src/main/resources/project/lightofdragons/dice rank 4.jpg"));
       rank5Image = new Image(new FileInputStream("src/main/resources/project/lightofdragons/dice rank 5.jpg"));
       rank6Image = new Image(new FileInputStream("src/main/resources/project/lightofdragons/dice rank 6.jpg"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private Field newPosition() {  //For beginning
    int x = new Scanner(System.in).nextInt();
    int y = new Scanner(System.in).nextInt();
    return battleOn.getBattlefield()[x][y].getDice() == null ? battleOn.getBattlefield()[x][y] :
        newPosition();
  }

  public Dice(Player player, int startingRank){
    this.player = player;
    rank = startingRank;
    game = player.getGame();
    battleOn = player.getGame().getBattleField();
    interfaceDice = new ImageView();
    interfaceDice.setFitHeight(25);
    interfaceDice.setFitWidth(25);
    DropShadow diecolor = new DropShadow();
    diecolor.setColor(player.getColor());
    interfaceDice.setEffect(diecolor);
    setImage(rank);
    game.newDiceCreated();
  }

  public Player getPlayer() {
    return player;
  }

  public void setBattleOn(BattleField battlefield) {
    battleOn = battlefield;
  }

  public BattleField getBattleOn() {
    return battleOn;
  }

  /**
   *
   * @param x
   * @param y
   */
  public void setField(int x, int y) {

    if(getField() != null) {
      int fromX = getField().getXCoord();
      int fromY = getField().getYCoord();
      game.getPanes()[getField().getXCoord()][ getField().getYCoord()].setStyle("-fx-border-color : null");
      getField().setDice(null);

    }

    standsOn = battleOn.getBattlefield()[x][y];
    battleOn.getBattlefield()[x][y].setDice(this);

    xCoord = standsOn.getXCoord();
    yCoord = standsOn.getYCoord();

    game.getPanes()[x][y].getChildren().clear();
    game.getPanes()[x][y].getChildren().addAll(interfaceDice);

  }

  /**
   *
   * @return
   */
  public Field getField() {
    return standsOn;
  }

  /**
   * Upgrades the Rank of the Die.
   *
   * @return if the action was successfully (false for successful) executed.
   */
  public boolean upgrade() {
    RotateTransition rotate = new RotateTransition();
    rotate.setByAngle(360);
    rotate.setDuration(Duration.millis(100));
    rotate.setAxis(Rotate.X_AXIS);

    if(rank == 6){
      return true;
    }

    rotate.setNode(interfaceDice);
    rotate.play();

    switch (rank) {
      case 1:
        rank = 2;
        break;
      case 2:
        rank = 3;
        break;
      case 3:
        rank = 4;
        break;
      case 4:
        rank = 5;
        break;

      case 5:
        rank = 6;
        break;
    }
    setImage(rank);
    return false;
  }

  /**
   * Checks if a movement form one point to another is valid for a Die
   *
   * @param toX Ending X-position
   * @param toY Ending Y-position
   */
  public boolean moveValid(int toX, int toY) {
    int fromX = xCoord;
    int fromY = yCoord;
    if(0<=toX&&toX<7&&0<=toY&&toY<7) {
      if (standsOn != battleOn.exile) {
        if (getBattleOn().checkForShield(toX, toY)) {
          switch (rank) {

            //Normal movement
            case 1, 3, 4, 6: {
              if ((fromX == toX && (fromY - toY == -1 || fromY - toY == 1)) ||
                  (fromY == toY && (fromX - toX == -1 || fromX - toX == 1))) {
                return moveTo(toX, toY);

              } else {
                game.getOutputLabel().setText("No valid movement");
              }
              break;

            }


            //ghost
            case 2: {
              if (fromX == toX) {
                switch (fromY - toY) {
                  case 2:
                    if (getBattleOn().checkForShield(toX, toY + 1)) {
                      Dice inWay = getBattleOn().getBattlefield()[toX][toY + 1].getDice();
                      if (inWay != null) {
                        inWay.downgrade(); //Downgrade on the way
                        player.mPoints++;
                      }
                      return moveTo(toX, toY);

                    } else {
                      game.getOutputLabel().setText("Shielded");
                    }
                    break;

                  case -2:
                    if (getBattleOn().checkForShield(toX, toY - 1)) {
                      Dice inWay = getBattleOn().getBattlefield()[toX][toY - 1].getDice();
                      if (inWay != null) {
                        inWay.downgrade();
                        player.mPoints++;
                      }
                      return moveTo(toX, toY);

                    } else {
                      game.getOutputLabel().setText("Shielded");
                    }

                    break;
                  case 1, -1:
                    return moveTo(toX, toY);
                  case 0:
                    game.getOutputLabel().setText("No movement");
                    return false;

                  default:
                    game.getOutputLabel().setText("No valid movement");
                    return false;
                }
              } else if (fromY == toY) {

                switch (fromX - toX) {
                  case 2:
                    if (getBattleOn().checkForShield(toX + 1, toY)) {
                      Dice inWay = getBattleOn().getBattlefield()[toX + 1][toY].getDice();
                      if (inWay != null) {
                        inWay.downgrade();  //Downgrade on the way
                      }
                      return moveTo(toX, toY);
                    }

                    break;

                  case -2:
                    if (getBattleOn().checkForShield(toX - 1, toY)) {
                      Dice inWay = getBattleOn().getBattlefield()[toX - 1][toY].getDice();
                      if (inWay != null) {
                        inWay.downgrade();
                      }
                      return moveTo(toX, toY);
                    }
                    break;
                  case 1, -1:
                    return moveTo(toX, toY);
                  case 0:
                    return false;

                  default:
                    return false;
                }
              }
            }


            //Basilisk
            case 5: {
              if ((fromX != toX && fromY != toY) &&        //just diagonal movement
                  ((fromX - toX >= -1 || fromX - toX <= 1) &&  // distance <=1
                      (fromY - toY >= -1 || fromY - toY <= 1))) {
                return moveTo(toX, toY);
              } else {
                game.getOutputLabel().setText("No valid movement");
              }
              break;
            }
            default: {
              game.getOutputLabel().setText("No valid movement");
              return false;

            }

          }
        } else {
          game.getOutputLabel().setText("Shielded");
          return false;
        }
      } else {
        game.getOutputLabel().setText("Exiled");
        return false;
      }
    }
    else{
      game.getOutputLabel().setText("Not on Battlefield");
      }
    return false;
  }

  /**
   * Moves the Die to the new Position and if possible starts a Fight
   *
   * @param x new x position
   * @param y new y position
   */
  private boolean moveTo(int x, int y) {

    if (battleOn.battlefield[x][y].getDice() != null) { //field not empty
      if (player != battleOn.battlefield[x][y].getDice().player) {  //same players
        if ((rank != 6 && rank > battleOn.battlefield[x][y].getDice().getRank()) //rank higher and rank not dragon
            || battleOn.battlefield[x][y].getDice().getRank() != 3 && rank > battleOn.battlefield[x][y].getDice().getRank() //rank higher and enemy not warrior
            || rank == 3 &&
            battleOn.battlefield[x][y].getDice().getRank() == 6) { // warrior and dragon
           //setField(x,y); //field gets set for die
          attack(x, y); //
          if(rank==5){
            return false;
          }
          return true;
        } else {
          game.getOutputLabel().setText("Enemy Rank higher");
          exile();
          return true;
        }
      } else {
        //own Die
        game.getOutputLabel().setText("Same player");
        return false;
      }
    } else {
      //other field is empty
     game.getOutputLabel().setText("");
      setField(x,y);

      return true;
    }
  }

  public void attack(int x, int y) {
    Dice onField = battleOn.battlefield[x][y].getDice();
    getPlayer().setsPoints(getPlayer().getsPoints()+onField.getRank());
    onField.exile(); //dice gets removed
    setField(x,y); // dice coords are updated
  }

  public boolean hasMagic(){
    if( getRank()== 3 || getRank()== 4|| getRank()== 6){
      return true;
    }
    else{
      game.getOutputLabel().setText("Dice has no magic");
      return false;
    }
  }

  /**
   * triggers the magic ability of the dice
   * @return if new dice were created
   */
  public void magic() {

   switch (rank) {


     case 3: //Magic for warrior

       Stage stage = new Stage();

       FlowPane main = new FlowPane();
       main.setAlignment(Pos.CENTER);

       Scene scene = new Scene(main);
       stage.setScene(scene);
       Label rankLabel = new Label();
       rankLabel.setText("Orientation for Souls:");
       Button vertical = new Button();
       Button horizontal = new Button();
       vertical.setText("Vertical");
       horizontal.setText("Horizontal");
       vertical.setOnMouseClicked(vB -> {
         if (yCoord > 0 && yCoord < 6 && battleOn.getBattlefield()[xCoord][yCoord-1].getDice()==null &&battleOn.getBattlefield()[xCoord][yCoord+1].getDice()==null) {
           setRank(1);

           Dice dice1 = new Dice(player, 1);
           Dice dice2 = new Dice(player, 1);
           dice1.setBattleOn(battleOn);
           dice2.setBattleOn(battleOn);
           dice1.setField(xCoord, yCoord - 1);
           dice2.setField(xCoord, yCoord + 1);
           stage.close();
         } else {
           game.getOutputLabel().setText("Not Possible");
         }
       });
       horizontal.setOnMouseClicked(hb -> {
         if (xCoord > 0 && xCoord < 6 && battleOn.getBattlefield()[xCoord-1][yCoord].getDice()==null &&battleOn.getBattlefield()[xCoord+1][yCoord].getDice()==null) {
           setRank(1);

           Dice dice1 = new Dice(player, 1);
           Dice dice2 = new Dice(player, 1);
           dice1.setBattleOn(battleOn);
           dice2.setBattleOn(battleOn);
           dice1.setField(xCoord - 1, yCoord);
           dice2.setField(xCoord + 1, yCoord);
           stage.close();
         } else {
           game.getOutputLabel().setText("Not Possible");
         }
       });
       main.getChildren().addAll(rankLabel, vertical, horizontal);
       stage.show();

       break;
     case 4:  //Magic for wizard //TODO Update for new interaction
       Stage stageM = new Stage();

       FlowPane mainM = new FlowPane();
       mainM.setAlignment(Pos.CENTER);

       Scene sceneM = new Scene(mainM);
       stageM.setScene(sceneM);

       Label label = new Label();
       TextField xField = new TextField();
       TextField yField = new TextField();
       xField.setMaxSize(25,30);
       yField.setMaxSize(25,30);

       Button accept = new Button();

       accept.setText("Accept");

       AtomicInteger amount = new AtomicInteger(0);
       AtomicInteger circle = new AtomicInteger(0);
       for (Dice e : player.dices) {
         if (e != null && e.getRank() == 4) {
           amount.getAndIncrement();
         }
       }
       label.setText("Choose "+amount+ " Die(s) to Exile:");
       accept.setOnMouseClicked(aD -> {

         if(xField.getText()!= null && yField.getText() != null) {
           int x = Integer.parseInt(xField.getText()) - 1;
           int y = Integer.parseInt(yField.getText()) - 1;
           if (0 <= x && x < 7 && 0 <= y && y < 7 &&
               battleOn.getBattlefield()[x][y].getDice() != null
               && battleOn.getBattlefield()[x][y].getDice() != this
               && battleOn.getBattlefield()[x][y].getDice().getPlayer() == this.getPlayer()) {
             battleOn.getBattlefield()[x][y].getDice().exile();
             getPlayer().setmPoints(getPlayer().getmPoints() + 2);
             circle.getAndIncrement();
             if (circle.get() >= amount.get()) {
               accept.setDisable(true);
               stageM.close();
               return;
             }
           } else {
             game.getOutputLabel().setText("Not Possible");
           }
         }
       });

       mainM.getChildren().addAll(label, xField, yField, accept);
       stageM.show();

       break;
     case 6: //Magic for Dragon

       Stage stageD = new Stage();
       stageD.close();

       FlowPane mainD = new FlowPane();
       mainD.setAlignment(Pos.CENTER);

       Scene sceneD = new Scene(mainD);
       stageD.setScene(sceneD);

       Label rankLabelD = new Label();
       rankLabelD.setText("Orientation for Warriors:");

       Button verticalD = new Button();
       Button horizontalD = new Button();
       verticalD.setText("Vertical");
       horizontalD.setText("Horizontal");

       verticalD.setOnMouseClicked(vD -> {
         if (yCoord > 0 && yCoord < 6) {
           setRank(3);
           Dice diceW1 = new Dice(player, 3);
           diceW1.setBattleOn(battleOn);
           diceW1.setField(xCoord, yCoord - 1);
           setField(xCoord, yCoord + 1);
           stageD.close();
         } else {
           game.getOutputLabel().setText("Not Possible");
         }
       });
       horizontalD.setOnMouseClicked(hD -> {
         if (xCoord > 0 && xCoord < 6) {
           setRank(3);
           Dice diceW1 = new Dice(player, 3);
           diceW1.setBattleOn(battleOn);
           diceW1.setField(xCoord - 1, yCoord);
           setField(xCoord + 1, yCoord);
           stageD.close();
           return;
         } else {
           game.getOutputLabel().setText("Not Possible");
         }
       });
       mainD.getChildren().addAll(rankLabelD, verticalD, horizontalD);
       stageD.show();
       break;

   }
  }

  public void exile() {
    battleOn.getBattlefield()[standsOn.getXCoord()][standsOn.getYCoord()].setDice(null);

      game.getPanes()[standsOn.getXCoord()][standsOn.getYCoord()].getChildren().clear();
      game.getExilePane().getChildren().add(new Box(15,15,15));

    standsOn = battleOn.exile;

    xCoord = standsOn.getXCoord();
    yCoord = standsOn.getYCoord();

  }

  public int getRank() {
    return rank;
  }

  private void setRank(int newRank) {
    rank = newRank;
    setImage(rank);
  }

  public ImageView getInterfaceDice() {
    return interfaceDice; // interfaceDice;
  }

  private void downgrade() {
    if (rank > 1) {
      RotateTransition rotate = new RotateTransition();
      rotate.setByAngle(-360);
      rotate.setDuration(Duration.millis(100));
      rotate.setAxis(Rotate.X_AXIS);
      rotate.setNode(interfaceDice);
      rotate.play();

      rank -= 1;
      setImage(rank);

    }
  }
  public void setImage(int rank){

    switch(rank){
      case 1:
        interfaceDice.setImage(rank1Image);
        break;
      case 2:
      interfaceDice.setImage(rank2Image);
      break;
      case 3:
    interfaceDice.setImage(rank3Image);
    break;
      case 4:
     interfaceDice.setImage(rank4Image);
     break;
      case 5:
     interfaceDice.setImage(rank5Image);
     break;
      case 6:
      interfaceDice.setImage(rank6Image);
      break;

    }
  }
}

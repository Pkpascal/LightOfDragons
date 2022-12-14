package Classes;

import javafx.scene.layout.Pane;

public class Field {

  int xCoord;
  int yCoord;
  boolean passable;
  Dice monster;
  Pane pane;

  public Field(int x, int y) {
    xCoord = x;
    yCoord = y;
    passable = true;
    //pane =
  }

  public int getXCoord() {
    return xCoord;
  }

  public int getYCoord() {
    return yCoord;
  }

  public void setPassable(boolean pass) {
    passable = pass;
  }

  public boolean isPassable() {
    return passable;
  }

  public void setDice(Dice dice) {
    monster = dice;
  }

  public Dice getDice() {
    return monster;
  }
}

package project.lightofdragons;

import javafx.scene.paint.Color;

public class Settings {

  public static int startingPoints = 10;
  public static int winningPoints = 12;
  public static Color pl1Color = Color.RED;
  public static Color pl2Color = Color.BLACK;
  public static boolean ruinsActive = true;
  public static int maxDice = 15;

  public static int getMaxDice() {
    return maxDice;
  }

  public static void setMaxDice(int maxDice) {
    Settings.maxDice = maxDice;
  }


  public static boolean isRuinsActive() {
    return ruinsActive;
  }

  public static void setRuinsActive(boolean ruinsActive) {
    Settings.ruinsActive = ruinsActive;
  }


  public static int getWinningPoints() {
    return winningPoints;
  }

  public static void setWinningPoints(int winningP) {
    winningPoints = winningP;
  }

  public static Color getPl1Color() {
    return pl1Color;
  }

  public static void setPl1Color(Color pl1C) {
    pl1Color = pl1C;
  }

  public static Color getPl2Color() {
    return pl2Color;
  }

  public static void setPl2Color(Color pl2C) {
    pl2Color = pl2C;
  }

}

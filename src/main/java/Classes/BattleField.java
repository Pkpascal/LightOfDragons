package Classes;


import project.lightofdragons.GameController;

public class BattleField {
  public Field[][] battlefield = new Field[7][7];
  ExileField exile = new ExileField(10,10);
  GameController game;
  static int soulAmount;

  public BattleField(){
    int x;
    int y;

    for (x =0;x<7;x++){
      for (y = 6; y>=0;y--){
        battlefield[x][y] = new Field(x,y);
      }
    }
  }
  public Field[][] getBattlefield(){
    return battlefield;
  }

  public boolean checkForShield(int x , int y){
    Field checkingField = battlefield[x][y];
    if(checkingField.getDice() != null && checkingField.getDice().getRank() == 1){
      int soulAmount = 1;
      soulAmount += getNeighborSouls(checkingField);

      if(soulAmount>=3) {
        return false;
      }
      else{
        return true;
      }

    }
    else {
      return true;
    }
  }

  public int getNeighborSouls( Field field){
    int startX = field.getXCoord();
    int startY = field.getYCoord();
    soulAmount = 0;
      if (soulAmount<3 && startX > 0 && battlefield[startX - 1][startY].getDice() != null &&
          battlefield[startX - 1][startY].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getNegXSouls(battlefield[startX - 1][startY]);
      }
      if (soulAmount<3 && startX < 6 && battlefield[startX + 1][startY].getDice() != null &&
          battlefield[startX + 1][startY].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getPosXSouls(battlefield[startX + 1][startY]);
      }
      if (soulAmount<3 && startY > 0 && battlefield[startX][startY - 1].getDice() != null &&
          battlefield[startX][startY - 1].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getNegYSouls(battlefield[startX][startY - 1]);
      }
      if (soulAmount<3 && startY < 6 && battlefield[startX][startY + 1].getDice() != null &&
          battlefield[startX][startY + 1].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getPosYSouls(battlefield[startX][startY + 1]);
    }

    return soulAmount;

  }

  private int getPosYSouls(Field field) {
    int startX = field.getXCoord();
    int startY = field.getYCoord();
      if (soulAmount<3 && startX > 0 && battlefield[startX - 1][startY].getDice() != null &&
          battlefield[startX - 1][startY].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getNegXSouls(battlefield[startX - 1][startY]);
      }
      if (soulAmount<3 && startX < 6 && battlefield[startX + 1][startY].getDice() != null &&
          battlefield[startX + 1][startY].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getPosXSouls(battlefield[startX + 1][startY]);
      }
      if (soulAmount<3 && startY < 6 && battlefield[startX][startY + 1].getDice() != null &&
          battlefield[startX][startY + 1].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getPosYSouls(battlefield[startX][startY + 1]);

    }

    return soulAmount;

  }

  private int getNegYSouls(Field field) {
    int startX = field.getXCoord();
    int startY = field.getYCoord();
      if (soulAmount<3 && startX > 0 && battlefield[startX - 1][startY].getDice() != null &&
          battlefield[startX - 1][startY].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getNegXSouls(battlefield[startX - 1][startY]);
      }
      if (soulAmount<3 && startX < 6 && battlefield[startX + 1][startY].getDice() != null &&
          battlefield[startX + 1][startY].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getPosXSouls(battlefield[startX + 1][startY]);
      }
      if (soulAmount<3 && startY > 0 && battlefield[startX][startY - 1].getDice() != null &&
          battlefield[startX][startY - 1].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getNegYSouls(battlefield[startX][startY - 1]);

    }

    return soulAmount;

  }

  private int getPosXSouls(Field field) {
    int startX = field.getXCoord();
    int startY = field.getYCoord();
      if (soulAmount<3 && startX < 6 && battlefield[startX + 1][startY].getDice() != null &&
          battlefield[startX + 1][startY].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getPosXSouls(battlefield[startX + 1][startY]);
      }
      if (soulAmount<3 && startY > 0 && battlefield[startX][startY - 1].getDice() != null &&
          battlefield[startX][startY - 1].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getNegYSouls(battlefield[startX][startY - 1]);
      }
      if (soulAmount<3 && startY < 6 && battlefield[startX][startY + 1].getDice() != null &&
          battlefield[startX][startY + 1].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getPosYSouls(battlefield[startX][startY + 1]);

    }

    return soulAmount;

  }

  private int getNegXSouls(Field field) {
    int startX = field.getXCoord();
    int startY = field.getYCoord();
      if (soulAmount<3 && startX > 0 && battlefield[startX - 1][startY].getDice() != null &&
          battlefield[startX - 1][startY].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getNegXSouls(battlefield[startX - 1][startY]);
      }
      if (soulAmount<3 && startY > 0 && battlefield[startX][startY - 1].getDice() != null &&
          battlefield[startX][startY - 1].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getNegYSouls(battlefield[startX][startY - 1]);
      }
      if (soulAmount<3 && startY < 6 && battlefield[startX][startY + 1].getDice() != null &&
          battlefield[startX][startY + 1].getDice().getRank() == 1) {
        soulAmount++;
        soulAmount += getPosYSouls(battlefield[startX][startY + 1]);
      }

    return soulAmount;

  }

  public GameController getGame(){
    return game;
  }

  public void setGame(GameController ui){
    game = ui;
  }


}

package TestingClasses;

import Classes.BattleField;
import Classes.Dice;
import Classes.Player;
import org.junit.Test;

public class TestDice {
  @Test
  public static void main(String[] args) {
    testMovement();
  }

  public static void testUpgrade(){
   /* Dice testdice = new Dice(new Player());
    System.out.println(testdice.upgrade());
    System.out.println(testdice.getRank());
    */

  }
  public static void testMagic(){
    /*Dice testdice = new Dice(new Player(),6);
    BattleField field = new BattleField();
    testdice.setBattleOn(field);
    testdice.setField(1,1);
    testdice.magic();
    System.out.println(field.getBattlefield()[0][1].getDice().getRank());
    System.out.println(field.getBattlefield()[1][1]);
    System.out.println(field.getBattlefield()[2][1].getDice().getRank());
*/
  }
  public static void testGhost(){
   /* BattleField field = new BattleField();
    Dice testDice1 = new Dice(new Player(),2);
    Dice testDice2 = new Dice(new Player(),5);
    testDice1.setBattleOn(field);
    testDice2.setBattleOn(field);
    testDice1.setField(1,1);
    testDice2.setField(1,2);
    System.out.println("Player mPoints :" + testDice1.getPlayer().getmPoints());
    System.out.println("Rank :" + testDice2.getRank());
    testDice1.moveValid(1,3);
    System.out.println("Movement :" + testDice1.getField().getYCoord());
    System.out.println("Player mPoints :" + testDice1.getPlayer().getmPoints());
    System.out.println("Rank after :" + testDice2.getRank());*/
  }
  public static void testMovement(){
   /* BattleField field = new BattleField();
    Dice testDice1 = new Dice(new Player(),1);
    testDice1.setBattleOn(field);
    testDice1.setField(1,1);
    System.out.println(testDice1.moveValid(2,2));
    System.out.println(testDice1.moveValid(1,2));*/
  }

}

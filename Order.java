import javax.swing.*;
import java.io.*;
import java.util.*;
public class Order{
   private boolean mushroom, sausage;
   private int timeLeft;
   private int type;
   private int ypos;
   private int ID;
   private LoaderV2 load;
   private boolean passed = false;
   public Order(int x, boolean m, boolean s, boolean first){
      ypos = (first ? 900 : -1);
      timeLeft = x;
      mushroom = m;
      sausage = s;
      type=1;
      if(sausage){
         type = 3;
      }
      if(mushroom){
         type++;
      }
      load = new LoaderV2(62);
   }
   public void decrement(){
      timeLeft--;
   }
   public LoaderV2 getLoader(){
      return load;
   }
   
   // returns whether 'other' is a correct order
   public boolean match(Item other){
      if(mushroom == other.getList().contains("mus")){
         if(sausage == other.getList().contains("sau")){ 
            if(other.isOven() && other.hasPlate() && timeLeft > 0){
               return true;
            }
         }
      }
      return false;
   }
   public void setID(int id){
      this.ID = id;
   }
   public int getID(){
      return ID;
   }
   public void setYPos(int y){
      ypos = y;
   }   
   public int getYPos(){
      return ypos;
   }
   public int timeLeft(){
      return timeLeft;
   }
   
   public int getType(){
      return type;
   }
   public int getScore(){
      passed = true;
      return (40+timeLeft)+((sausage) ? 10 : 0)+((mushroom) ? 10 : 0); // calculates the score of (40+timeLeft) + 10 if sausage + 10 if mushroom
   }
   public boolean getPassed(){
      return passed;
   }
   
   public ImageIcon getPicture(){
      return new ImageIcon("images/game/order"+type+((timeLeft<=0 && !passed) ? "F" : "")+(passed ? "P" : "")+".gif");
   }

}
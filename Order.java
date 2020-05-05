import javax.swing.*;
import java.io.*;
import java.util.*;
public class Order{
   private boolean mushroom, sausage;
   private int timeLeft;
   private int type;
   public Order(int x){
      timeLeft = x;
      mushroom = false;
      sausage = false;
      type = 1;
   }
   public Order(int x, boolean m, boolean s){
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
   }
   public void decrement(){
      timeLeft--;
   }
   
   // returns whether 'other' is a correct order
   public boolean match(Item other){
      if(mushroom == other.getList().contains("mus")){
         if(sausage == other.getList().contains("sau")){ 
            if(other.isOven() && other.hasPlate()){
               return true;
            }
         }
      }
      return false;
   }
   
   public int timeLeft(){
      return timeLeft;
   }
   
   public int getType(){
      return type;
   }
   public int getScore(){
      return (100-timeLeft)+((sausage) ? 0 : 10)+((mushroom) ? 0 : 10); //lmfao ternary
   }
   
   public ImageIcon getPicture(){
      return new ImageIcon("images/game/order"+type+".gif");
   }

}
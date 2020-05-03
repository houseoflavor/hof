import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.*;
import java.util.*;
public class Item{
   private Player holder;
   private int chopLeft;
   private String name;
   private boolean food;
   private boolean tool;
   private String cut;
   private int cookLeft;
   private String oven;
   private int dx, dy;
   private ArrayList<String> ingr;
   // Item constructor
   public Item(String name, Player h, boolean f, boolean t){
      holder = h;
      this.name=name;
      ingr = new ArrayList<String>();
      ingr.add(name);
      food = f;
      tool = t;
      if(food){
         chopLeft = 9;
      }
      dx=0;
      dy=0;
      oven = "";
      cut = "";
   }
   public Item(){} // nothing constructor
   
   public boolean isFood(){
      return food;
   }
   public boolean isTool(){
      return tool;
   }
   public String getName(){
      Collections.sort(ingr);
      String ret = "";
      for(String n:ingr){
         ret+=n;
      }
      return ret;
   }
   
   public ArrayList getList(){
      return ingr;
   }
   
   public boolean isChopped(){
      return cut.equals("C");
   }
   
   public ImageIcon getPicture(){
      return new ImageIcon("images/items/"+name+cut+oven+".gif");
   }
   
   public ImageIcon getHPicture(){
      return new ImageIcon("images/items/"+name+cut+oven+"H.gif");
   }
   
   public boolean canChop(){
      if(chopLeft>0){
         return true;
      }
      if(name.length()==3){
         cut="C";
      }
      return false;
   }
   
   public void chop(){
      if(chopLeft>0){
         chopLeft--;
         // do some shaking
         dx=(int)(Math.random()*400)-200;
         dy=(int)(Math.random()*400)-200;
      }
   }
   
   public int getXShake(){
      if(dx!=0){
         dx-=2*(dx/Math.abs(dx));
      }
      return dx/40;
   }
   public int getYShake(){
      if(dy!=0){
         dy-=2*(dy/Math.abs(dy));
      }
      return dy/40;
   }
   public int getChop(){
      return chopLeft;
   }
   
   public void setCook(int c){
      cookLeft = c;
   }
   
   public int getCook(){
      return cookLeft;
   }
   
   public void startCook(){
      cookLeft = 1120;
   }
   
   // combines the other food with this
   public boolean combine(Item other){
      if(other.isFood() && (other.isChopped() || other.getName().length()!= 3) && (this.isChopped() || this.getName().length()!= 3)){
         ArrayList<String> oList = other.getList();
         // are you adding something that already exists on the other item
         for(String n : ingr){ // if any item is a duplicate, can't combine
            if(oList.contains(n)){
               return false;
            }
         }
         // is it a valid food combination?
         String validCombo = " doutom chedou chedoutom "; // expand as needed
         for(String n : ingr){
            oList.add(n);
         }
         Collections.sort(oList);
         String ret = "";
         for(String n : oList){
            ret+=n;
         }
         if(!validCombo.contains(" " + ret + " ")){
            return false;
         }
         // valid! (delete other, keep this)
         else{
            ingr = oList;
            name = this.getName();
            cut = "";
         }
         return true;
      }
      return false;
   }
   
   public boolean oven(){
      String validOven = " chedoutom ";
      if(validOven.contains(" " + this.getName() + " ")){
         oven = "X"; // filler to make invisible
         return true;
      }
      return false;
   }
   
   public void ovenCooked(){
      oven = "O";
   }
}
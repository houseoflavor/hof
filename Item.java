import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.*;
import java.util.*;
public class Item{
   private int chopLeft;
   private String name;
   private boolean food;
   private boolean tool;
   private String cut;
   private int cookLeft;
   private String oven;
   private int dx, dy;
   private boolean plate;
   private boolean pan;
   private ArrayList<String> ingr;
   private int panCookLeft;
   private String panned;
   // Item constructor
   public Item(String name, boolean f, boolean t){
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
      plate = false;
      cookLeft = 0;
      pan = false;
      panCookLeft = 0;
      panned = "";
   }
   public Item(){} // nothing constructor
   
   public boolean isFood(){
      return food;
   }
   public boolean isTool(){
      return tool;
   }
   // returns the full name of all the ingredients in the item
   public String getName(){
      Collections.sort(ingr);
      String ret = "";
      for(String n:ingr){
         ret+=n;
      }
      return ret;
   }
   // returns an arraylist of all ingredients in the item
   public ArrayList getList(){
      return ingr;
   }
   
   public void setList(ArrayList<String> other){
      ingr = other;
   }
   
   // returns whether an item is chopped
   public boolean isChopped(){
      return cut.equals("C");
   }
   
   // returns an ImageIcon of the correct picture
   public ImageIcon getPicture(){
      if(plate){
         return new ImageIcon("images/items/"+name+"pla.gif");
      }
      if(panned.equals("P")){ //sauced
         return new ImageIcon("images/items/pantomP.gif");
      }
      if(tool){
         return new ImageIcon("images/items/"+name+".gif");
      }
      if(this.inPan()){
         return new ImageIcon("images/items/"+name+".gif");
      }
      return new ImageIcon("images/items/"+name+cut+oven+".gif");
   }
   // returns the highlighted version
   public ImageIcon getHPicture(){
      if(plate){
         return new ImageIcon("images/items/"+name+"plaH.gif");
      }
      if(panned.equals("P")){ //sauced
         return new ImageIcon("images/items/pantomPH.gif");
      }
      if(tool){
         return new ImageIcon("images/items/"+name+"H.gif");
      }
      if(this.inPan()){
         return new ImageIcon("images/items/"+name+"H.gif");
      }
      return new ImageIcon("images/items/"+name+cut+oven+"H.gif");
   }
   
   // returns whether the item can be chopped
   public boolean canChop(){
      if(chopLeft>0){
         return true;
      }
      if(name.length()==3){
         cut="C";
      }
      return false;
   }
   // chop
   public void chop(){
      if(chopLeft>0){
         chopLeft--;
         // do some shaking
         dx=(int)(Math.random()*400)-200;
         dy=(int)(Math.random()*400)-200;
      }
   }
   
   public void setChopped(){
      chopLeft = 0;
      cut = "C";
   }
   // x and y shaking for chop mechanic
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
   // returns chopLeft
   public int getChop(){
      return chopLeft;
   }
   // sets cookLeft to the parameter
   public void setCook(int c){
      cookLeft = c;
   }
   // accessor
   public int getCook(){
      return cookLeft;
   }
   // i don't know which one's i'm still using but i really don't want to check so just keep all of these and it works so /shrug
   public int getPanCook(){
      return panCookLeft;
   }
   public void setPanCook(int c){
      panCookLeft = c;
   }
   public boolean isPan(){
      return name.equals("pan");
   }
   public boolean inPan(){
      return pan;
   }
   public void setPan(boolean t){
      pan = t;
   }
   public boolean hasPlate(){
      return plate;
   }
   public boolean isPlate(){
      return name.equals("pla");
   }
   public void donePan(){
      panned = "P";
   }
   public String getPanned(){
      return panned;
   }
   public void setPlate(boolean t){
      plate = t;
   }
   // combines the other food with this
   public boolean combine(Item other){ // this = what is on table; other = in hand
      if(other.isPan()){ // holding pan, drop onto tomato
         if(other.getName().equals("pan") && this.name.equals("tom") && this.isChopped() && other.getPanCook()<561){
            ingr.add("pan");
            pan = true;
            other.setPan(true);
            Collections.sort(ingr);
            name = this.getName();
            cut = "";
            return true;
         }
      }
      else if(this.isPan()){ // holding tomato, drop onto pan
         if(this.getName().equals("pan") && other.name.equals("tom") && other.isChopped() && this.getPanCook()<561){
            ingr.add("tom");
            pan = true;
            other.setPan(true);
            Collections.sort(ingr);
            cut = "";
            name = this.getName();
            return true;
         }
      }
      else if(other.isPlate()){
         if(this.isOven() && !plate){
            plate = true;
            return true;
         }
      }
      else if(this.isPlate()){
         if(other.isOven() && !other.hasPlate()){
            plate = true;
            tool = false;
            food = true;
            oven = "O";
            ingr = other.getList();
            name = other.getName();
            return true;
         }
      }
      else if(other.isFood() && (other.isChopped() || other.getName().length()!= 3) && (this.isChopped() || this.getName().length()!= 3)){
         if(this.isOven() || other.isOven()){
            return false;
         }
         ArrayList<String> oList = (ArrayList)(other.getList().clone());
         // are you adding something that already exists on the other item
         for(String n : ingr){ // if any item is a duplicate, can't combine
            if(oList.contains(n)){
               return false;
            }
         }
         // is it a valid food combination?
         // top = tomato pan (tomato is cooked)
         String validCombo = " doutop chedou chedoutop chedoumustop doumustop chedoumus doumus "; // expand as needed
         for(String n : ingr){
            oList.add(n);
         }
         Collections.sort(oList);
         String ret = "";
         for(String n : oList){
            ret+=n;
         }
         System.out.println(ret);
         if(!validCombo.contains(" " + ret + " ")){
            return false;
         }
         // valid! (delete other, keep this)
         else{
            ingr = oList;
            name = this.getName();
            cut = "";
            return true;
         }
      }
      return false;
   }
   // putting an item in the oven
   // returns whether the item can go in the oven
   public boolean oven(){
      String validOven = " chedoutop chedoumustop ";
      if(validOven.contains(" " + this.getName() + " ") && oven.equals("") && cookLeft<1121){
         oven = "X"; // filler to make invisible
         return true;
      }
      return false;
   }
   // take item out of oven (make visible)
   public void takeOut(){
      oven = "";
   }
   // the item is cooked (show cooked form)
   public void ovenCooked(){
      oven = "O";
   }
   public boolean isOven(){
      return oven.equals("O");
   }
}
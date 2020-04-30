import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.*;
import java.util.*;
public class Item{
   private ImageIcon pic;
   private Player holder;
   private int chopLeft;
   private String name;
   private boolean food;
   private boolean tool;
   private int dx, dy;
   // Item constructor
   public Item(String name, Player h, boolean f, boolean t){
      pic = new ImageIcon("images/items/"+name+".gif");
      holder = h;
      this.name=name;
      food = f;
      tool = t;
      if(food){
         chopLeft = 9;
      }
      dx=0;
      dy=0;
   }
   public Item(){} // nothing constructor
   
   public boolean isFood(){
      return food;
   }
   public boolean isTool(){
      return tool;
   }
   public String getName(){
      return name;
   }
   
   public ImageIcon getPicture(){
      return pic;
   }
   
   public ImageIcon getHPicture(){
      return new ImageIcon("images/items/"+name+"H.gif");
   }
   
   public boolean canChop(){
      if(chopLeft>0){
         return true;
      }
      return false;
   }
   
   public void chop(){
      if(chopLeft>0){
         chopLeft--;
         // do some shaking
         dx=(int)(Math.random()*25);
         dy=(int)(Math.random()*25);
      }
   }
   
   public int getXShake(){
      dx/=2;
      return dx;
   }
   public int getYShake(){
      dy/=2;
      return dy;
   }
   public int getChop(){
      return chopLeft;
   }
   
   
   // combines the other food with this
   public boolean combine(Item other){
      if(other.isFood()){
         if(name.compareTo(other.getName())<0){ // this < other
            name=name+other.getName();
            return true;
         }
         if(name.compareTo(other.getName())==0){ // this = other
            return false; // can't combine two of same thing
         }
         else{ // this > other
            name=other.getName()+name;
            return true;
         }
      }
      return false;
   }
}
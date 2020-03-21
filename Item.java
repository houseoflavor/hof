import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.*;
import java.util.*;
public class Item{
   private ImageIcon pic;
   private Player holder;
   private String name;
   private boolean food;
   private boolean tool;
   public Item(String name, Player h, boolean f, boolean t){
      pic = new ImageIcon("images/items/"+name+".gif");
      holder = h;
      this.name=name;
   }
   
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
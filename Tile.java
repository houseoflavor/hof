import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays.*;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Tile {
   private ImageIcon pic;
   private String name;
   private int row;
   private int col;
   private int tileID;
   private boolean hitbox;
   private boolean spawner;
   private String on;
   private boolean conveyer;
   private int numPlates;
   private boolean obs;
   // constructor
   //           name      row    col   hitbox?    spawner?
   public Tile(String n, int r, int c, boolean h, boolean s){
      obs = n.startsWith("z");
      this.pic = new ImageIcon("images/tile/"+n+".gif");
      name = n;
      row=r;
      col=c;
      hitbox = h;
      tileID = (20*r)+c;
      spawner = s;
      on = "";
      conveyer = n.startsWith("co");
      numPlates = 0;
   }
   
   // returns the imageicon of the tile
   // if the tile can be selected, return the brighter version of the icon (append an H)
   // if the tile has an item on it, do not show knife (if cutting board)
   public ImageIcon getPicture(int t1, int t2, boolean hasItem){
      if(obs){
         return new ImageIcon("images/obstacles/"+name+".gif");
      }
      if(name.startsWith("de")){
         return pic;
      }
      if(conveyer){
         return pic;
      } 
      if(name.equals("wal")){
         return pic;
      }
      
      // return highlighted picture
      if(t1==tileID || t2==tileID){
         if(name.equals("ove")){
            return new ImageIcon("images/tile/ove"+on+"H.gif");
         }
         if(name.equals("pla")){
            return new ImageIcon("images/tile/pla"+numPlates+"H.gif");
         }
         if(hasItem){
            if(name.equals("cut")){
               return new ImageIcon("images/tile/cutNH.gif");
            }
         }
         return new ImageIcon("images/tile/"+name+"H.gif");
      }
      if(name.equals("pla")){
         return new ImageIcon("images/tile/pla"+numPlates+".gif");
      }
      if(name.equals("ove")){
         return new ImageIcon("images/tile/ove"+on+".gif");
      }
      if(hasItem){ // cutting board (knife)
         if(name.equals("cut")){
            return new ImageIcon("images/tile/cutN.gif");
         }
      }
      return pic;
   }
   
   public boolean addPlate(){
      if(!name.equals("pla")){
         return false;
      }
      if(numPlates<4){
         numPlates++;
         return true;
      }
      return false;
   }
   public int getPlates(){
      return numPlates;
   }
   public boolean takePlate(){
      if(!name.equals("pla")){
         return false;
      }
      if(numPlates>0){
         numPlates--;
         return true;
      }
      return false;
   }
   
   // returns spawner
   public boolean isSpawner(){
      return spawner;
   }
   // turn on the oven
   public void turnOn(){
      on = "O";
   }
  // turn off the oven
   public void turnOff(){
      on = "";
   }
   
   // returns the location of the tile represented by a single int
   // wrap around, like a book
   public int getTileID(){
      return tileID;
   }
   
   // returns the 3 letter name of the tile
   public String getName(){
      return name;
   }
   
   // returns a Rectangle that is the hitbox of the tile
   public Rectangle getBounds(){
      if(hitbox){
         return new Rectangle(col*32+228, row*32+178, 32, 32);
      }
      return new Rectangle(0,0,0,0);
   }
   
   // toString method is name (for debug)
   public String toString(){
      return name;
   }



}
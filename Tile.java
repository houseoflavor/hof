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
   
   // constructor
   public Tile(String n, int r, int c, boolean h){
      this.pic = new ImageIcon("images/tile/"+n+".gif");
      name = n;
      row=r;
      col=c;
      hitbox = h;
      tileID = (20*r)+c;
   }
   
   // returns the imageicon of the tile
   // if the tile can be selected, return the brighter version of the icon (append an H)
   public ImageIcon getPicture(int t1, int t2){
      if(t1==tileID || t2==tileID){
         return new ImageIcon("images/tile/"+name+"H.gif");
      }
      return pic;
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
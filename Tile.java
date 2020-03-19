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
   
   public Tile(String n, int r, int c){
      this.pic = new ImageIcon("images/tile/"+n+".gif");
      name = n;
      row=r;
      col=c;
   }
   
   public ImageIcon getPicture(){
      return pic;
   }
   public String getName(){
      return name;
   }
   public Rectangle getBounds(){
      return new Rectangle(row*32+228, col*32+178, 32, 32);
   }



}
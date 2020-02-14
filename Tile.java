import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays.*;
import java.util.*;

public class Tile {
   private ImageIcon pic;
   private String name;
   public Tile(String n){
      this.pic = new ImageIcon("images/"+n);
      name = n;
   }
   
   public Image image(){
      return pic.getImage();
   }
   public String getName(){
      return name;
   }



}
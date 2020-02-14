import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays.*;
import java.util.*;

public class HOFUtil extends HOF{
   public static void doEverything(Graphics g){
   
      if(mode.equals("menu")){
         drawMenu(g);
      }
      if(mode.equals("controls")){
         drawControls(g);
      }
      if(mode.equals("game")){
         drawBoard(g);
         drawGame(g);
      }
   
   
      // final thing to draw is transition
      if(transition==1){
         drawTransition(g);
      }
        
   }
   
   public static void drawTransition(Graphics g){
      transX+=((Math.abs(transX))*(-0.07)-1);
      ImageIcon black = new ImageIcon("images/black.png");
      g.drawImage(black.getImage(),transX,0,1300,750,null);
      if(transX<0){
         mode = transMode;
      }  
      if(transX<-1500){
         transX=1500;
         transMode = "none";
         transition = 0;
      }
   }
   
   public static void drawControls(Graphics g){
      ImageIcon controls = new ImageIcon("images/controls.png");
      ImageIcon clickX = new ImageIcon("images/X.png");
      g.drawImage(controls.getImage(),0,0,null);
      if(mouseX<1161 && mouseX>1136 && mouseY<151 && mouseY>113 && mode.equals("controls")){
         g.drawImage(clickX.getImage(),0,0,null);
         buttonTouching = EXIT;
      }
      else{
         buttonTouching = NONE;
      }
   }
   
   public static void drawGame(Graphics g){
      g.setColor(Color.RED);
      g.fillOval(((int)p1.getx()),((int)p1.gety()),50,50);
      g.setColor(Color.BLUE);
      g.fillOval(((int)p2.getx()),((int)p2.gety()),50,50);
   }
   
   public static void drawBoard(Graphics g){
   
   }
   
   public static void drawMenu(Graphics g){
      ImageIcon homescreen = new ImageIcon("images/homescreen-no-buttons.png");
      g.drawImage(homescreen.getImage(),0,0,1300,750,null);
      
      // buttons
      ImageIcon startun = new ImageIcon("images/start-unclicked.png");
      ImageIcon startcl = new ImageIcon("images/start-click.png");
      ImageIcon controlun = new ImageIcon("images/controls-unclicked.png");
      ImageIcon controlcl = new ImageIcon("images/controls-click.png");
      g.drawImage(controlun.getImage(), 0, 0, null);
      g.drawImage(startun.getImage(), 0, 0, null);
      if(mouseX<360 && mouseX>0 && mouseY<365 && mouseY>273 && mode.equals("menu")){
         g.drawImage(startcl.getImage(), 0, 0, null);
         buttonTouching = START;
      }
      else if(mouseX<360 && mouseX>0 && mouseY<546 && mouseY>454 && mode.equals("menu")){
         g.drawImage(controlcl.getImage(), 0, 0, null);
         buttonTouching = CONTROLS;
      }
      else{
         buttonTouching = NONE;
      }
   }
   
   


}
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
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
      ImageIcon black = new ImageIcon("images/menus/black.png");
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
      ImageIcon controls = new ImageIcon("images/menus/controls.png");
      ImageIcon clickX = new ImageIcon("images/menus/X.png");
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
      // background image
      
      // right sidebar + orders
   
      int aspect = 48;
      //background circle
      int width = 30; // width of boundary box
      int height = 25;
      int yoffset = 17; // offset only for physical character, boundary box stays same
      int alpha = 255; // transparency 
      Color p1blue = new Color(17, 126, 233, alpha);
      Color p2red = new Color(252, 46, 46, alpha);
      g.setColor(p1blue);
      drawThickCircle(g, (int)p1.getx()-(width/2), (int)p1.gety()-(width/2), width, height, 5);
      g.drawImage(p1.getPicture().getImage(),((int)p1.getx())-aspect/2,((int)p1.gety())-aspect/2-yoffset,aspect,aspect,null); // normal size is 24x24 so change to proportional aspect ratio!!
      g.setColor(p2red);
      drawThickCircle(g, (int)p2.getx()-(width/2), (int)p2.gety()-(width/2), width, height, 5);
      g.drawImage(p2.getPicture().getImage(),((int)p2.getx())-aspect/2,((int)p2.gety())-aspect/2-yoffset, aspect,aspect,null); // 12, 24, 48
   }
   
   // draw a thicc circle at x, y
   public static void drawThickCircle(Graphics g, int x, int y, int width, int height, int thick){
      Graphics2D g2 = (Graphics2D)(g);
      g2.setStroke(new BasicStroke(thick));
      g2.drawOval(x, y, width, height);
   }
   public static void drawBoard(Graphics g){
      for(int i=0; i<13; i++){
         for (int j=0; j<13; j++){
            //g.drawImage(tiles[i][j].image(), i*
         }
      }
      g.setColor(Color.BLACK);
      g.drawLine(50,50, 50,500);
      g.drawLine(50,50, 1000,50);
      g.drawLine(50,500, 1000,500);
      g.drawLine(1000,50, 1000,500);
   }
   
   public static void drawMenu(Graphics g){
      // back gradient
      ImageIcon gradient = new ImageIcon("images/menus/gradient.png");
      g.drawImage(gradient.getImage(),0,0,null);
      // clouds
      g.drawImage(new ImageIcon("images/menus/clouds-moving-coalesced.gif").getImage(),0,0,null);
      //g.drawImage(cloud.getPicture().getImage(),0,0,null);
      // home
      ImageIcon homescreen = new ImageIcon("images/menus/wall-and-sign.png");
      g.drawImage(homescreen.getImage(),0,0,1300,750,null);
      
      // buttons
      ImageIcon startun = new ImageIcon("images/menus/start-unclicked.png");
      ImageIcon startcl = new ImageIcon("images/menus/start-click.png");
      ImageIcon controlun = new ImageIcon("images/menus/controls-unclicked.png");
      ImageIcon controlcl = new ImageIcon("images/menus/controls-click.png");
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
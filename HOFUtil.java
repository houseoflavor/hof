import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays.*;
import java.util.*;

public class HOFUtil extends HOF{
   // do everything :)
   public static void doEverything(Graphics g){   
   
      if(mode.equals("menu")){
         drawMenu(g);
      }
      if(mode.equals("controls")){
         drawControls(g);
      }
      if(mode.equals("game")){
         drawGame(g);
      }
   
   
      // final thing to draw is transition
      if(transition==1){
         drawTransition(g);
      }
        
   }
   
   // draws the black transition that appears when switching menus
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
   
   // draws the controls screen
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
      // background image (TEMPORARY ONE IS USED)
      g.drawImage((new ImageIcon("images/menus/tempbg.png")).getImage(),0,0,null);
      
      // floor
      for(int i=0; i<20; i++){
         for(int j=0; j<12; j++){
            ImageIcon floor;
            if(j%2==0){ // alternate between tile types
               if(i%2==0){
                  floor = new ImageIcon("images/tile/floor1.gif");
               }
               else{
                  floor = new ImageIcon("images/tile/floor2.gif");
               }
            }
            else{ // new row starts on other tile type
               if(i%2==0){
                  floor = new ImageIcon("images/tile/floor2.gif");
               }
               else{
                  floor = new ImageIcon("images/tile/floor1.gif");
               }
            }
            g.drawImage(floor.getImage(), (i*32)+228, (j*32)+178, 32, 32, null);
         }
      }
      
      /*
      tile ordering:
      1 - find tallest player (smallest row), let that be p1
      2 - do all blocks above and including p1
      3 - p1
      4 - do all (if any) blocks between p1 and p2
      5 - p2
      6 - do all blocks remaining
      */
      int aspect = 48; // normal size is 24x24 so change to proportional aspect ratio!!
      //background circle
      int width = 26; // width of boundary box
      int height = 20;
      int yoffset = 15; // offset only for physical character, boundary box stays same
      int alpha = 190; // transparency 
      Color p1blue = new Color(17, 126, 233, alpha);
      Color p2red = new Color(252, 46, 46, alpha);
      
      // 1 --
      boolean tall = false; // true: p1 is tallest, false: p2 is tallest
      if(p1.getRow()<p2.getRow()){
         tall = true;
      }
      // 2 --
      if(tall){
         drawSomeTiles(g, 0, p1.getRow()+1);
      }
      else{
         drawSomeTiles(g, 0, p2.getRow()+1);
      }
      // 3 --
      if(tall){
         g.setColor(p1blue);
         drawThickCircle(g, (int)p1.getx()-(width/2), (int)p1.gety()-(height/2), width, height, 5);
         g.drawImage(p1.getPicture().getImage(),((int)p1.getx())-aspect/2,((int)p1.gety())-aspect/2-yoffset,aspect,aspect,null); 
      }
      else{
         g.setColor(p2red);
         drawThickCircle(g, (int)p2.getx()-(width/2), (int)p2.gety()-(height/2), width, height, 5);
         g.drawImage(p2.getPicture().getImage(),((int)p2.getx())-aspect/2,((int)p2.gety())-aspect/2-yoffset, aspect,aspect,null);
      }
      // 4 --
      if(tall){
         drawSomeTiles(g, p1.getRow()+1, p2.getRow()+1);
      }
      else{
         drawSomeTiles(g, p2.getRow()+1, p1.getRow()+1);
      }
      // 5 --
      if(tall){
         g.setColor(p2red);
         drawThickCircle(g, (int)p2.getx()-(width/2), (int)p2.gety()-(height/2), width, height, 5);
         g.drawImage(p2.getPicture().getImage(),((int)p2.getx())-aspect/2,((int)p2.gety())-aspect/2-yoffset, aspect,aspect,null);
      }
      else{
         g.setColor(p1blue);
         drawThickCircle(g, (int)p1.getx()-(width/2), (int)p1.gety()-(height/2), width, height, 5);
         g.drawImage(p1.getPicture().getImage(),((int)p1.getx())-aspect/2,((int)p1.gety())-aspect/2-yoffset,aspect,aspect,null);
      }
      // 6 -- 
      if(tall){
         drawSomeTiles(g, p2.getRow()+1, 12);
      }
      else{
         drawSomeTiles(g, p1.getRow()+1, 12);
      }
      drawBounds(g); // draw test bounds ----------------------------------------------
   }
   
   // draw a thicc circle at x, y
   public static void drawThickCircle(Graphics g, int x, int y, int width, int height, int thick){
      Graphics2D g2 = (Graphics2D)(g); // cast to Graphics2D so i can use setStroke()
      g2.setStroke(new BasicStroke(thick));
      g2.drawOval(x, y, width, height);
   }
   
   // draws all tiles from row 'start', inclusive to row 'end', exclusive
   public static void drawSomeTiles(Graphics g, int start, int end){
      for(int i=start; i<end; i++){
         for(int j=0; j<20; j++){
            ImageIcon tile;
            try{
               tile = gameTiles[i][j].getPicture(p1.getFacing(), p2.getFacing());
            }
            catch(Exception eee){
               tile = new ImageIcon("");
            }
            g.drawImage(tile.getImage(),j*32+228, i*32+178-32, 32, 64, null);
         }
      }
   }
   
   // testing method - draws the bounding box of all things with hitboxes
   public static void drawBounds(Graphics g){
      g.setColor(Color.YELLOW);
      Graphics2D g2 = (Graphics2D)(g);
      g2.setStroke(new BasicStroke(2));
      g2.draw(p1.getBounds());
      g2.draw(p2.getBounds());
      
      g2.setColor(Color.RED);
      for(int i=0; i<12; i++){
         for(int j=0; j<20; j++){
            g2.draw(gameTiles[i][j].getBounds());
         }   
      }
      
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
      // see if hovering over a button
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
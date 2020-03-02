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
   
      // players
      ImageIcon player1;
      if(p1.getDir() == UP){
         if(((((int)(p1.getdx())*20)/20)==0)&&((((int)(p1.getdy())*20)/20)==0)){ // actually idle
            player1 = new ImageIcon("images/characters/monkey-up-idle.gif");
         }
         else{
            player1 = new ImageIcon("images/characters/monkey-up-walk.gif");
         }
      }
      else if(p1.getDir() == DOWN){
         if(((((int)(p1.getdx())*20)/20)==0)&&((((int)(p1.getdy())*20)/20)==0)){ // actually idle
            player1 = new ImageIcon("images/characters/monkey-down-idle.gif");
         }
         else{
            player1 = new ImageIcon("images/characters/monkey-down-walk.gif");
         }      
      }
      else if(p1.getDir() == RIGHT){
         if(((((int)(p1.getdx())*20)/20)==0)&&((((int)(p1.getdy())*20)/20)==0)){ // actually idle
            player1 = new ImageIcon("images/characters/monkey-right-idle.gif");
         }
         else{
            player1 = new ImageIcon("images/characters/monkey-right-walk.gif");
         }      
      }
      else{ //if(p1.getDir() == LEFT){
         if(((((int)(p1.getdx())*20)/20)==0)&&((((int)(p1.getdy())*20)/20)==0)){ // actually idle
            player1 = new ImageIcon("images/characters/monkey-left-idle.gif");
         }
         else{
            player1 = new ImageIcon("images/characters/monkey-left-walk.gif");
         }      
      }
   
      g.drawImage(player1.getImage(),((int)p1.getx()),((int)p1.gety()),null);
      g.setColor(Color.BLUE);
      g.fillOval(((int)p2.getx()),((int)p2.gety()),50,50);
   }
   
   public static void drawBoard(Graphics g){
      for(int i=0; i<13; i++){
         for (int j=0; j<13; j++){
            //g.drawImage(tiles[i][j].image(), i*
         }
      }
   }
   
   public static void drawMenu(Graphics g){
      ImageIcon gradient = new ImageIcon("images/menus/gradient.png");
      g.drawImage(gradient.getImage(),0,0,null);
      ImageIcon clouds = new ImageIcon("images/menus/clouds-moving.gif");
      g.drawImage(clouds.getImage(),0,0,null);
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
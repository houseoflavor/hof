import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays.*;
import java.util.*;

public class HOFUtil extends HOF{
   static final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
   // do everything :)
   public static void doEverything(Graphics g){   
   
      if(mode.equals("menu")){
         drawMenu(g);
      }
      if(mode.equals("controls")){
         drawControls(g);
      }
      if(mode.equals("level")){
         drawLevel(g);
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
   
   // draws the level select screen
   public static void drawLevel(Graphics g){
      ImageIcon gradient = new ImageIcon("images/menus/gradient.png");
      g.drawImage(gradient.getImage(),0,0,null);
      g.drawImage(cloud.getPicture().getImage(),cloud.move(),0,null);
      g.drawImage((new ImageIcon("images/menus/vine-wall.png")).getImage(), 0, 0, null);
      for(int i=0; i<2; i++){
         for(int j=0; j<5; j++){
            int size = 160;
            int centx = j*170+220;
            int centy = i*190+280;
            String high = "";
            if(distance(mouseX, mouseY, centx, centy)<size/2-5){
               high = "H";
            }
            g.drawImage((new ImageIcon("images/menus/level/"+levels[i*2+j]+"star"+high+".png")).getImage(), centx-size/2, centy-size/2, size, size, null);
            if(i*5+j+1==10){
               g.drawImage((new ImageIcon("images/numbers/num"+high+(int)(i*5+j+1)+".png")).getImage(), centx-size/10-27, centy-size/10+55, null);
            }
            else{
               g.drawImage((new ImageIcon("images/numbers/num"+high+(int)(i*5+j+1)+".png")).getImage(), centx-size/10-7, centy-size/10+55, null);
            }
            if(high.equals("H")){
               buttonTouching = 11+i*5+j;
            }
            
         }
      }
   }
   
   public static int distance(int mx, int my, int cx, int cy){
      return (int)(Math.sqrt(Math.pow(cx-mx,2)+Math.pow(cy-my,2)));
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
      3 - 
         a) if p1 facing up, items p1 is holding THEN p1 then p1+1
         b) else, p1 blocks between p1 and p1+1, THEN items
      4 - do all (if any) blocks between p1+2 and p2
      5 - 
         a) if p2 facing up, items p2 is holding THEN p2
         b) else, p2 THEN items
      6 - do all blocks remaining
      7 - do number chops & knives
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
      if(p1.gety()<p2.gety()){
         if(p1.getDir() == UP){
            if(p1.isHold()){
               g.drawImage(p1.whatHold().getPicture().getImage(), ((int)p1.getx())-16,((int)p1.gety())-24-p1.yChange(),32,32,null);
            }
            //drawSomeTiles(g, p1.getRow()+1, p1.getRow()+2);
         }
         g.setColor(p1blue);
         drawThickCircle(g, (int)p1.getx()-(width/2), (int)p1.gety()-(height/2), width, height, 5);
         g.drawImage(p1.getPicture().getImage(),((int)p1.getx())-aspect/2,((int)p1.gety())-aspect/2-yoffset,aspect,aspect,null); 
         //if(p1.getDir() != UP){ 
         if(p1.isHold()){
            if(p1.getDir()==DOWN){
               g.drawImage(p1.whatHold().getPicture().getImage(), ((int)p1.getx())-16,((int)p1.gety())-16-p1.yChange(),32,32,null);
            }
         }
         drawSomeTiles(g, p1.getRow()+1, p1.getRow()+2);
         if(p1.isHold()){
            if(p1.getDir()==LEFT){
               g.drawImage(p1.whatHold().getPicture().getImage(), ((int)p1.getx())-30,((int)p1.gety())-20-p1.yChange(),32,32,null);
            }
            if(p1.getDir()==RIGHT){
               g.drawImage(p1.whatHold().getPicture().getImage(), ((int)p1.getx())-3,((int)p1.gety())-20-p1.yChange(),32,32,null);
            }
         }
         //}
      }
      else{
         if(p2.getDir() == UP){
            if(p2.isHold()){
               g.drawImage(p2.whatHold().getPicture().getImage(), ((int)p2.getx())-16,((int)p2.gety())-24-p2.yChange(),32,32,null);
            }
            //drawSomeTiles(g, p2.getRow()+1, p2.getRow()+2);
         }
         g.setColor(p2red);
         drawThickCircle(g, (int)p2.getx()-(width/2), (int)p2.gety()-(height/2), width, height, 5);
         g.drawImage(p2.getPicture().getImage(),((int)p2.getx())-aspect/2,((int)p2.gety())-aspect/2-yoffset, aspect,aspect,null);
         //if(p2.getDir() != UP){ 
         if(p2.isHold()){
            if(p2.getDir()==DOWN){
               g.drawImage(p2.whatHold().getPicture().getImage(), ((int)p2.getx())-16,((int)p2.gety())-16-p1.yChange(),32,32,null);
            }
         }
         drawSomeTiles(g, p2.getRow()+1, p2.getRow()+2);
         if(p2.isHold()){
            if(p2.getDir()==LEFT){
               g.drawImage(p2.whatHold().getPicture().getImage(), ((int)p2.getx())-30,((int)p1.gety())-20-p2.yChange(),32,32,null);
            }
            if(p2.getDir()==RIGHT){
               g.drawImage(p2.whatHold().getPicture().getImage(), ((int)p2.getx())-3,((int)p1.gety())-20-p2.yChange(),32,32,null);
            }
         }
         //}
      
      }
      // 4 --
      if(tall){
         drawSomeTiles(g, p1.getRow()+2, p2.getRow()+1);
      }
      else{
         drawSomeTiles(g, p2.getRow()+2, p1.getRow()+1);
      }
      // 5 --
      if(p1.gety()>p2.gety()){
         if(p1.getDir() == UP){
            if(p1.isHold()){
               g.drawImage(p1.whatHold().getPicture().getImage(), ((int)p1.getx())-16,((int)p1.gety())-24-p1.yChange(),32,32,null);
            }
            //drawSomeTiles(g, p1.getRow()+1, p1.getRow()+2);
         }
         g.setColor(p1blue);
         drawThickCircle(g, (int)p1.getx()-(width/2), (int)p1.gety()-(height/2), width, height, 5);
         g.drawImage(p1.getPicture().getImage(),((int)p1.getx())-aspect/2,((int)p1.gety())-aspect/2-yoffset,aspect,aspect,null); 
         //if(p1.getDir() != UP){ 
         if(p1.isHold()){
            if(p1.getDir()==DOWN){
               g.drawImage(p1.whatHold().getPicture().getImage(), ((int)p1.getx())-16,((int)p1.gety())-16-p1.yChange(),32,32,null);
            }
         }
         drawSomeTiles(g, p1.getRow()+1, p1.getRow()+2);
         if(p1.isHold()){
            if(p1.getDir()==LEFT){
               g.drawImage(p1.whatHold().getPicture().getImage(), ((int)p1.getx())-30,((int)p1.gety())-20-p1.yChange(),32,32,null);
            }
            if(p1.getDir()==RIGHT){
               g.drawImage(p1.whatHold().getPicture().getImage(), ((int)p1.getx())-3,((int)p1.gety())-20-p1.yChange(),32,32,null);
            }
         }
         //}
      }
      else{
         if(p2.getDir() == UP){
            if(p2.isHold()){
               g.drawImage(p2.whatHold().getPicture().getImage(), ((int)p2.getx())-16,((int)p2.gety())-24-p2.yChange(),32,32,null);
            }
            //drawSomeTiles(g, p2.getRow()+1, p2.getRow()+2);
         }
         g.setColor(p2red);
         drawThickCircle(g, (int)p2.getx()-(width/2), (int)p2.gety()-(height/2), width, height, 5);
         g.drawImage(p2.getPicture().getImage(),((int)p2.getx())-aspect/2,((int)p2.gety())-aspect/2-yoffset, aspect,aspect,null);
         //if(p2.getDir() != UP){ 
         if(p2.isHold()){
            if(p2.getDir()==DOWN){
               g.drawImage(p2.whatHold().getPicture().getImage(), ((int)p2.getx())-16,((int)p2.gety())-16-p1.yChange(),32,32,null);
            }
         }
         drawSomeTiles(g, p2.getRow()+1, p2.getRow()+2);
         if(p2.isHold()){
            if(p2.getDir()==LEFT){
               g.drawImage(p2.whatHold().getPicture().getImage(), ((int)p2.getx())-30,((int)p1.gety())-20-p2.yChange(),32,32,null);
            }
            if(p2.getDir()==RIGHT){
               g.drawImage(p2.whatHold().getPicture().getImage(), ((int)p2.getx())-3,((int)p1.gety())-20-p2.yChange(),32,32,null);
            }
         }
         //}
      
      
      }
      // 6 -- 
      if(tall){
         drawSomeTiles(g, p2.getRow()+2, 12);
      }
      else{
         drawSomeTiles(g, p1.getRow()+2, 12);
      }
      // 7 -- 
      for(int i=0; i<12; i++){
         for(int j=0; j<20; j++){
            try{
               if((p1.getFRow()==i && p1.getFCol() == j) || (p2.getFRow()==i && p2.getFCol() == j)){
                  g.drawImage((new ImageIcon("images/numbers/numH"+(int)(itemTiles[i][j].getChop())+".png")).getImage(), j*32+240, i*32+188, 9, 11, null);
               }
            }
            catch(Exception eE){}
         }
      }
      // draw knives, remove knife if done animating
      for(int i=0; i<knives.size(); i++){
         Knife k = knives.get(i);
         if(k.getFrame()==10){
            knives.remove(k);
         }
         else{
            g.drawImage(k.getPicture().getImage(), k.getX(), k.getY(), 40, 40, null);
         }
      }
      
      //drawBounds(g); // draw test bounds ----------------------------------------------
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
            ImageIcon item;
            Item physItem = new Item();
            boolean hasItem = false;
            try{
            // if p1 or p2 is looking at a tile
               try{ // test if there is an item on the tile
                  if(itemTiles[i][j] != null){
                     hasItem = true;
                     physItem = itemTiles[i][j];
                  }
               }
               catch(Exception ee){}
               tile = gameTiles[i][j].getPicture(p1.getFacing(), p2.getFacing(), hasItem);
            }
            catch(Exception eee){
               tile = new ImageIcon("");
            }
            try{
            // if p1 or p2 are looking at an item
               if((p1.getFRow()==i && p1.getFCol() == j) || (p2.getFRow()==i && p2.getFCol() == j)){
                  item = itemTiles[i][j].getHPicture();
                  
               }
               else{
                  item = itemTiles[i][j].getPicture();
               }
            }
            catch(Exception eee){
               item = new ImageIcon("");
            }
            g.drawImage(tile.getImage(),j*32+228, i*32+178-32, 32, 64, null);
            g.drawImage(item.getImage(),j*32+228+physItem.getXShake(), i*32+178-12+physItem.getXShake(), 32, 32, null);
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
      //g.drawImage(new ImageIcon("images/menus/clouds-moving-coalesced.gif").getImage(),0,0,null);
      g.drawImage(cloud.getPicture().getImage(),cloud.move(),0,null);
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
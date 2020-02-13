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
      // background drawing
   
   
   /*
      // character drawing
      g.setColor(Color.RED);
      g.fillOval(((int)p1.getx()),((int)p1.gety()),50,50);
      g.setColor(Color.BLUE);
      g.fillOval(((int)p2.getx()),((int)p2.gety()),50,50);
   */
   
   
        
   }
   
   public static void drawMenu(Graphics g){
      ImageIcon homescreen = new ImageIcon("images/homescreen-no-buttons.png");
      g.drawImage(homescreen.getImage(),0,0,1300,750,null);
      
      // buttons
      ImageIcon startun = new ImageIcon("images/start-unclicked.png");
      ImageIcon startcl = new ImageIcon("images/start-click.png");
      ImageIcon controlun = new ImageIcon("images/controls-unclicked.png");
      ImageIcon controlcl = new ImageIcon("images/controls-click.png");
      if(mouseX<360 && mouseX>0 && mouseY<365 && mouseY>273){
         g.drawImage(startcl.getImage(), 0, 0, 1300, 750, null);
      }
      else{
         g.drawImage(startun.getImage(), 0, 0, 1300, 750, null);
      }
      
      if(mouseX<360 && mouseX>0 && mouseY<546 && mouseY>454){
         g.drawImage(controlcl.getImage(), 0, 0, 1300, 750, null);
      }
      else{
         g.drawImage(controlun.getImage(), 0, 0, 1300, 750, null);
      }
   }
   
   


}
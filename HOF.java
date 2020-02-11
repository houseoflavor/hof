import java.util.*;
import java.io.*;
import java.nio.*;
import javax.swing.*;

import java.awt.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color.*;

import java.util.Timer;
import java.util.TimerTask;

public class HOF extends JPanel{

   Timer timer1 = new Timer(); // refresh screen timer
   TimerTask task1 = new TimerTask(){
      public void run(){
         repaint();
      }
   };

   static String mode; // menu, credits, ingame
   static int x1=0, y1=0, dx1=0, dy1=0;// player 1 instance ints
   static int x2=0, y2=0, dx2=0, dy2=0; // player 2 instance ints
   static ArrayList<Integer> keysDown;


   public HOF(){ // constructor for jpanel
      keysDown = new ArrayList<Integer>();

      
   }

   public void start(){
      timer1.scheduleAtFixedRate(task1,0,100); // delay = 0ms, period = 100ms
   }
   
   public void paintComponent(Graphics g){ //method override for graphics
      super.paintComponent(g);
      this.setBackground(Color.WHITE); // set color (change if you want)
      
      HOFUtil.doEverything(g); // main draw

      /* cancel timer (should never do because this timer draws everything)
      timer.cancel();
      timer.purge();
      */
   }

   public void keyCommand(Integer key){
      // IMPORTANT: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
      //if(mode.equals("ingame")){ // main chunk for in game
         if (!keysDown.contains(key)){
            keysDown.add(key);
         }
         System.out.println(key);
         if(key == KeyEvent.VK_W){ // p1 moving up
              
         }


      //}
      
   }
   
   public void keyRemove(Integer key){
      keysDown.remove(key);
   
   }



}
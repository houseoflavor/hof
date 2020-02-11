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
   TimerTask task1 = 
      new TimerTask(){
         public void run(){
            // movement
            double speed = 0.4;
            //player 1
            if(keysDown.contains(KeyEvent.VK_W)){//up
               dy1-=speed;
            }
            if(keysDown.contains(KeyEvent.VK_D)){ //right
               dx1+=speed;
            }
            if(keysDown.contains( KeyEvent.VK_S)){ //down
               dy1+=speed;
            }
            if(keysDown.contains(KeyEvent.VK_A)){ //left
               dx1-=speed;
            }
            //player 2
            if(keysDown.contains(KeyEvent.VK_I)){ //up
               dy2-=speed;
            }
            if(keysDown.contains(KeyEvent.VK_L)){ //right
               dx2+=speed;
            }
            if(keysDown.contains( KeyEvent.VK_K)){ //down
               dy2+=speed;
            }
            if(keysDown.contains(KeyEvent.VK_J)){ //left
               dx2-=speed;
            }
         
         
            //physics
            double velocity=0.8;
            x1+=dx1;
            dx1*=velocity;
            x2+=dx2;
            dx2*=velocity;
            y1+=dy1;
            dy1*=velocity;
            y2+=dy2;
            dy2*=velocity;
            //test message
            System.out.println("x: " + x1 + " y: " + y1 + " dy: " + dy1);
         
            repaint();
         }
      };


   static double x1=0, y1=550, dx1=0, dy1=0;// player 1 instance ints
   static double x2=0, y2=0, dx2=0, dy2=0; // player 2 instance ints
   static ArrayList<Integer> keysDown;


   public HOF(){ // constructor for jpanel
      keysDown = new ArrayList<Integer>();
      this.start();
      
   }

   public void start(){
      timer1.scheduleAtFixedRate(task1,0,10); // delay = 1ms, period = 10ms
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
   
      //}
      
   }
  
   public void keyRemove(Integer key){
      keysDown.remove(key);
   }



}
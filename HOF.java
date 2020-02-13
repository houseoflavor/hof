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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class HOF extends JPanel implements MouseListener, MouseMotionListener{

   Timer timer1 = new Timer(); // refresh screen timer
   TimerTask task1 = 
      new TimerTask(){
         public void run(){
            // movement
            double speed = 0.4;
            //player 1
            if(keysDown.contains(KeyEvent.VK_W)){//up
               p1.input(UP);
            }
            if(keysDown.contains(KeyEvent.VK_D)){ //right
               p1.input(RIGHT);
            }
            if(keysDown.contains(KeyEvent.VK_S)){ //down
               p1.input(DOWN);
            }
            if(keysDown.contains(KeyEvent.VK_A)){ //left
               p1.input(LEFT);
            }
            //player 2
            if(keysDown.contains(KeyEvent.VK_I)){ //up
               p2.input(UP);
            }
            if(keysDown.contains(KeyEvent.VK_L)){ //right
               p2.input(RIGHT);
            }
            if(keysDown.contains(KeyEvent.VK_K)){ //down
               p2.input(DOWN);
            }
            if(keysDown.contains(KeyEvent.VK_J)){ //left
               p2.input(LEFT);
            }
         
         
            //physics
            
            p1.move();
            p2.move();
            
            repaint();
         }
      };

   final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
   static String mode; // menu, controls, ingame
   static ArrayList<Integer> keysDown;
   static Player p1, p2;

   static int mouseX, mouseY;

   public HOF(){ // constructor
      p1 = new Player();
      p2 = new Player();
      keysDown = new ArrayList<Integer>();
      mode = "menu";
      
      //mouse stuff
      addMouseListener(this);
      addMouseMotionListener(this);
      mouseX=0;
      mouseY=0;
      
      this.start();
      
   }

   public void start(){
      timer1.scheduleAtFixedRate(task1,1,10); // delay = 1ms, period = 10ms
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
      if(mode.equals("ingame")){ // main chunk for in game
         if (!keysDown.contains(key)){
            keysDown.add(key);
         }
      
      }
      
   }
  
   public void keyRemove(Integer key){
      keysDown.remove(key);
   }
   
   // more mouse stuff
   public void mouseMoved(MouseEvent e){
      mouseX=e.getX();
      mouseY=e.getY();
      //System.out.println("mousex: " + mouseX + " mousey: " + mouseY);
   }
   public void mouseClicked(MouseEvent e){
      mouseX=e.getX();
      mouseY=e.getY();
      if(mode.equals("menu")){
         
      }
   }
   public void mousePressed(MouseEvent e){ }
   public void mouseReleased(MouseEvent e){ }
   public void mouseEntered(MouseEvent e){ }
   public void mouseExited(MouseEvent e){ }
   public void mouseDragged(MouseEvent e){ }




}
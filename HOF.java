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
// music stuff
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

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

   static String mode; // menu, controls, game
   static String transMode;
   static ArrayList<Integer> keysDown;
   static Player p1, p2;

   static int mouseX, mouseY;
   static int buttonTouching;
   
   static int transX, transition;
   
   
   //finals
   static final int NONE = 0, START = 1, CONTROLS = 2, EXIT = 3;
   static final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
   
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
      buttonTouching = NONE;
      transition = 0;
      transMode = "none";
      playMusic();
      
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
      if(mode.equals("game")){ // main chunk for in game
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
      if(mode.equals("menu") && transMode.equals("none")){
         if(buttonTouching == START){
            transMode = "game";
            transition = 1;
            transX=1500;
         }
      }
      
   }
   public void mousePressed(MouseEvent e){ }
   public void mouseReleased(MouseEvent e){ }
   public void mouseEntered(MouseEvent e){ }
   public void mouseExited(MouseEvent e){ }
   public void mouseDragged(MouseEvent e){ }

   // music
   public static void playMusic(){
      try {
         File file = new File("sounds/background-music.wav");
         AudioInputStream stream;
         AudioFormat format;
         DataLine.Info info;
         Clip clip;
      
         stream = AudioSystem.getAudioInputStream(file);
         format = stream.getFormat();
         info = new DataLine.Info(Clip.class, format);
         clip = (Clip) AudioSystem.getLine(info);
         clip.open(stream);
         // control volume
         FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
         volume.setValue(-4);
         clip.start();
         //loop music
         clip.loop(Clip.LOOP_CONTINUOUSLY);
      } 
      catch (Exception exception) {
         System.out.println("No music detected");
      }
   }
   
   
}



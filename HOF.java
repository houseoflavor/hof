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
            // dashing
            boolean p1Boost = false;
            boolean p2Boost = false;
            //p1 dash
            if(keysDown.contains(KeyEvent.VK_CONTROL) && dirKeys.contains(KeyEvent.KEY_LOCATION_LEFT)){
               if(!hasP1Boost){ // prevents players from holding dash
                  p1Boost = true;
                  hasP1Boost = true;
               }
            }
            else{
               hasP1Boost = false;
            }
            //p2 dash
            if(keysDown.contains(KeyEvent.VK_CONTROL) && dirKeys.contains(KeyEvent.KEY_LOCATION_RIGHT)){
               if(!hasP2Boost){
                  p2Boost = true;
                  hasP2Boost = true;
               }
            }
            else{
               hasP2Boost = false;
            }
            //player 1
            if(keysDown.contains(KeyEvent.VK_W)){//up
               p1.input(UP, p1Boost);
            }
            if(keysDown.contains(KeyEvent.VK_D)){ //right
               p1.input(RIGHT, p1Boost);
            }
            if(keysDown.contains(KeyEvent.VK_S)){ //down
               p1.input(DOWN, p1Boost);
            }
            if(keysDown.contains(KeyEvent.VK_A)){ //left
               p1.input(LEFT, p1Boost);
            }
            //player 2
            if(keysDown.contains(KeyEvent.VK_UP)){ //up
               p2.input(UP, p2Boost);
            }
            if(keysDown.contains(KeyEvent.VK_RIGHT)){ //right
               p2.input(RIGHT, p2Boost);
            }
            if(keysDown.contains(KeyEvent.VK_DOWN)){ //down
               p2.input(DOWN, p2Boost);
            }
            if(keysDown.contains(KeyEvent.VK_LEFT)){ //left
               p2.input(LEFT, p2Boost);
            }
         
         
            //physics
            p1.move(p2);
            p2.move(p1);
            
            
            
            repaint();
         }
      };

   static String mode; // menu, controls, game
   static String transMode;
   static ArrayList<Integer> keysDown;
   static ArrayList<Integer> dirKeys;
   static Player p1, p2;

   static boolean hasP1Boost = false, hasP2Boost = false;
   static int mouseX, mouseY;
   static int buttonTouching;
   
   static int transX, transition;
   
   static Tile [][] gameTiles;
   static Tile [][] allTiles;
   
   static int[] levels; // -1 = not beat, 0, 1, 2, 3 stars per level
   
   //static Clouds cloud;
   
   //finals
   static final int NONE = 0, START = 1, CONTROLS = 2, EXIT = 3;
   static final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
   
   public HOF(){ // constructor
      p1 = new Player("monkey",300,300);
      p2 = new Player("cat",500,500);
      keysDown = new ArrayList<Integer>();
      dirKeys = new ArrayList<Integer>();
      mode = "menu";
      
      gameTiles = new Tile[12][20];
      
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
      
      levels = new int[5]; // change for num of levels
      
      //cloud = new Clouds();  // ------------------- clouds
      
   }

   public void start(){
      timer1.scheduleAtFixedRate(task1,1,10);
   }
   
   public int lastBeat(){ //returns the index of the level that is next to beat
      for(int i=0; i<levels.length; i++){
         if(levels[i]==-1){
            return i;
         }
      }
      return levels.length-1;
   }
   
   public void paintComponent(Graphics g){ //method override for graphics
      super.paintComponent(g);      
      HOFUtil.doEverything(g); // main draw
   
      /* cancel timer (should never do because this timer draws everything)
      timer.cancel();
      timer.purge();
      */
   }
   public void update(Graphics g){
      paint(g);
   }
   
   //file reading
   public static void readFile(String fileName)throws IOException{
      Scanner input = new Scanner(new FileReader(fileName));
      String line;
      for(int i=0; i<12; i++){
         line = input.nextLine();
         //System.out.println(line);
         String [] singleLine = line.split(" ");
         for(int j=0; j<20; j++){
            gameTiles[i][j] = new Tile(singleLine[j]);
            //System.out.print(tiles[i][j].getName());
         }
      }
      input.close();
      
   
   }

   public void keyCommand(KeyEvent e){
      // IMPORTANT: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
      Integer key = new Integer(e.getKeyCode());
      Integer loc = new Integer(e.getKeyLocation());
      if(mode.equals("game")){ // main chunk for in game
         if (!keysDown.contains(key)){
            keysDown.add(key);
            dirKeys.add(loc);
         }
      
      }
      
   }
  
   public void keyRemove(KeyEvent e){
      Integer key = new Integer(e.getKeyCode());
      Integer loc = new Integer(e.getKeyLocation());
      keysDown.remove(key);
      dirKeys.remove(loc);
   }
   
   // more mouse stuff
   public void mouseMoved(MouseEvent e){
      mouseX=e.getX();
      mouseY=e.getY();
     // System.out.println("mousex: " + mouseX + " mousey: " + mouseY);
   }
   public void mouseClicked(MouseEvent e){
      mouseX=e.getX();
      mouseY=e.getY();
      if(mode.equals("menu") && transMode.equals("none")){
         if(buttonTouching == START){ // clicking start button (from menu)
            transMode = "game";
            transX=1500;            
            transition = 1;
         }
         if(buttonTouching == CONTROLS){ // clicking controls button (from menu)
            transMode = "controls";            
            transX=1500;
            transition = 1;
         }
      }
      
      if(mode.equals("controls") && transMode.equals("none")){
         if(buttonTouching == EXIT){ // clicking X button (from controls)
            transMode = "menu";
            transX=1500;
            transition = 1;
         }
      }
      
   } // interface stuff
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



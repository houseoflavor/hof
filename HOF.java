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
            if(mode.equals("game") && game != null && !game.isPaused() && game.getTime()>0){
               //if(game.getTime()<=180){
               // dashing
               try{
                  boolean p1Boost = false;
                  boolean p2Boost = false;
                  //p1 dash
                  if(keysDown.contains(KeyEvent.VK_CONTROL) && dirKeys.contains(KeyEvent.KEY_LOCATION_LEFT)){
                     if(!hasP1Boost){ // prevents players from holding dash
                        p1Boost = true;
                        hasP1Boost = true;
                        if(p1.isMoving() && (keysDown.contains(KeyEvent.VK_A) ||keysDown.contains(KeyEvent.VK_S) ||keysDown.contains(KeyEvent.VK_D) ||keysDown.contains(KeyEvent.VK_W))){
                           boost.add(new Particle((int)p1.getx()-15, (int)p1.gety()-15, "boost"));
                        }
                     }
                  }
                  else{
                     hasP1Boost = false;
                  }
                  //p2 dash
                  if(keysDown.contains(KeyEvent.VK_B)){
                     if(!hasP2Boost){
                        p2Boost = true;
                        hasP2Boost = true;
                        if(p2.isMoving() && (keysDown.contains(KeyEvent.VK_UP) ||keysDown.contains(KeyEvent.VK_RIGHT) ||keysDown.contains(KeyEvent.VK_DOWN) ||keysDown.contains(KeyEvent.VK_LEFT))){
                           boost.add(new Particle((int)p2.getx()-15, (int)p2.gety()-15, "boost"));
                        }
                     }
                  }
                  else{
                     hasP2Boost = false;
                  }
                  java.util.List<Integer> p1d = new ArrayList<Integer>(); // refer to java.util.List not java.awt.List
                  java.util.List<Integer> p2d = new ArrayList<Integer>();
                  //player 1
                  if(keysDown.contains(KeyEvent.VK_W)){//up
                     p1.input(UP, p1Boost);
                     p1d.add(UP);
                  }
                  if(keysDown.contains(KeyEvent.VK_D)){ //right
                     p1.input(RIGHT, p1Boost);
                     p1d.add(RIGHT);
                  }
                  if(keysDown.contains(KeyEvent.VK_S)){ //down
                     p1.input(DOWN, p1Boost);
                     p1d.add(DOWN);
                  }
                  if(keysDown.contains(KeyEvent.VK_A)){ //left
                     p1.input(LEFT, p1Boost);
                     p1d.add(LEFT);
                  }
                  //player 2
                  if(keysDown.contains(KeyEvent.VK_I)){ //up
                     p2.input(UP, p2Boost);
                     p2d.add(UP);
                  }
                  if(keysDown.contains(KeyEvent.VK_L)){ //right
                     p2.input(RIGHT, p2Boost);
                     p2d.add(RIGHT);
                  }
                  if(keysDown.contains(KeyEvent.VK_K)){ //down
                     p2.input(DOWN, p2Boost);
                     p2d.add(DOWN);
                  }
                  if(keysDown.contains(KeyEvent.VK_J)){ //left
                     p2.input(LEFT, p2Boost);
                     p2d.add(LEFT);
                  }
                  p1.direction(p1d);
                  p2.direction(p2d);
                  
                  
                  //physics
                  p1.move(p2);
                  p2.move(p1);
               }
               catch(Exception e){}
               //}
               if(game.ready()){
               // interact
               // p1 -----
                  if(keysDown.contains(KeyEvent.VK_E)){
                     if(!hasP1Int){ // interactions? its really big idk whats what anymore
                        int i = p1.getFRow();
                        int j = p1.getFCol();
                        hasP1Int = true;
                        if(p1.isHold()){ // holding something
                           if(gameTiles[i][j].getName().equals("wal") || gameTiles[i][j].getName().startsWith("co") || gameTiles[i][j].getName().startsWith("z") || gameTiles[i][j].getName().equals("pld")){
                           // these tiles are invalid to put something on
                           
                           }
                           else if(gameTiles[i][j].getName().startsWith("de")){ // delivering
                              if(p1.whatHold().isTool()){
                                 error = 4;
                                 errorTimer=180;
                              }
                              else if(p1.whatHold().isOven()){ // no consequence for uncooked items
                                 if(p1.whatHold().hasPlate()){
                                 // you do lose the item if the item is plated however
                                    if(game.deliver(p1.whatHold())){ // returns boolean is successful, if future me wants to use
                                    }
                                    else{
                                       error = 3;
                                       errorTimer = 180;
                                    }
                                    p1.drop();
                                 } 
                                 else{
                                    error = 1;
                                    errorTimer = 180;
                                 } 
                              }
                              else{
                                 error = 2;
                                 errorTimer = 180;
                              }
                           }
                           else if(gameTiles[i][j].getName().equals("pla")){ // taking plate while holding something
                              if(gameTiles[i][j].getPlates()>0){
                                 //System.out.println("taking plate");
                                 Item tempPlate = new Item("pla", false, true);
                                 if(p1.whatHold().combine(tempPlate)){ // if successfully combine
                                    gameTiles[i][j].takePlate(); // take a plate
                                 
                                 }
                              }
                           }
                           else if(gameTiles[i][j].getName().startsWith("siw") && p1.whatHold().getName().equals("pld")){ // putting in sink water plate
                              gameTiles[i][j].addPlate();
                              p1.drop();
                           }
                           else if(gameTiles[i][j].getName().startsWith("siw")){
                           }
                           else if(gameTiles[i][j].getName().equals("ove")){ // adding food to oven
                              if(itemTiles[i][j] == null && p1.whatHold().oven()){ // nothing in the oven? & can be put in oven?
                                 sound("oven", 2);
                                 itemTiles[i][j] = p1.whatHold();
                                 gameTiles[i][j].turnOn();
                                 p1.drop();
                                 loader.add(new Loader(20, p1.getFCol(), p1.getFRow(), itemTiles[i][j].getCook()));
                              }
                              else if(itemTiles[i][j] != null && p1.whatHold().isPlate()){
                                 if(itemTiles[i][j].getCook()==1121){
                                    sound("grab", 5);
                                    p1.pickUpEmpty(itemTiles[i][j]);
                                    p1.whatHold().takeOut();
                                    itemTiles[i][j].ovenCooked();
                                    itemTiles[i][j] = null;
                                    gameTiles[i][j].turnOff();
                                    for(int k=0; k<loader.size(); k++){
                                       if(i==loader.get(k).getRow() && j==loader.get(k).getCol()){
                                          loader.remove(k);
                                          break;
                                       }
                                    }
                                    Item tempPlate = new Item("pla", false, true);
                                    p1.whatHold().combine(tempPlate);
                                 
                                 }
                              }
                           }
                           else if(itemTiles[i][j] == null){   // space is empty
                           // test if trash
                              if(gameTiles[i][j].getName().equals("tra")){
                              
                                 if(p1.whatHold().isFood()){
                                    p1.drop();
                                    itemTiles[i][j] = null;
                                 }
                              }
                              else if(gameTiles[i][j].getName().startsWith("de")){ // delivery = check if valid
                                 sound("grab", 5);
                              
                                 if(p1.whatHold().isOven() && p1.whatHold().hasPlate()){
                                    game.deliver(p1.whatHold());
                                    p1.drop(); // lose item if cooked (regardless if correct or not)
                                 }
                              // if not cooked, don't lose it
                              }
                              else if(gameTiles[i][j].getName().equals("bur")){
                                 if(itemTiles[i][j] == null){
                                    sound("grab", 5);
                                 
                                    itemTiles[i][j] = p1.whatHold();
                                    if(p1.whatHold().getName().contains("pan") && p1.whatHold().getName().contains("tom")){
                                    
                                       loader.add(new Loader(10, p1.getFCol(), p1.getFRow(), itemTiles[i][j].getPanCook()));
                                       p1.drop();
                                       sound("sizz", 0);
                                    }
                                    else{
                                       sound("grab", 5);
                                       p1.drop();
                                    }
                                 }
                              }
                              else if (!gameTiles[i][j].getName().equals("") && !gameTiles[i][j].getName().equals("ove")){// does tile exist
                                 if(itemTiles[i][j]==null){// does the tile already have something on it
                                    sound("grab", 5);
                                    itemTiles[i][j] = p1.whatHold();
                                    p1.drop();
                                 }
                              }
                           }
                           else{ //tile is already occupied
                           // test if combination is possible
                              if(itemTiles[i][j] != null && p1.whatHold().getName().contains("dou") && itemTiles[i][j].getName().contains("pan") && itemTiles[i][j].getName().contains("tom") && itemTiles[i][j].getPanned().equals("P")){ // hold dough pick up tomatoes from pan
                                 Item tempTom = new Item("top", true, false);
                                 tempTom.setChopped();
                                 if(p1.whatHold().combine(tempTom)){
                                    Item tempPan = new Item("pan", false, true);
                                    itemTiles[i][j] = null;
                                    itemTiles[i][j] = tempPan;
                                    for(int k=0; k<loader.size(); k++){
                                       if(i==loader.get(k).getRow() && j==loader.get(k).getCol()){
                                          loader.remove(k);
                                          break;
                                       }
                                    }
                                 }
                              }
                              else if(itemTiles[i][j]!= null && itemTiles[i][j].getName().equals("pan")){ // putting tomato into a pan
                              
                                 if(itemTiles[i][j].combine(p1.whatHold())){
                                    p1.drop();
                                    if(gameTiles[i][j].getName().equals("bur")){ // only add loader if the pan is on a burner
                                       loader.add(new Loader(10, p1.getFCol(), p1.getFRow(), itemTiles[i][j].getPanCook()));
                                       sound("sizz", 0);
                                    }
                                 }
                              }
                              else if(itemTiles[i][j] != null && p1.whatHold().getName().contains("pan")){ // hold pan onto tomatoas
                                 if(itemTiles[i][j].getName().contains("tom")){
                                    if(itemTiles[i][j].combine(p1.whatHold())){
                                    
                                       p1.drop();
                                       if(gameTiles[i][j].getName().equals("bur")){
                                          loader.add(new Loader(10, p1.getFCol(), p1.getFRow(), itemTiles[i][j].getPanCook()));
                                          sound("sizz", 0);
                                       }
                                    }
                                 }
                                 else if(itemTiles[i][j].getName().contains("dou") && !itemTiles[i][j].getName().contains("top") && p1.whatHold().getPanned().equals("P")){ // hold pan onto something with dough
                                 
                                    Item tempTom = new Item("top", true, false);
                                    tempTom.setChopped();
                                    if(itemTiles[i][j].combine(tempTom)){
                                       Item tempPan = new Item("pan", false, true);
                                       p1.drop();
                                       p1.pickUpEmpty(tempPan);
                                    }
                                 }
                              }
                              else{
                              
                                 if(itemTiles[i][j].combine(p1.whatHold())){
                                    p1.pickUpEmpty(itemTiles[i][j]);
                                    itemTiles[i][j] = null;
                                 }
                              }
                           }
                        }
                        else{ // picking up
                           if(!p1.isHold()){ // player's hand is empty
                              if(gameTiles[i][j].getName().startsWith("z")){} // obstacle can't be picked up
                              else if(gameTiles[i][j].isSpawner()){ // is this tile a spawner
                                 sound("grab", 5);
                              
                                 if(itemTiles[i][j]==null){ // is empty?
                                 // create a new food object and place it on the tile
                                    itemTiles[i][j] = new Item(gameTiles[i][j].getName(), true, false);
                                 // have the player pick it up
                                    p1.pickUpEmpty(itemTiles[i][j]);
                                    itemTiles[i][j] = null;
                                 }
                                 else{ // already has something, pick up
                                 
                                    p1.pickUpEmpty(itemTiles[i][j]);
                                    itemTiles[i][j] = null;
                                 }
                              }
                              else if(gameTiles[i][j].getName().equals("pld")){
                                 if(gameTiles[i][j].getPlates()>0){
                                 //System.out.println("taking plate");
                                    Item tempPlate = new Item("pld", false, true);
                                    p1.pickUpEmpty(tempPlate);
                                    gameTiles[i][j].takePlate();
                                 
                                 }
                              }
                              else if(gameTiles[i][j].getName().equals("pla")){
                                 if(gameTiles[i][j].getPlates()>0){
                                 
                                    //System.out.println("taking plate");
                                    Item tempPlate = new Item("pla", false, true);
                                    p1.pickUpEmpty(tempPlate);
                                    gameTiles[i][j].takePlate(); // take a plate
                                 }
                              }
                              else if(!gameTiles[i][j].getName().equals("")){ // does tile exist
                                 if(itemTiles[i][j]!=null){// does the tile actually have something on it
                                    sound("grab", 5);
                                 
                                    if(gameTiles[i][j].getName().equals("ove")){ // taking something out of oven
                                       p1.pickUpEmpty(itemTiles[i][j]);
                                       p1.whatHold().takeOut();
                                       if(itemTiles[i][j].getCook()==1121){
                                          itemTiles[i][j].ovenCooked();
                                       }
                                       itemTiles[i][j] = null;
                                       gameTiles[i][j].turnOff();
                                       for(int k=0; k<loader.size(); k++){
                                          if(i==loader.get(k).getRow() && j==loader.get(k).getCol()){
                                             loader.remove(k);
                                             break;
                                          }
                                       }
                                    }
                                    else if(gameTiles[i][j].getName().equals("bur")){ // picking up pan with food in it and on burner
                                       sound("grab", 5);
                                       p1.pickUpEmpty(itemTiles[i][j]);
                                       itemTiles[i][j] = null;
                                       for(int k=0; k<loader.size(); k++){
                                          if(i==loader.get(k).getRow() && j==loader.get(k).getCol()){
                                             loader.remove(k);
                                             break;
                                          }
                                       }
                                    }
                                    else{
                                       sound("grab", 5);
                                       p1.pickUpEmpty(itemTiles[i][j]);
                                       itemTiles[i][j] = null;
                                    }
                                 }
                              }
                           }
                           else{ // p1 hand NOT empty (and picking up)
                           
                           
                           }
                        }
                     }
                  }
                  else{
                     hasP1Int = false;
                  }
                  if(keysDown.contains(KeyEvent.VK_O)){
                  
                  
                   // RECOPY EVERYTHING HERE
                   
                   
                  }
                  else{
                     hasP2Int = false;
                  }
               // chopping
                  if(keysDown.contains(KeyEvent.VK_SHIFT) && dirKeys.contains(KeyEvent.KEY_LOCATION_LEFT) && !p1.isHold()){
                     int i=p1.getFRow();
                     int j=p1.getFCol();
                     if(gameTiles[i][j].getName().equals("cut")){
                        try{ // try to access a chop, if not then go to catch
                           if(itemTiles[p1.getFRow()][p1.getFCol()].canChop() && !hasP1Chop && gameTiles[p1.getFRow()][p1.getFCol()].getName().equals("cut")){
                              hasP1Chop = true;
                              itemTiles[p1.getFRow()][p1.getFCol()].chop();
                              knives.add(new Particle(p1.getFCol()*32+222, p1.getFRow()*32+178-12, "knife"));
                              sound("chop", 5);
                           }
                        }
                        catch(Exception e){} // no item here
                     }
                     else if(gameTiles[i][j].getName().equals("siw") && !hasP1Chop){
                        if(gameTiles[i][j].getPlates()>0){
                           hasP1Chop = true;
                           gameTiles[i][j].takePlate();
                           gameTiles[sidX][sidY].addPlate();
                        }
                     }
                  }
                  else{
                     hasP1Chop = false;
                  }
                  if(keysDown.contains(KeyEvent.VK_V) || keysDown.contains(KeyEvent.VK_OPEN_BRACKET) && !p2.isHold()){
                     //RECOPY CHOP
                  }
                  else{
                     hasP2Chop = false;
                  }
                  
               
               }
            
            // plate stuff
               try{
                  if(game!=null && game.shouldSpawn() && numPlates<4){
                     gameTiles[platY][platX].addPlate();
                     game.spawnPlate();
                  }
               }
               catch(Exception e){}
               
               if(keysDown.contains(KeyEvent.VK_ESCAPE) && game.ready()){ // pause
                  game.pause();
               }
               
            }
            else if(mode.equals("charsel") && transX==1500){
               if(keysDown.contains(KeyEvent.VK_W)){
                  if(!hasP1W){
                     hasP1W = true;
                     if(p1Sel<3){
                        if(p1Sel+3 != p2Sel){
                           p1Sel+=3;
                        }
                     }
                     else if(p1Sel-3 != p2Sel){
                        p1Sel-=3;
                     }
                  }
               }
               else{
                  hasP1W = false;
               }
               if(keysDown.contains(KeyEvent.VK_A)){
                  if(!hasP1A){
                     hasP1A = true;
                     if(p1Sel%3==0){
                        if(p1Sel+2 != p2Sel){
                           p1Sel+=2;
                        }
                     }
                     else if(p1Sel-1 != p2Sel){
                        p1Sel-=1;
                     }
                  }
               }
               else{
                  hasP1A = false;
               }
               if(keysDown.contains(KeyEvent.VK_S)){
                  if(!hasP1S){
                     hasP1S = true;
                     if(p1Sel>2){
                        if(p1Sel-3 != p2Sel){
                           p1Sel-=3;
                        }
                     }
                     else if(p1Sel+3 != p2Sel){
                        p1Sel+=3;
                     }
                  }
               }
               else{
                  hasP1S = false;
               }
               if(keysDown.contains(KeyEvent.VK_D)){
                  if(!hasP1D){
                     hasP1D = true;
                     if(p1Sel%3==2){
                        if(p1Sel-2 != p2Sel){
                           p1Sel-=2;
                        }
                     }
                     else if(p1Sel+1 != p2Sel){
                        p1Sel+=1;
                     }
                  }
               }
               else{
                  hasP1D = false;
               }
               if(keysDown.contains(KeyEvent.VK_I)){
                  if(!hasP2W){
                     hasP2W = true;
                     if(p2Sel<3){
                        if(p2Sel+3 != p1Sel){
                           p2Sel+=3;
                        }
                     }
                     else if(p2Sel-3 != p1Sel){
                        p2Sel-=3;
                     }
                  }
               }
               else{
                  hasP2W = false;
               }
               if(keysDown.contains(KeyEvent.VK_J)){
                  if(!hasP2A){
                     hasP2A = true;
                     if(p2Sel%3==0){
                        if(p2Sel+2 != p1Sel){
                           p2Sel+=2;
                        }
                     }
                     else if(p2Sel-1 != p1Sel){
                        p2Sel-=1;
                     }
                  }
               }
               else{
                  hasP2A = false;
               }
               if(keysDown.contains(KeyEvent.VK_K)){
                  if(!hasP2S){
                     hasP2S = true;
                     if(p2Sel>2){
                        if(p2Sel-3 != p1Sel){
                           p2Sel-=3;
                        }
                     }
                     else if(p2Sel+3 != p1Sel){
                        p2Sel+=3;
                     }
                  }
               }
               else{
                  hasP2S = false;
               }
               if(keysDown.contains(KeyEvent.VK_L)){
                  if(!hasP2D){
                     hasP2D = true;
                     if(p2Sel%3==2){
                        if(p2Sel-2 != p1Sel){
                           p2Sel-=2;
                        }
                     }
                     else if(p2Sel+1 != p1Sel){
                        p2Sel+=1;
                     }
                  }
               }
               else{
                  hasP2D = false;
               }
               if(keysDown.contains(KeyEvent.VK_ENTER)){
                  transMode = "level";
                  transX=1500;            
                  transition = 1;
                  name1 = chars[p1Sel];
                  name2 = chars[p2Sel];
               }
            }
            if(mode.equals("restart")){
               p1 = null;
               p2 = null;
               int curLevel = game.getLevel();
               gameTiles = new Tile[12][20];
               itemTiles = new Item[12][20];
               knives = new ArrayList<Particle>();
               loader = new ArrayList<Loader>();
               game = null;
               try{
                  readFile("maps/level"+curLevel+".txt");
               }
               catch(Exception e){}
               p1 = new Player(name1,start1x,start1y);
               p2 = new Player(name2,start2x,start2y);
               p1.setLevel(gameTiles);
               p2.setLevel(gameTiles);
               game.cancel();
               game = new Game(buttonTouching-10);
               mode = "game";
               transMode = "game";
            }
            if(game!= null && game.getTime()==-3 && !transMode.equals("score")){
               transMode = "score";
               transition = 1;
               countScore = -120;
               scoreStage = 0;
            }
            if(mode.equals("score")){
               countScore++;
            }
            
            repaint();
         }
      };

   static String mode; // menu, controls, game
   static String transMode;
   static ArrayList<Integer> keysDown;
   static ArrayList<Integer> dirKeys;
   static Player p1, p2;
   
   static int countScore;
   static int scoreStage;

   static boolean hasP1Boost = false, hasP2Boost = false;
   static boolean hasP1Int = false, hasP2Int = false;
   static boolean hasP1Chop = false, hasP2Chop = false;
   static ArrayList<Particle> knives;
   static ArrayList<Loader> loader;
   static ArrayList<Particle> boost;
   static int mouseX, mouseY;
   static int buttonTouching;
   
   static int error=-1;
   static int errorTimer=0;
   
   static int shift;
   
   static String name1="monkey", name2="chicken";   
   static int start1x, start1y, start2x, start2y, star1, star2, star3, highscore;
   static int p1Sel=0, p2Sel=1;
   
   static boolean hasP1W=false,hasP1A=false,hasP1S=false,hasP1D=false;
   static boolean hasP2W=false,hasP2A=false,hasP2S=false,hasP2D=false;
   
   static int numPlates;
   static boolean newhs = false;
   
   static Game game;
   
   static int transX, transition;
   
   static int platX, platY;
   static int sidX, sidY;
   
   static Tile [][] gameTiles;
   static Item [][] itemTiles;
   
   static int[] levels; // 0, 1, 2, 3 stars per level
   
   static Clouds cloud;
   
   //finals
   static final int NONE = 0, START = 1, CONTROLS = 2, EXIT = 3, CHARSEL = 4; // 11+ is level 1, 2, 3, 4, 5 etc.
   static final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
   static final String [] chars = {"monkey", "chicken", "cat", "mouse", "pig", "bunny"};
   
   public HOF(){ // constructor
      keysDown = new ArrayList<Integer>();
      dirKeys = new ArrayList<Integer>();
      mode = "main";
      
      gameTiles = new Tile[12][20];
      itemTiles = new Item[12][20];
      
      knives = new ArrayList<Particle>();
      loader = new ArrayList<Loader>();
      boost = new ArrayList<Particle>();
      
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
      
      cloud = new Clouds();
      
      HOFUtil.setup();
      
      updateLevel();
      
   }
   // stars the timer, 10ms refresh
   public void start(){
      timer1.scheduleAtFixedRate(task1,1,10);
   }
   
   public static void updateLevel(){
      levels = new int[10]; // change for num of levels
      for(int i=0; i<10; i++){
         try{
            readFile("maps/level"+(i+1)+".txt");
            levels[i] = (highscore<star1) ? 0 : ((highscore<star2) ? 1 : ((highscore<star3) ? 2 : 3)); // we love nested ternaries
         }
         catch(Exception e){}
      }
   }
   
   //returns the index of the level that is next to beat
   public static int lastBeat(){ 
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
      gameTiles = new Tile[12][20];
      itemTiles = new Item[12][20];
      Scanner input = new Scanner(new FileReader(fileName));
      String line;
      for(int i=0; i<12; i++){
         line = input.nextLine();
         //System.out.println(line);
         String [] singleLine = line.split(" ");
         for(int j=0; j<20; j++){
            // if tomato, dough, cheese, sausage spawner
            String spawners = " tom dou che sau mus ";
            if(singleLine[j].startsWith("z")){// obstacle
               gameTiles[i][j] = new Tile(singleLine[j], i, j, true, false);
            }
            else if(spawners.contains(" "+ singleLine[j] + " ")){
               gameTiles[i][j] = new Tile(singleLine[j],i,j,true,true);
            }
            else if(singleLine[j].equals("bur")){
               itemTiles[i][j] = new Item("pan", false, true);
               gameTiles[i][j] = new Tile(singleLine[j], i, j, true, false);
            }
            else if(singleLine[j].startsWith("co")){
               gameTiles[i][j] = new Tile(singleLine[j], i, j, false, false);
            }
            else if(singleLine[j].startsWith("pl")){
               gameTiles[i][j] = new Tile(singleLine[j], i, j, true, false);
               platX = j;
               platY = i;
            }
            else if(singleLine[j].equals("sid")){
               gameTiles[i][j] = new Tile(singleLine[j], i, j, true, false);
               sidX = j;
               sidY = i;
            }
            else if(!singleLine[j].equals("flo")){
               gameTiles[i][j] = new Tile(singleLine[j], i, j, true,false);
            }
            else{
               gameTiles[i][j] = new Tile("",i,j, false,false);
            }
            //System.out.print(tiles[i][j].getName());
         }
      }
      try{
         line = input.nextLine();
         String [] singleLine = line.split(" ");
         start1x = Integer.valueOf(singleLine[0]);
         start1y = Integer.valueOf(singleLine[1]);
         line = input.nextLine();
         singleLine = line.split(" ");
         start2x = Integer.valueOf(singleLine[0]);
         start2y = Integer.valueOf(singleLine[1]);
         line = input.nextLine();
         star1 = Integer.valueOf(line);
         line = input.nextLine();
         star2 = Integer.valueOf(line);
         line = input.nextLine();
         star3 = Integer.valueOf(line);
         line = input.nextLine();
         highscore = Integer.valueOf(line);
         input.close();
      }
      catch(Exception e){
         start1x = 388;
         start1y = 370;
         start2x = 708;
         start2y = 370;
         star1=100;
         star2=200;
         star3=300;
         highscore = 0;
      }
      
   
   }
      


   public void keyCommand(KeyEvent e){
      // IMPORTANT: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
      Integer key = new Integer(e.getKeyCode());
      Integer loc = new Integer(e.getKeyLocation());
      if (!keysDown.contains(key)){
         keysDown.add(key);
         dirKeys.add(loc);
      }
      
   }
  
   // called when the player untypes a key
   public void keyRemove(KeyEvent e){
      Integer key = new Integer(e.getKeyCode());
      Integer loc = new Integer(e.getKeyLocation());
      keysDown.remove(key);
      dirKeys.remove(loc);
      if(key==45){ // minus
         if(shift>0){
            shift-=5;
         }
      }
      else if(key==61){
         if(shift<100){
            shift+=5;
         }
      }
   }
   
   // mouse stuff
   public void mouseMoved(MouseEvent e){
      mouseX=e.getX();
      mouseY=e.getY();
      System.out.println("mousex: " + mouseX + " mousey: " + mouseY); // mouse ---------
   }
   public void mouseClicked(MouseEvent e){
      mouseX=e.getX();
      mouseY=e.getY();
      if(mode.equals("main") && transMode.equals("none")){
         transMode = "menu";
         transX = 1500;
         transition = 1;
      }
      else if(mode.equals("menu") && transMode.equals("none")){
         if(buttonTouching == START){ // clicking start button (from menu)
            transMode = "level";
            transX=1500;            
            transition = 1;
         }
         if(buttonTouching == CONTROLS){ // clicking controls button (from menu)
            transMode = "controls";            
            transX=1500;
            transition = 1;
           
         }
      }
      
      else if(mode.equals("controls") && transMode.equals("none")){
         if(buttonTouching == EXIT){ // clicking X button (from controls)
            transMode = "menu";
            transX=1500;
            transition = 1;
         }
      }
      
      else if(mode.equals("game") && transMode.equals("none")){
         if(buttonTouching == 1){
            game.resume();
         }
         if(buttonTouching == 2){
            transMode = "restart";
            transition = 1;
         }
         if(buttonTouching == 3){ // quit
            transMode = "level";
            transition = 1;
            game.resume();
            game.cancel();
         }
      }
      else if(mode.equals("score") && transMode.equals("none")){
         if(buttonTouching == EXIT){
            transMode = "level";
            transX=1500;
            transition = 1;
            newhs= false;
            updateLevel();
         }
      }
      else if(mode.equals("level") && transMode.equals("none")){
         if(buttonTouching >10){ // level select
            transMode = "game";
            try{
               readFile("maps/level"+(buttonTouching-10)+".txt"); // --------------------------------- read file
               p1 = new Player(name1,start1x,start1y);
               p2 = new Player(name2,start2x,start2y);
               p1.setLevel(gameTiles); // very important to have these 2 lines!!
               p2.setLevel(gameTiles);
               countScore=0;
               scoreStage=0;
               game = new Game(buttonTouching-10);
            }
            catch(Exception ee){
               System.out.println("something broke");
            }
            transX=1500;            
            transition = 1;
         }
         else if(buttonTouching == 4){
            transMode = "charsel";
            transX=1500;
            transition = 1;
         }
         else if(buttonTouching == 5){
            transMode = "menu";
            transX=1500;
            transition = 1;
         }
      }
   } 
   // interface overwriting
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
         volume.setValue(-6);
         clip.start();
         //loop music
         clip.loop(Clip.LOOP_CONTINUOUSLY);
      } 
      catch (Exception exception) {
         System.out.println("No music detected");
      }
   }
   public static void sound(String name, int vol){
      try{
         File file = new File("sounds/"+name+".wav");
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
         volume.setValue(vol);
         clip.start();
      }
      catch(Exception e){}
   }
   
   
}



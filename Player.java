import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.*;
import java.util.*;

public class Player{
   // instance vars
   private double x,y,dx=0,dy=0;
   private Item holding;
   private boolean hold;
   private int direction;
   private List<Integer> directions;
   private String name;
   private final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4, NONE = 0;
   private boolean onCon = false;
   private boolean press = false;
   private int ai = 0;
   
   // CONTROL PLAYER SPEED HERE ---
   private final double speed = 0.3;    // 0 < speed                                   recommended:   0.3
   private final double velocity = 0.8; // 0 (stop quickly) < velo < 1 (slippery)                     0.8
   private final double boostAmt = 4;   // 1 < boostAmt                                               4
   // -----------------------------
   
   private ImageIcon [][][] frames; //[active or idle][direction][frameNum]
   private int activeFrame = 0;
   private int idleFrame = 0;
   private final int activeSpeed = 7; // change speed of animation here, 1 = fastest
   private final int idleSpeed = 75; // idle speed
   
   private Tile [][] level;
   // maxSpeed is useful for calculating when players can boost (if <= max speed)
   private final double maxSpeed = ((speed*velocity)/(1-velocity));
   
   //constructor
   public Player(String name, int x, int y){
      this.name=name;
      this.x=x;
      this.y=y;
      direction = DOWN;
      directions = new ArrayList<Integer>();
      frames = new ImageIcon[2][4][4];
      hold = false;
      
      String type = "active";
      // fill 3d array with imageicons for reference when doing getPicture
      for(int a=0; a<2; a++){ // active and idle
         for(int i=0; i<4; i++){ // up right down left
            for(int j=0; j<4; j++){ // indv frame
               String dir="";
               if(i==UP-1){
                  dir = "up";
               }
               if(i==DOWN-1){
                  dir = "down";
               }
               if(i==RIGHT-1){
                  dir = "right";
               }
               if(i==LEFT-1){
                  dir = "left";
               }
               frames[a][i][j] = new ImageIcon("images/characters/"+name+"/"+type+"/"+dir+"/frame_"+String.valueOf(j)+".gif");
            }
         }
         type = "idle";
      }
      
   }

   // returns an ImageIcon of the current frame to show
   // also advances animation by 1 frame
   public ImageIcon getPicture(){
      if(!press){ // if idle
         try{
            return frames[ai][directions.get(directions.size()-1)-1][idleFrame/idleSpeed];
         }
         catch(Exception e){
            return new ImageIcon("images/characters/"+name+"/idle/"+direction+"/frame_0.gif");
         }
      }
      else{ // moving
         return frames[ai][directions.get(directions.size()-1)-1][activeFrame/activeSpeed];
      }
   }
   
   public void advance(){
      if(!press){ // if idle
         ai = 1;
         idleFrame++;
         if(idleFrame==((4*idleSpeed)-(int)(idleSpeed/1.2))){ // -idleSpeed/# to make the closed eye part a lot faster
            idleFrame=0;
         }
      }
      else{ // moving
         ai = 0;
         activeFrame++;
         if(activeFrame==(4*activeSpeed)){
            activeFrame=0;
         }
      }
   }
   
   // returns how the y changes of an item
   public int yChange(){
      if((((int)(dx*200)/200)==0)&&((((int)(dy*200)/200)==0))){
         return 0;
      }
      else{
         return 2*((int)(activeFrame/activeSpeed)%2);
      }
   }
   
   // return a rectangle that surrounds the base of the player (the rotating circle)
   public Rectangle getBounds(){
      return new Rectangle((int)x-13, (int)y-10, 26, 20);
   }  
   
   // called whenever a new level is read from a file
   public void setLevel(Tile[][] l){
      level = l;
   }   
   
   //accessors
   public double getx(){
      return x;
   }
   public double gety(){
      return y;
   }
   public double getdx(){
      return dx;
   }
   public double getdy(){
      return dy;
   }
   public int getDir(){
      return direction;
   } 
   
   // set movement
   public void setdx(double d){
      dx=d;   
   }
   public void setdy(double d){
      dy=d;
   }
   public void setx(double d){
      x=d;   
   }
   public void sety(double d){
      y=d;
   }
   // if player is holding something
   public boolean isHold(){
      return hold;
   }
   
   // picks up an item if the hand is empty
   public void pickUpEmpty(Item other){
      holding = other;
      hold = true;
   }
   // drop what is being held
   public void drop(){
      hold = false;
      holding = null;
   }
   // returns what is being held
   public Item whatHold(){
      return holding;
   }
   
   
   // calculate max speed: https://www.desmos.com/calculator/vcbpsjishq
   // moving
   public void input(int dir, boolean boost){
      if(dir==UP){ // up
         if(boost && Math.abs(dy)<=maxSpeed+0.1){
            dy-=maxSpeed*boostAmt;
         }
         else{
            dy-=speed;
         }
      }
      if(dir==RIGHT){ //right
         if(boost && Math.abs(dx)<=maxSpeed+0.1){
            dx+=maxSpeed*boostAmt;
         }
         else{
            dx+=speed;
         }
      }
      if(dir==DOWN){ //down
         if(boost && Math.abs(dy)<=maxSpeed+0.1){
            dy+=maxSpeed*boostAmt;
         }
         else{
            dy+=speed;
         }
      }
      if(dir==LEFT){ //left
         if(boost && Math.abs(dx)<=maxSpeed+0.1){
            dx-=maxSpeed*boostAmt;
         }
         else{
            dx-=speed;
         }
      }
      direction = dir;
   }
   
   // sets the array list of all directions one is facing, defaults to the previous direction if none is given
   public void direction(List<Integer> l){
      directions = l;
      if(l.size()==0){
         press = false;
         directions.add(direction);
      }
      else{
         press = true;
      }
   }
   // tests of the bounding box of 'this' collides with 'other', whether that be a Tile or Player object (for now...)
   public boolean collision(Object o){
      if(o instanceof Player){
         Player other = (Player)(o);
         return other.getBounds().intersects(getBounds());
      }
      else if(o instanceof Tile){
         Tile other = (Tile)(o);
         return other.getBounds().intersects(getBounds());
      }
      return false;
   }
   
   // returns the tile value of the player's position in the board ([0][0] = 0, [0][20] = 20, [1][0] = 21 aka wrap around, read like a book)
   public int getLoc(){
      return ((int)(x-228)/32)+(20*((int)(y-178)/32));
   }
   // returns the tile value the player is looking at
   public int getFacing(){
      int f = getLoc();
      if(directions.get(directions.size()-1)==UP){
         return f-20;
      }
      if(directions.get(directions.size()-1)==DOWN){
         return f+20;
      }
      if(directions.get(directions.size()-1)==RIGHT){
         return f+1;
      }
      else{ // dir==LEFT
         return f-1;
      }
   }
   // returns the row value of the tile the player is looking at
   public int getFRow(){
      if(directions.get(directions.size()-1)==UP){
         return getRow()-1;
      }
      if(directions.get(directions.size()-1)==DOWN){
         return getRow()+1;
      }
      if(directions.get(directions.size()-1)==RIGHT){
         return getRow();
      }
      else{ // dir==LEFT
         return getRow();
      }
   }
   // returns the column value of the tiel the player is looking at
   public int getFCol(){
      if(directions.get(directions.size()-1)==UP){
         return getCol();
      }
      if(directions.get(directions.size()-1)==DOWN){
         return getCol();
      }
      if(directions.get(directions.size()-1)==RIGHT){
         return getCol()+1;
      }
      else{ // dir==LEFT
         return getCol()-1;
      }
   }
   // returns the row value, from 1-12 the player is in
   public int getRow(){
      return (int)(y-178)/32;
   }
   // returns the column value, from 0-20 the player is in
   public int getCol(){
      return (int)(x-228)/32;
   }
   
   // moves and checks collisions
   public void move(Player other){
      // move with velocity
      x+=dx;
      y+=dy;
      
      // ALL collision detection 
      // game screen collision (board size)
      if(x<228+15){ // left bound
         x=228+15; // +13 - half of width, +2 for line thickness = +15
         dx=0;
      }
      if(x>868-15){ // right bound
         x=868-15;
         dx=0;
      }
      if(y<178+12){ // top bound
         y=178+12; // +10 - half of height, +2 for line thickness = +12
         dy=0;
      }
      if(y>562-12){ // bottom bound
         y=562-12;
         dy=0;
      }
      
      // player-player collision
      if(collision(other)){ // one player is inside the other
         double savedx=dx; // check x positions first
         x-=savedx;
         if(collision(other)){ // retain original movement if this was not the problem
            x+=savedx;
         }
         else{
            dx=0;
            other.setdx(0);
         }
      }
      if(collision(other)){
         double savedy=dy; // check y positions
         y-=savedy;
         if(collision(other)){
            y+=savedy;
         }
         else{
            dy=0; // if colliding, stop the other player from moving
            other.setdy(0);
         }
      }
      
      // tile collision
      int row = getRow();
      int col = getCol();
      double savedx=dx;
      double savedy=dy; 
      // colliding directly above or below
      if(collision(level[row+1][col]) || collision(level[row-1][col]) ){ // player is inside tile
         y-=savedy; // move out of tile
         if(collision(level[row+1][col]) || collision(level[row-1][col])){ // check when was this a bad decision (am i still colliding?)
            y+=savedy; // if still colliding, move back to original position
         }
         else{
            dy=0;
         }
      }
      // colliding directly left or right
      if(collision(level[row][col+1]) || collision(level[row][col-1])){ // player is inside tile
         x-=savedx; // move out
         if(collision(level[row][col+1]) || collision(level[row][col-1])){
            x+=savedx;
         }
         else{
            dx=0;
         }
      }  
      // bottom right
      if(collision(level[row+1][col+1])){
         // am i moving upright or downleft?
         //lowest priority > UP, RIGHT, DOWN, LEFT > highest priority
         if(directions.contains(RIGHT) && directions.contains(DOWN)){ // trying to clip in
            dx=0;
            dy=0;
            x-=savedx;
            y-=savedy;
         }
         else if(directions.contains(RIGHT) || directions.contains(UP)){
            dx=0;
            x-=savedx;
         }
         else{ // down or left
            dy=0;
            y-=savedy;
         }
      }
      // bottom left
      if(collision(level[row+1][col-1])){
         if(directions.contains(DOWN) && directions.contains(LEFT)){ // clipping
            dx=0;
            dy=0;
            x-=savedx;
            y-=savedy;
         }
         else if(directions.contains(LEFT) || directions.contains(UP)){
            dx=0;
            x-=savedx;
         }
         else{ // down or right
            dy=0;
            y-=savedy;
         }
      }
      // top right
      if(collision(level[row-1][col+1])){
         if(directions.contains(RIGHT) && directions.contains(UP)){ // clipping
            dx=0;
            dy=0;
            x-=savedx;
            y-=savedy;
         }
         else if(directions.contains(RIGHT) || directions.contains(DOWN)){
            dx=0;
            x-=savedx;
         }
         else{ // left or up
            dy=0;
            y-=savedy;
         }
      }
      // top left
      if(collision(level[row-1][col-1])){
         if(directions.contains(LEFT) && directions.contains(UP)){ // clipping
            dx=0;
            dy=0;
            x-=savedx;
            y-=savedy;
         }
         else if(directions.contains(LEFT) || directions.contains(DOWN)){
            dx=0;
            x-=savedx;
         }
         else{ // right or up
            dy=0;
            y-=savedy;
         }
      }
   
      // conveyer
      double conSpeed = 0.5;
      double revSpeed = 0.3;
      if(level[row][col].getName().startsWith("co")){
         onCon = true;
         if(level[row][col].getName().endsWith("l")){ // left
            if(directions.contains(RIGHT) && press){
               dx*=revSpeed;
            }
            else{
               dx-=conSpeed;
            }
         }
         if(level[row][col].getName().endsWith("r")){ // right
            if(directions.contains(LEFT) && press){
               dx*=revSpeed;
            }
            else{
               dx+=conSpeed;
            }
         }
         if(level[row][col].getName().endsWith("u")){ // up
            if(directions.contains(DOWN) && press){
               dy*=revSpeed;
            }
            else{
               dy-=conSpeed;
            }
         }
         if(level[row][col].getName().endsWith("d")){ // down
            if(directions.contains(UP) && press){
               dy*=revSpeed;
            }
            else{
               dy+=conSpeed;
            }
         }
      }
      else{
         onCon = false;
      }
      
      // colliding directly above or below
      if(collision(level[row+1][col]) || collision(level[row-1][col]) ){ // player is inside tile
         y-=savedy; // move out of tile
         if(collision(level[row+1][col]) || collision(level[row-1][col])){ // check when was this a bad decision (am i still colliding?)
            y+=savedy; // if still colliding, move back to original position
         }
         else{
            dy=0;
         }
      }
      // colliding directly left or right
      if(collision(level[row][col+1]) || collision(level[row][col-1])){ // player is inside tile
         x-=savedx; // move out
         if(collision(level[row][col+1]) || collision(level[row][col-1])){
            x+=savedx;
         }
         else{
            dx=0;
         }
      }  
      // bottom right
      if(collision(level[row+1][col+1])){
         // am i moving upright or downleft?
         //lowest priority > UP, RIGHT, DOWN, LEFT > highest priority
         if(directions.contains(RIGHT) && directions.contains(DOWN)){ // trying to clip in
            dx=0;
            dy=0;
            x-=savedx;
            y-=savedy;
         }
         else if(directions.contains(RIGHT) || directions.contains(UP)){
            dx=0;
            x-=savedx;
         }
         else{ // down or left
            dy=0;
            y-=savedy;
         }
      }
      // bottom left
      if(collision(level[row+1][col-1])){
         if(directions.contains(DOWN) && directions.contains(LEFT)){ // clipping
            dx=0;
            dy=0;
            x-=savedx;
            y-=savedy;
         }
         else if(directions.contains(LEFT) || directions.contains(UP)){
            dx=0;
            x-=savedx;
         }
         else{ // down or right
            dy=0;
            y-=savedy;
         }
      }
      // top right
      if(collision(level[row-1][col+1])){
         if(directions.contains(RIGHT) && directions.contains(UP)){ // clipping
            dx=0;
            dy=0;
            x-=savedx;
            y-=savedy;
         }
         else if(directions.contains(RIGHT) || directions.contains(DOWN)){
            dx=0;
            x-=savedx;
         }
         else{ // left or up
            dy=0;
            y-=savedy;
         }
      }
      // top left
      if(collision(level[row-1][col-1])){
         if(directions.contains(LEFT) && directions.contains(UP)){ // clipping
            dx=0;
            dy=0;
            x-=savedx;
            y-=savedy;
         }
         else if(directions.contains(LEFT) || directions.contains(DOWN)){
            dx=0;
            x-=savedx;
         }
         else{ // right or up
            dy=0;
            y-=savedy;
         }
      }
      
      // reduce change in movement by velocity (slows the speed)
      dx*=velocity;
      dy*=velocity;
      
   }





}
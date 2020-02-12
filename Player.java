public class Player{
   // instance vars
   private double x,y,dx=0,dy=0;
   private Holdable holding;
   private int direction;
   private final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
   
   private final double speed = 0.4;
   private final double velocity = 0.8;
   
   public Player(){ // no starting position
      x=0;
      y=0;
      direction = UP;
   }
   public Player(double x, double y){ // starting position
      this.x=x;
      this.y=0; // lol
      direction = UP;
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
   
   //moving
   public void input(int dir){
      direction = dir;
      if(dir==UP){ // up
         dy-=speed;
      }
      if(dir==RIGHT){ //right
         dx+=speed;
      }
      if(dir==DOWN){ //down
         dy+=speed;
      }
      if(dir==LEFT){ //left
         dx-=speed;
      }   
   }
   
   public void move(){
      x+=dx;
      dx*=velocity;
      y+=dy;
      dy*=velocity;
   }





}
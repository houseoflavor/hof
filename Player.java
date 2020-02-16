public class Player{
   // instance vars
   private double x,y,dx=0,dy=0;
   private Holdable holding;
   private int direction;
   private final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4, NONE = 0;
   
   
   // CONTROL PLAYER SPEED HERE ---
   private final double speed = 0.4;    // 0 < speed
   private final double velocity = 0.8; // 0 < velo < 1
   private final double boostAmt = 3;   // 1 < boostAmt
   // -----------------------------
   
   // maxSpeed is useful for calculating when players can boost (if <= max speed)
   private final double maxSpeed = ((speed*velocity)/(1-velocity));
   
   public Player(){ // no starting position
      x=0;
      y=0;
      direction = NONE;
   }
   public Player(double x, double y){ // starting position
      this.x=x;
      this.y=0; // lol
      direction = NONE;
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
   
   
   // calculate max speed: https://www.desmos.com/calculator/vcbpsjishq
   //moving
   public void input(int dir, boolean boost){
      direction = dir;
      if(dir==UP){ // up
         if(boost && Math.abs(dy)<=maxSpeed+0.2){
            dy-=maxSpeed*boostAmt;
         }
         else{
            dy-=speed;
         }
      }
      if(dir==RIGHT){ //right
         if(boost && Math.abs(dx)<=maxSpeed+0.2){
            dx+=maxSpeed*boostAmt;
         }
         else{
            dx+=speed;
         }
      }
      if(dir==DOWN){ //down
         if(boost && Math.abs(dy)<=maxSpeed+0.2){
            dy+=maxSpeed*boostAmt;
         }
         else{
            dy+=speed;
         }
      }
      if(dir==LEFT){ //left
         if(boost && Math.abs(dx)<=maxSpeed+0.2){
            dx-=maxSpeed*boostAmt;
         }
         else{
            dx-=speed;
         }
      }
   }
   
   
   public void move(){
      x+=dx;
      y+=dy;
      
      //collision detection 
      
      // game screen collision
      if(x<50){ // left bound
         x=50;
      }
      if(x>1000){ // right bound
         x=1000;
      }
      if(y<50){ // top bound
         y=50;
      }
      if(y>500){ // bottom bound
         y=500;
      }
      
      // tile collision (oh no)
      
      
      dx*=velocity;
      dy*=velocity;
      
   }





}
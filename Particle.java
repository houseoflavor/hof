import javax.swing.*;
public class Particle{
   private int frame;
   private int rand;
   private int x;
   private int y;
   private String name;
   public Particle(int x, int y, String name){
      this.name = name;
      this.x=x;
      this.y=y;
      frame = -1;
      if(name.equals("knife")){
         rand = (int)(Math.random()*4);
      }
   }
   // advance animation frame by 1, get the ImageIcon frame apprioriately
   public ImageIcon getPicture(){
      frame++;
      if(name.equals("knife")){
         return new ImageIcon("images/items/chop/"+rand+"/chop"+(frame/2)+".png");
      }
      else if(name.equals("boost")){
         return new ImageIcon("images/items/boost/boost"+(frame/3)+".png");
      }
      return null;
   }
   // return x position of particle
   public int getX(){
      return x;
   }
   // return y position of particle
   public int getY(){
      return y;
   }
   
   // return frame number
   public int getFrame(){
      if(name.equals("boost")){
         return frame/3;
      }
      return frame/2;
   }
}
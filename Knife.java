import javax.swing.*;
public class Knife{ // why the f do i need a knife object i dont know this sucks
   private int frame;
   private int rand;
   private int x;
   private int y;
   public Knife(int x, int y){
      this.x=x;
      this.y=y;
      frame = -1;
      rand = (int)(Math.random()*4);
   }
   // advance animation frame by 1, get the ImageIcon frame apprioriately
   public ImageIcon getPicture(){
      frame++;
      return new ImageIcon("images/items/chop/"+rand+"/chop"+(frame/2)+".png");
   }
   // return x position of knife
   public int getX(){
      return x;
   }
   // return y position of knife
   public int getY(){
      return y;
   }
   
   // return frame number
   public int getFrame(){
      return frame/2;
   }
}
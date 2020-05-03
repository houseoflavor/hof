import javax.swing.*;
public class Loader{
   private int frame;
   private int mult;
   private int x, y;
   public Loader(int mult, int x, int y){ // mult = 20, total frames = 56*20 = 1120 cook time
      this.mult=mult;
      this.x=x;
      this.y=y;
   }
   public int getFrame(){
      return frame/mult;
   }
   public int getUFrame(){
      return frame;
   }
   public int getMult(){
      return mult;
   }
   public int getX(){
      return x*32+222;
   }
   public int getY(){
      return y*32+178-12;
   }
   public int getCol(){
      return x;
   }
   public int getRow(){
      return y;
   }
   public void advance(){
      if(frame<mult*56){
         frame++;
      }
   }
   public ImageIcon getPicture(){
      return new ImageIcon("images/loading/frame"+(frame/mult)+".gif");
   }
}
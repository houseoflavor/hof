import javax.swing.*;
public class Loader{
   private int frame;
   private int mult;
   private int x, y;
   public Loader(int mult, int x, int y){
      this.mult=mult;
      this.x=x;
      this.y=y;
   }
   public int getFrame(){
      return frame/mult;
   }
   public int getMult(){
      return mult;
   }
   public int getX(){
      return x;
   }
   public int getY(){
      return y;
   }
   public ImageIcon getPicture(){
      if(frame<mult*56-1){
         frame++;
      }
      return new ImageIcon("images/loading/frame"+(frame/mult)+".gif");
   }
}
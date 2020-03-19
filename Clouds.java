import javax.swing.*;
public class Clouds{
   int x=0;
   int resolution = 60;
   int speed = 16;
   ImageIcon pic = new ImageIcon("images/menus/longcloud.gif");
   public Clouds(){}
   
   public int move(){
      x-=speed;
      if(x==-1300*resolution){
         x=0;
      }
      return x/resolution; // change number to speed
   }
   
   public ImageIcon getPicture(){
      return pic;
   }
}
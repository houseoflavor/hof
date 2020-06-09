import javax.swing.*;
public class LoaderV2{ // loader for orders
   private int frame;
   public LoaderV2(){
      frame = 93;
   }
   // advance frame of the loader
   public void advance(int t){
      frame = t;
   }
   // return the correct imageicon frame of the loader
   public ImageIcon getPicture(){
      return new ImageIcon("images/game/loader2/frame_"+frame+".gif");
   }

}
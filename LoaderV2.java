import javax.swing.*;
public class LoaderV2{ // loader for orders
   private int frame;
   private int mult;
   public LoaderV2(int mult){ // 94 frames
      this.mult=mult;
      frame = mult*94-1;
   }
   public void advance(){
      if(frame>0){
         frame--;
      }
   }
   public ImageIcon getPicture(){
      return new ImageIcon("images/game/loader2/frame_"+(frame/mult)+".gif");
   }

}
// UNUSED but may use for players so don't delete!!

import javax.swing.*;
public class Clouds{
   public String[] clouds = new String[269];
   int k=0;
   public Clouds(){
      for(int i=0; i<269; i++){
         String j = String.valueOf(i);
         if(j.length()==1){
            j="00"+j;
         }
         if(j.length()==2){
            j="0"+j;
         }
         if(j.length()==3){}
         clouds[i] = "images/menus/clouds/frame_"+j+"_delay-0.26s.gif";
      }
   }
   // returns an ImageIcon of the current frame to show
   // also advances animation by 1 frame
   public ImageIcon getPicture(){
      int mult = 5; // change speed of animation here, 1 = fastest
      k++;
      if(k==(269*mult)){
         k=0;
      }
      System.out.println(clouds[k/mult]);
      return new ImageIcon(clouds[k/mult]);
   }
}
import javax.swing.*;
public class NPC{
   private int x, y, frame, mult=7;
   private int path;
   private String name;
   private int phase=0;
   private boolean fast;
   private boolean slow;
   private String dir;
   // constructor, sets up starting x and y of npc
   public NPC(int c, int p){
      name = c==0 ? "monkey" : c==1 ? "chicken" : c==2 ? "cat" : c==3 ? "mouse" : c==4 ? "pig" : "bunny";
      fast = Math.random()>0.85;
      if(!fast){
         slow = Math.random()>0.75;
      }
      if(Math.random()>0.98){
         name="batman";
         slow = false;
         fast = true;
      }
      path = p;
      if(path==1){
         x=44; y=-20;
      }
      if(path==3){
         x=1050; y=-20;
      }
      if(path==9){
         x=890; y=770;
      }
      if(path==10){
         x=205; y=770;
      }
      if(path==5 || path==17 || path==14){
         x=890; y=-20;
      }
      if(path==6 || path==15 || path==12){
         x=205; y=-20;
      }
      if(path==2 || path==13 || path==16 || path==8){
         x=-20; y=583;
      }
      if(path==4 || path==11 || path==18 || path==7){
         x=1120; y=583;
      }
   }
   // returns an imageicon of the correct frame
   public ImageIcon getPicture(){
      return new ImageIcon("images/characters/"+name+"/active/"+dir+"/frame_"+String.valueOf((int)(frame/mult))+".gif");
   }
   // return x and y coordinates
   public int getx(){
      return x;
   }
   public int gety(){
      return y;
   }
   // increase frame of character by 1, moves them
   public void advance(){
      frame++;
      if(frame==(28)){
         frame=0;
      }
      if((slow && frame%3==0) || (!fast && !slow && frame%2==0) || fast){
         if(path==1){
            if(phase==0){
               dir="down";
               y+=1;
            }
            if(phase==1){
               dir="left";
               x-=1;
            }
            if(y==583 && phase==0){
               phase=1;
            }
            if(x==-20 && phase==1){
               phase=2;
            }
         }
         if(path==2){
            if(phase==0){
               dir="right";
               x+=1;
            }
            if(phase==1){
               dir="up";
               y-=1;
            }
            if(x==205 && phase==0){
               phase=1;
            }
            if(y==-20 && phase==1){
               phase=2;
            }
         }
         if(path==3){
            if(phase==0){
               dir="down";
               y+=1;
            }
            if(phase==1){
               dir="right";
               x+=1;
            }
            if(y==583 && phase==0){
               phase=1;
            }
            if(x==1320 && phase==1){
               phase=2;
            }
         }
         if(path==4){
            if(phase==0){
               dir="left";
               x-=1;
            }
            if(phase==1){
               dir="up";
               y-=1;
            }
            if(x==1050 && phase==0){
               phase=1;
            }
            if(y==-20 && phase==1){
               phase=2;
            }
         }
         if(path==5){
            if(phase==0){
               dir="down";
               y+=1;
            }
            if(phase==1){
               dir="left";
               x-=1;
            }
            if(phase==2){
               dir="up";
               y-=1;
            }
            if(y==140 && phase==0){
               phase=1;
            }
            if(x==205 && phase==1){
               phase=2;
            }
            if(y==-20 && phase==2){
               phase=3;
            }
         }
         if(path==6){
            if(phase==0){
               dir="down";
               y+=1;
            }
            if(phase==1){
               dir="right";
               x+=1;
            }
            if(phase==2){
               dir="up";
               y-=1;
            }
            if(y==140 && phase==0){
               phase=1;
            }
            if(x==890 && phase==1){
               phase=2;
            }
            if(y==-20 && phase==2){
               phase=3;
            }
         }
         if(path==7){
            dir="right";
            x+=1;
            if(x==1120){
               phase=1;
            }
         }
         if(path==8){
            dir="left";
            x-=1;
            if(x==-20){
               phase=1;
            }
         }
         if(path==9){
            if(phase==0){
               dir="up";
               y-=1;
            }
            if(phase==1){
               dir = "left";
               x-=1;
            }
            if(phase==2){
               dir= "down";
               y+=1;
            }
            if(y==140 && phase==0){
               phase=1;
            }
            if(x==205 && phase==1){
               phase=2;
            }
            if(y==770 && phase==2){
               phase=3;
            }
         }
         if(path==10){
            if(phase==0){
               dir="up";
               y-=1;
            }
            if(phase==1){
               dir = "right";
               x+=1;
            }
            if(phase==2){
               dir= "down";
               y+=1;
            }
            if(y==140 && phase==0){
               phase=1;
            }
            if(x==890 && phase==1){
               phase=2;
            }
            if(y==770 && phase==2){
               phase=3;
            }
         }
         if(path==11){
            if(phase==0){
               dir="left";
               x-=1;
            }
            else{
               dir="up";
               y-=1;
            }
            if(x==205 && phase==0){
               phase=1;
            }
            if(y==-20 && phase==1){
               phase=2;
            }
         }
         if(path==12){
            if(phase==0){
               dir="down";
               y+=1;
            }
            else{
               dir="right";
               x+=1;
            }
            if(y==583 && phase==0){
               phase=1;
            }
            if(x==1120 && phase==1){
               phase=2;
            }
         }
         if(path==13){
            if(phase==0){
               dir="right";
               x+=1;
            }
            else{
               dir="up";
               y-=1;
            }
            if(x==890 && phase==0){
               phase=1;
            }
            if(y==-20 && phase==1){
               phase=2;
            }
         }
         if(path==14){
            if(phase==0){
               dir="down";
               y+=1;
            }
            else{
               dir="left";
               x-=1;
            }
            if(y==583 && phase==0){
               phase=1;
            }
            if(x==-20 && phase==1){
               phase=2;
            }
         }
         if(path==15){
            if(phase==0){
               dir="down";
               y+=1;
            }
            if(phase==1){
               dir="left";
               x-=1;
            }
            if(y==583 && phase==0){
               phase=1;
            }
            if(x==-20 && phase==1){
               phase=2;
            }
         }
         if(path==16){
            if(phase==0){
               dir="right";
               x+=1;
            }
            if(phase==1){
               dir="up";
               y-=1;
            }
            if(x==205 && phase==0){
               phase=1;
            }
            if(y==-20 && phase==1){
               phase=2;
            }
         }
         if(path==17){
            if(phase==0){
               dir="down";
               y+=1;
            }
            if(phase==1){
               dir="right";
               x+=1;
            }
            if(y==583 && phase==0){
               phase=1;
            }
            if(x==1320 && phase==1){
               phase=2;
            }
         }
         if(path==18){
            if(phase==0){
               dir="left";
               x-=1;
            }
            if(phase==1){
               dir="up";
               y-=1;
            }
            if(x==890 && phase==0){
               phase=1;
            }
            if(y==-20 && phase==1){
               phase=2;
            }
         }
      }
   }
   // returns true if the sprite is out of view
   public boolean delete(){
      if((path==1 || path==2 || path==3 || path==4 || path==11 || path==12 || path==13 || path==14 || path==15 || path==16 || path==17 || path==18) && phase==2){
         return true;
      }
      if((path==5 || path==6 || path==9 || path==10) && phase==3){
         return true;
      }
      if((path==7 || path==8) && phase==1){
         return true;
      }
      return false;
   }

}
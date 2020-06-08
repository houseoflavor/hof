public class NPC {
   private int x, y, frame, mult;
   private int path;
   private String name;
   public NPC(int c, int p){
      name = c==0 ? "monkey" : c==1 ? "chicken" : c==2 ? "cat" : c==3 ? "mouse" : c==4 ? "pig" : "bunny";
      path = p;
   }


}
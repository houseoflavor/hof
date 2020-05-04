
public class Order{
   private boolean mushroom, sausage;
   private int timeLeft;
   public Order(int x){
      timeLeft = x;
      mushroom = false;
      sausage = false;
   }
   public Order(int x, boolean m, boolean s){
      timeLeft = x;
      mushroom = m;
      sausage = s;
   }
   public void decrement(){
      timeLeft--;
   }
   
   // returns whether 'other' is a correct order
   public boolean match(Item other){
      return true;
   }

}
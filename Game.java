// deals with literally everything gamewise
import java.util.*;
import java.io.*;
import java.nio.*;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;

public class Game{

   // timer (round)
   Timer timer2 = new Timer();
   TimerTask task2 = 
      new TimerTask(){
         public void run(){
            timeLeftInRound--;
            if(timeLeftInRound==0){
               timer2.cancel();
            }
            for(int i=0; i<orders.size(); i++){
               orders.get(i).decrement();
            }
         
         }
      };
   public void start(){
      timer2.scheduleAtFixedRate(task2,0,1000); // 1s
   }
   public void countdown(){
      //timer3.scheduleAtFixedRate(task2, 0, 1000); // 3s countdown
   }
   
   private static LinkedList<Order> orders;
   private int timeLeftInRound;
   private int complete;
   
   //constructor
   public Game(){
      orders = new LinkedList<Order>();
      timeLeftInRound = 120;
      complete = 0;
      this.start();
   }
   
   public LinkedList getOrders(){
      return orders;
   }
   
   public Order getOrder(int i){
      return orders.get(i);
   }
   public int getTime(){
      return timeLeftInRound;
   }
}
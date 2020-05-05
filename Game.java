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
            
            // adding orders
            
         
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
   private int coins;
   private int level;
   
   //constructor
   public Game(int l){
      this.level=l;
      orders = new LinkedList<Order>();
      timeLeftInRound = 180;
      complete = 0;
      this.start();
      coins = 1234;
      orders.add(new Order(45, false, false));
      orders.add(new Order(45, false, false));
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
   public int getCoins(){
      return coins;
   }
   
   public void deliver(Item i){
      for(int j=0; j<orders.size(); j++){
         if(orders.get(j).match(i)){
            coins+=orders.get(j).getScore();
            orders.remove(j);
            boolean mus, sau;
            if(level>=2){
               mus = Math.random()>0.5 ? true : false;
            }
            else{
               mus = false;
            }
            //if(level
            break;
         }
      }
   }
}
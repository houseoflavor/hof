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
            nextOrder--;
            nextPlate--;
            for(int i=0; i<orders.size(); i++){
               orders.get(i).decrement();
               if(orders.get(i).timeLeft()==0){
                  coins-=30;
                  nextOrder = 10;
                  if(coins<0){
                     coins = 0;
                  }
               }
               if(orders.get(i).timeLeft()<=-3){
                  orders.remove(i);
               }
            }
            // adding orders
            if(nextOrder == 0){
               addOrder();
            }
            if((timeLeftInRound+10) % 30 == 0){ // spawn plate every x seconds
               spawnPlate = true;
            }
            if(timeLeftInRound % 30 == 0){ // new order every x sec
               addOrder();
            }
            if(nextPlate == 0){
               spawnPlate2 = true;
            }
         
         }
      };
   public void start(){
      timer2.scheduleAtFixedRate(task2,0,1000); // 1s
   }
   public void countdown(){
      //timer3.scheduleAtFixedRate(task2, 0, 1000); // 3s countdown
   }
   public void addOrder(){
      boolean mus, sau;
      if(level>=2){
         mus = Math.random()>0.5 ? true : false;
      }
      else{
         mus = false;
      }
      if(level>=3){ // change to whatever level first has sausage
         sau = Math.random()>0.5 ? true : false;
      }
      else{
         sau = false;
      }
      if(orders.size()<6){
         orders.add(new Order(60, mus, sau, true));
      }
   }
   
   private static LinkedList<Order> orders;
   private int timeLeftInRound;
   private int complete;
   private int coins;
   private int level;
   private boolean spawnPlate;
   private boolean spawnPlate2;
   private int nextOrder;
   private int nextPlate;
   
   //constructor
   public Game(int l){
      nextOrder = -1;
      nextPlate = -1;
      spawnPlate = true;
      this.level=l;
      orders = new LinkedList<Order>();
      timeLeftInRound = 180;
      complete = 0;
      this.start();
      coins = 0;
      orders.add(new Order(60, false, false, false));
      orders.add(new Order(60, false, false, false));
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
   
   public void spawnPlate(){
      if(spawnPlate){
         spawnPlate = false;
      }
      else if(spawnPlate2){
         spawnPlate2 = false;
      }
   }
   
   public boolean shouldSpawn(){
      return spawnPlate || spawnPlate2;
   }
   
   public boolean deliver(Item i){
      for(int j=0; j<orders.size(); j++){
         if(orders.get(j).match(i)){
            coins+=orders.get(j).getScore();
            orders.remove(j);
            addOrder();
            return true;
         }
      }
      nextPlate = 6;
      return false;
   }
}
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
            if(!pause){
               timeLeftInRound--;
               if(timeLeftInRound==-4){
                  timer2.cancel();
               }
               if(timeLeftInRound<=180 && timeLeftInRound>0){
                  nextOrder--;
                  nextPlate--;
                  for(int i=0; i<orders.size(); i++){
                     orders.get(i).decrement();
                     if(orders.get(i).timeLeft()==0){
                        coins-=30;
                        numFail++;
                        nextOrder = 4;
                        if(coins<0){
                           coins = 0;
                        }
                     }
                     if(orders.get(i).timeLeft()<=-3){
                        orders.remove(i);
                     }
                  }
                  if(timeLeftInRound==170){
                     spawnPlate = true;
                  }
                  if(timeLeftInRound==160){
                     spawnPlate = true;
                  }
                  for(int i=0; i<rem.size(); i++){
                     if(remNums.get(i)<=0){
                        System.out.println("removing" + rem.get(i));
                        orders.remove((int)(rem.get(i).intValue()));
                        rem.remove(i);
                        remNums.remove(i);
                        break;
                     }
                     else{
                        System.out.println("minus one" + remNums.get(i));
                        remNums.set(i, remNums.get(i)-1);
                     }
                  }
               // adding orders
                  if(nextOrder == 0){
                     addOrder();
                  }
                  if(timeLeftInRound % 15 == 0 && timeLeftInRound < 120){ // new order every x sec (and wait initial 1 minute)
                     addOrder();
                  }
                  if(nextPlate == 0){
                     spawnPlate = true;
                  }
               }
            }
         
         }
      };
      
   public void pause(){ 
      pause = true;
   }
   public void resume(){
      pause = false;
   }
   public boolean isPaused(){
      return pause;
   }
   public void start(){
      timer2.scheduleAtFixedRate(task2,0,1000); // 1s
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
      if(orders.size()<5){
         orders.add(new Order(60, mus, sau, true));
      }
   }
   
   private static LinkedList<Order> orders;
   private int timeLeftInRound;
   private int complete;
   private int coins;
   private int level;
   private boolean spawnPlate;
   private int nextOrder;
   private int nextPlate;
   private boolean pause = false;
   private ArrayList<Integer> rem = new ArrayList<Integer>();
   private ArrayList<Integer> remNums = new ArrayList<Integer>();
   
   //info
   private int numDeliv=3, numFail=2;
   
   // tip = coins - (numDeliv*40)
   
   //constructor
   public Game(int l){
      nextOrder = -1;
      nextPlate = -1;
      spawnPlate = true;
      this.level=l;
      orders = new LinkedList<Order>();
      timeLeftInRound = 4; // 185
      complete = 0;
      this.start();
      coins = 223;
      orders.add(new Order(60, false, false, false));
      orders.add(new Order(60, false, false, false));
   }
   
   public int numDel(){
      return numDeliv;
   }
   public int numFail(){
      return numFail;
   }
   public int getCoins(){
      return coins;
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
   
   public void spawnPlate(){
      spawnPlate = false;
   }
   public int getLevel(){
      return level;
   }
   public boolean ready(){
      return timeLeftInRound <= 180;
   }
   
   public boolean shouldSpawn(){
      return spawnPlate;
   }
   
   public boolean deliver(Item i){
      for(int j=0; j<orders.size(); j++){
         if(orders.get(j).match(i)){
            coins+=orders.get(j).getScore();
            rem.add(j);
            numDeliv++;
            remNums.add(3);
            nextPlate = 15; // 15 seconds after delivery for next plate
            addOrder();
            return true;
         }
      }
      return false;
   }
   
  
}
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
               if(timeLeftInRound<=200 && timeLeftInRound>0){
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
                  if(timeLeftInRound==190){
                     spawnPlate = true;
                  }
                  if(timeLeftInRound==170){
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
                  if(timeLeftInRound % 30 == 0 && timeLeftInRound < 140){ // new order every x sec (and wait initial 1 minute)
                     addOrder();
                  }
                  if(nextPlate == 0){
                     spawnPlate = true;
                  }
               }
               
               if(Math.random()>0.6){ // chance of spawning a npc
                  npcs.add(new NPC((int)(Math.random()*6), (int)(Math.random()*18)+1)); // sort based on y value
                  for(int i=0; i<npcs.size(); i++){
                     for(int j = npcs.size() - 1; j > i; j--){
                        if(npcs.get(i).gety() > npcs.get(j).gety()){
                           NPC tmp = npcs.get(i);
                           npcs.set(i,npcs.get(j));
                           npcs.set(j,tmp);
                        }
                     }
                  
                  }
               }
               
            }
         
         }
      };
   //pause and unpause game
   public void pause(){ 
      pause = true;
   }
   public void resume(){
      pause = false;
   }
   // returns true if paused
   public boolean isPaused(){
      return pause;
   }
   public void start(){
      timer2.scheduleAtFixedRate(task2,0,1000); // 1s
   }
   public void cancel(){
      timer2.cancel();
   }
   // add an order to the list
   public void addOrder(){
      boolean mus, sau;
      mus=false;
      sau=false;
      if(level>=2 && level<5){
         mus = Math.random()>0.5 ? true : false;
      }
      else{
         if(level>=5){
            if(Math.random()>0.5){
               sau = Math.random()>0.4 ? true : false;
            }
            else{
               mus = Math.random()>0.4 ? true : false;
            }
         }
      }
      if(orders.size()<5){
         orders.add(new Order(90, mus, sau, true));
      }
   }
   
   private static LinkedList<Order> orders = new LinkedList<Order>();
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
   private ArrayList<NPC> npcs = new ArrayList<NPC>();
   
   //info
   private int numDeliv=0, numFail=0;
   
   // tip = coins - (numDeliv*40)
   
   //constructor
   public Game(int l){
      nextOrder = -1;
      nextPlate = -1;
      spawnPlate = true;
      this.level=l;
      orders = new LinkedList<Order>();
      timeLeftInRound = 205; // 185
      complete = 0;
      this.start();
      coins = 0;
      orders.add(new Order(90, false, false, false));
      orders.add(new Order(90, false, false, false));
   }
   public ArrayList<NPC> getList(){
      return npcs;
   }
   public void reset(){
      timer2.cancel();
      for(int i=0; i<orders.size(); i++){
         orders.remove(i);
      }
      orders = new LinkedList<Order>();
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
      return timeLeftInRound <= 200;
   }
   
   public boolean shouldSpawn(){
      return spawnPlate;
   }
   // attemps to deliver the item (returns true if a good order)
   public boolean deliver(Item i){
      for(int j=0; j<orders.size(); j++){
         if(orders.get(j).match(i)){
            coins+=orders.get(j).getScore();
            rem.add(j);
            numDeliv++;
            remNums.add(3);
            nextPlate = 2;
            addOrder();
            return true;
         }
      }
      return false;
   }
   
  
}
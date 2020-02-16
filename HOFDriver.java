import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;


public class HOFDriver{

   public static HOF window;
   
   public static void main(String[] args){
      window = new HOF();
      JFrame w = new JFrame("House of Flavor");
      w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      w.setContentPane(window);
      w.setLocation(0, 0);
      HOF graphics = window;
      w.setSize(1316,789); // the window size is slightly off, pretend 1300x750
      w.setResizable(true);
      w.setVisible(true); // show it
      
      w.addKeyListener(new listen());
   }

   public static class listen implements KeyListener{
      public void keyPressed(KeyEvent e){
         window.keyCommand(e);
      }
      public void keyReleased(KeyEvent e){
         window.keyRemove(e);
      }
      public void keyTyped(KeyEvent e){
      }
   
   
   
   
   }
}

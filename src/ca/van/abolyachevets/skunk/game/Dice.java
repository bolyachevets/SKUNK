package ca.van.abolyachevets.skunk.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

public class Dice {
	
	private int dieOne = 0;
	private int dieTwo = 0;
	
	
    private static final Dice instance = null;
    
    //private constructor to avoid client applications to use constructor
    private Dice(){
    }

    public static Dice getInstance(){
    	if (instance != null) return instance;
    	else return new Dice();
    }
	
	   /**
	    * Draw a die with upper left corner at (x,y).  The die is
	    * 35 by 35 pixels in size.  The val parameter gives the
	    * value showing on the die (that is, the number of dots).
	    */
  public static void drawDie(Graphics g, int val, int x, int y) {
	      g.setColor(Color.white);
	      g.fillRect(x, y, 35, 35);
	      g.setColor(Color.black);
	      g.drawRect(x, y, 34, 34);
	      if (val > 1)  // upper left dot
	         g.fillOval(x+3, y+3, 9, 9);
	      if (val > 3)  // upper right dot
	         g.fillOval(x+23, y+3, 9, 9);
	      if (val == 6) // middle left dot
	         g.fillOval(x+3, y+13, 9, 9);
	      if (val % 2 == 1) // middle dot (for odd-numbered val's)
	         g.fillOval(x+13, y+13, 9, 9);
	      if (val == 6) // middle right dot
	         g.fillOval(x+23, y+13, 9, 9);
	      if (val > 3)  // bottom left dot
	         g.fillOval(x+3, y+23, 9, 9);
	      if (val > 1)  // bottom right dot
	         g.fillOval(x+23, y+23, 9,9);
	   }
	   
	   
	   /**
	    * Run an animation that randomly changes the values shown on
	    * the dice 10 times, every 100 milliseconds.
	    */
	   public void roll() {
	            setDice((int)(Math.random()*6) + 1, (int)(Math.random()*6) + 1);
	   }
	   
	public void setDice(int dieOne, int dieTwo) {   
			   this.dieOne = dieOne;
			   this.dieTwo = dieTwo;   
	   }
	
	public synchronized int getDieOne() {
		return dieOne;
	}
	
	public synchronized int getDieTwo() {
		return dieTwo;
	}
	
}

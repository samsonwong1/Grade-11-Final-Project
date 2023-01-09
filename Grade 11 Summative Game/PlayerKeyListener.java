//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * [PlayerKeyListener.java]
 * class for the keyboard listener - this detects key presses and runs the corresponding code
 * @author Samson Wong, Mr. Mangat
 * @version 1.0 2021/06/15
 **/

class PlayerKeyListener implements KeyListener {
  
  //reference to items effected by keyboard press
  private Player player;

  /**
   * PlayerKeyListener constructor
   * @param p, the player of the game
   */
    PlayerKeyListener(Player p) {
      player = p;
    }

    /**
     * keyTyped 
     * checks when a key is pressed
     * @param e, KeyEvent
     */
    public void keyTyped(KeyEvent e) {  
      if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ){   
        player.moveLeft();     
      } else if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D'){
        player.moveRight();
      } else if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W'){
        player.jump();
      }else if (e.getKeyChar() == ' '){
        player.fire();
      }
    }

    
    public void keyPressed(KeyEvent e) {
    }   
 
  /**
   * keyReleased 
   * checks when a key is released
   * @param e, KeyEvent
   */
    public void keyReleased(KeyEvent e) {
      if (e.getKeyChar() == 'a') {    
        player.stopMove();
      } else if (e.getKeyChar() == 'd') {
        player.stopMove();
      } 
    } 
  } //end of keyboard listener
  
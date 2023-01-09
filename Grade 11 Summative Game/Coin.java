import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
* [Coin.java]
* The heart that the player can use to gain health
* @author Jayeong Lee
* @version 1.0 2021/06/15
**/

class Coin extends Heart {
 
  //Variables
  private BufferedImage sprite;
  private int coinCounter;
  private boolean collected; 

  //Sound Effect
  public static AudioInputStream audioStream; 
  public static Clip coinSoundEffect; 
  
  /**
   * Coin constructor
   * @param xCord, the xCord of the coin
   * @param yCord, the yCord of the coin
   * @author Samson Wong
   */    
  Coin (int xCord, int yCord) {
    super(xCord, yCord);
    setWidth(24);
    setHeight(24);
    loadSprite();
    setStartingXCord(xCord);
  }

  /**
   * setCollected
   * @param collected, boolean for if coin is collected or not 
   * @author Jayeong Lee
   * method to set coins to collected which will erase the drawing from screen
   */
  public void setCollected(boolean collected) {
    this.collected = collected;

    //Load the sound effect for when a coin is collected 
    try {
      File coinSoundFile = new File("sounds/coin.wav");
      audioStream = AudioSystem.getAudioInputStream(coinSoundFile);
      coinSoundEffect = AudioSystem.getClip();
      coinSoundEffect.open(audioStream);
    } catch (Exception ex) {
      System.out.println("Error loading coin sound");
    } 

    //Play the sound
    coinSoundEffect.start();
  }

  /**getHitLeftBoundary
    * @return if the coin was collected
    * @author Jaeyong Lee
    */
  public boolean getCollected() {
    return collected;
  }
  
  /**loadSprites
    * A method that tries to load the coin image 
    * catches Exception if failed to load
    * @author Jaeyong Lee
    */
  public void loadSprite() {
    try {
      sprite = ImageIO.read(new File("images/coin.png"));

    } catch(Exception e) { System.out.println("error loading coin sprite");}
  }

  /**
   * draw 
   * draws the coin image
   * @param g, Graphics
  * @author Jaeyong Lee
   */
   public void draw(Graphics g) {              
     g.drawImage(sprite, getXCord(), getYCord(), null);
  }   
}
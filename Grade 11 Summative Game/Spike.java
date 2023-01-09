import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

/**
* [Spike.java]
* A game obstacle that damages the player
* @author Jaeyong Lee
* @version 1.0 2021/06/15
**/

class Spike extends Heart {
  
  //variables
  private BufferedImage sprite;
  
  /**
   * Spike constructor
   * @param xCord, the xCord of the spike
   * @param yCord, the yCord of the spike
   */
  Spike (int xCord, int yCord) {
    super(xCord, yCord);
    setWidth(65);
    setHeight(25);
    loadSprite();
  }
  
  /**loadSprites
    * A method that tries to load the animated sprites
    * catches Exception if failed to load
    */
  public void loadSprite() {
    try {
      sprite = ImageIO.read(new File("images/Spikes.png"));
    } catch(Exception e) { System.out.println("error loading spike");}
  }
  
  /**
   * draw 
   * draws the spike image
   * @param g, Graphics
   */
  public void draw(Graphics g) {         
    g.drawImage(sprite, getXCord(), getYCord(), null);
  }
}
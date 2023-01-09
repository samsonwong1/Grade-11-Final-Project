import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
/**
* [Heart.java]
* The collectable heart item that the player can use to gain health
* @author Samson Wong, Jayeong Lee
**/

class Heart extends Entity {
  //Variables
  private int startingXCord; 
  private BufferedImage sprite;

  /**
   * Heart constructor
   * @param xCord, the xCord of the Heart
   * @param yCord, the yCord of the Heart
   * @author Samson Wong
   */  
  Heart(int xCord, int yCord) {
    super(xCord, yCord);
    setWidth(24);
    setHeight(24);
    loadSprite();
    setStartingXCord(xCord);
  }

  /**
   * setStartingXCord
   * @author Jayeong Lee
   * method to set initial starting positions of hearts 
   */
  public void setStartingXCord(int startingXCord) {
    this.startingXCord = startingXCord;
  }

  /**
   *getStartingXCord
   *@author Jayeong Lee
   */
  public int getStartingXCord() {
    return startingXCord;
  }
  
  /**loadSprites
    * A method that tries to load the heart image
    * catches Exception if failed to load
    * @author Samson Wong
    */
  public void loadSprite() {
    try {
      sprite = ImageIO.read(new File("images/Health.png"));

    } catch(Exception e) { System.out.println("error loading block");}
  }
  
  /**
   * draw 
   * draws the heart image
   * @param g, Graphics
   */
   public void draw(Graphics g) {
    g.drawImage(sprite, getXCord(), getYCord(), null);
  } 
}
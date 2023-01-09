import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

/**
* [Bullet.java]
* The bullets that the gun shoots
* @author Samson Wong
* @version 1.0 2021/06/15
**/
class Bullet extends Entity {
  private BufferedImage sprite;
  
  /**
   * Bullet constructor
   * @param xCord, the xCord of the bullet
   * @param yCord, the yCord of the bullet
   */
  Bullet(int xCord, int yCord){
    super(xCord,yCord);
    setWidth(29);
    setHeight(9);
    loadSprite();
  }
  
  /**
   * update
   * method that updates the bullet
   */
  public void update(){
    setXCord(getXCord() + 10);
  }
  
  /**loadSprites
    * A method that tries to load the bullet sprite
    * catches Exception if failed to load
    */
  private void loadSprite() {
    try {
      sprite = ImageIO.read(new File("images/Bullet.png"));
    } catch(Exception e) { System.out.println("error loading bullet");};
  }
  
  /**
   * draw 
   * draws the bullet image
   * @param g, Graphics
   */ 
  public void draw(Graphics g) {            
    g.drawImage(sprite, getXCord(), getYCord(), null);
  }
}

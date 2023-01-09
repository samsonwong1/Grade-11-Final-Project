import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

/**
* [Block.java]
* The platforms that the player jumps on
* @author Samson Wong
* @version 1.0 2021/06/15
**/

class Block extends Heart {

  //Variables
  private BufferedImage sprite;
  private int numberDrawn;

  /**
   * Block constructor
   * @param xCord, the xCord of the block
   * @param yCord, the yCord of the block
   * @param numberDrawn, how many blocks the obstalce should be drawn
   */ 
  Block(int xCord, int yCord, int numberDrawn) {
    super(xCord, yCord);
    this.numberDrawn=numberDrawn;
    setWidth(56*getNumberDrawn());
    setHeight(38);
    loadSprite();
  }

  public int getNumberDrawn() {
    return numberDrawn;
  }
    
  /**loadSprites
    * A method that tries to load the animated sprites
    * catches Exception if failed to load
    */
  public void loadSprite() {
    try {
      sprite = ImageIO.read(new File("images/Brick_01.png"));
    } catch(Exception e) { System.out.println("error loading block");}
  }
  
  /**
   * draw 
   * draws the block image
   * @param g, Graphics
   */
   public void draw(Graphics g) {         
    for (int i=0; i < getNumberDrawn(); i++){
      g.drawImage(sprite, getXCord()+(i*56), getYCord(), null);
    } 
  } 
}
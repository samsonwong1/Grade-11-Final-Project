import java.awt.Graphics;
import java.awt.Rectangle;

/**
* [Entity.java]
* abstract class for all objects in the game
* @author Samson Wong
* @version 1.0 2021/06/15
**/

abstract class Entity {
  //variables
  private int xCord, yCord;
  private int width,height;
  
   /**
   * Entity constructor
   * @param xCord, the xCord of the entity
   * @param yCord, the yCord of the entity
   */
  Entity (int x, int y) {
    this.xCord = x;
    this.yCord=y;
  }
    
  /**getBounds
    * a method that draws a rectangle around the box for collision
    * @return Rectangle, the rectangle box around the sprite 
    */
  public Rectangle getBounds() {
    return new Rectangle(getXCord(), getYCord(), getWidth(), getHeight());
  }
  
  /**setXCord
    * a method sets the xCord
    * @param xCord, an int that is the xCord
    */
  public void setXCord(int xCord) {
    this.xCord = xCord;
  }

  /**setYCord
    * a method sets the yCord
    * @param yCord, an int that is the yCord
    */
  public void setYCord(int yCord) {
    //yCord can't be greater than 510 which is the floor of the game
    if (yCord >=510){
      this.yCord = 510;
    }else{
      this.yCord = yCord;
    }
  }

  /**getXCord
    * a method gets the xCord
    * @return xCord, an int that is the xCord
    */
  public int getXCord() {
    return xCord;
  }

  /**getYCord
    * a method gets the yCord
    * @return yCord, an int that is the yCord
    */
  public int getYCord() {
    return yCord;
  }

  /**getWidth
    * a method gets the width
    * @return width, an int that is the width
    */
  public int getWidth(){
    return width;
  }

  /**setWidth
    * a method sets the width
    * @param width, an int that is the width
    */
  public void setWidth(int width){
    this.width=width;
  }

  /**setHeight
    * a method sets the height
    * @param height, an int that is the height
    */
  public void setHeight(int height){
    this.height=height;
  }

  /**getHeight
    * a method gets the height
    * @return height, an int that is the height
    */
  public int getHeight(){
    return height;
  }
}



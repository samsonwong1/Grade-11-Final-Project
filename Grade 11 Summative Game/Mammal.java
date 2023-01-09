/**
* [Mammal.java]
* abstract Mammal class from entitiy that has the player and monster extending it
* @author Samson Wong
* @version 1.0 2021/06/15
**/

abstract class Mammal extends Entity {
  
  //variables
  private double health;
  private int xDirection, yDirection;

  /**
   * Mammal constructor
   * @param xCord, the xCord of the Mammal
   * @param yCord, the yCord of the Mammal
   */
  Mammal (int xCord, int yCord) {
    super(xCord,yCord);
    
    //set health, width and height of all mammals
    health = 100;
    setWidth(60);
    setHeight(85);
 
  }
  
  /**update
    * a method that updates mammal
    */
  abstract public void update(); 
  
  /**setHealth
    * a method that sets health of mammals
    * @param health, a double that takes in health and adds or subtracts it to current health
    */
  public void setHealth(double health){
    this.health += health;
    //make sure the health doesn't go above 100 and 0
    if (this.health > 100){
      this.health = 100;
    }else if (this.health < 0){
     this.health = 0;
    }
  }
  /**getHealth
    * a method returns health
    * @return health, a double the reaturns the health
    */
  public double getHealth() {
    return this.health;
  }
  /**getXDirection
    * a method returns xDirection
    * @return xDirection, a int the reaturns the x direction
    */
  public int getXDirection() {
    return xDirection;
  }
  
  /**getYDirection
    * a method returns yDirection
    * @return yDirection, a int the reaturns the y direction
    */
  public int getYDirection() {
    return yDirection;
  }
  
    /**setXDirection
    * a method sets xDirection
    * @param xDirection, a int that sets the x direction
    */
  public void setXDirection(int xDirection) {
    this.xDirection = xDirection;
  }

  
  /**setYDirection
    * a method sets yDirection
    * @param yDirection, a int that sets the y direction
    */
  public void setYDirection(int yDirection) {
    this.yDirection = yDirection;
  }
}


   
/**
* [Moveable.java]
* Interface for all objects that are moveable
* @author Samson Wong
* @version 1.0 2021/06/15
**/

public interface Moveable { 
  /**
   * moveLeft
   *moves an object left
   */
  public void moveLeft();
  /**
   * moveRight
   *moves an object right
   */
  public void moveRight();
  /**
   * stopMove
   *stops an object
   */
  public void stopMove();
}
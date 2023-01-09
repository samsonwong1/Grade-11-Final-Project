import java.util.ArrayList;
import java.awt.Graphics;

/**
* [Gun.java]
* the weapon that the player has that shoots bullets and stores the array list of bullets
* @author Samson Wong
* @version 1.0 2021/06/15
**/

public class Gun{
  //varibles
  private  ArrayList<Bullet> bullets = new ArrayList<Bullet>();//array list of bullets inside gun
  Bullet tempBullet;
  
  /**
   * Health constructor
   */ 
  Gun() {
  }
    
  /**update
    * a method that updates the bullets inside the gun
    */
  public void update() {
    //loops through bullets 
    for (int i = 0; i < bullets.size(); i++) {
      tempBullet = bullets.get(i);
      //remove bullet when the bullet travles a ceratin distance from player
      if (tempBullet.getXCord() > 1200) {
        removeBullet(tempBullet);
      }
      //update bullet
      tempBullet.update();
    }
  }
  
  /**playerMonstercollision
    * a method that checks bullet and monster collision
    * @param monster, the obejct monster that the bullet interacts with
    */
  public void bulletMonstercollision(Monster monster) {
    //loop through array
    for (int i = 0; i < bullets.size(); i++){
      tempBullet = bullets.get(i);
      //remove bullet when it hits a monster
      if (tempBullet.getBounds().intersects(monster.getBounds())) {
        removeBullet(tempBullet);
        //remove health from monster
        monster.setHealth(-10);
      }
    }
  }
  /**bulletBlockcollision
    * a method that checks bullet and block collision
    * @param block, the obejct block that the bullet interacts with
    */
  public void bulletBlockcollision(Block block) {
    for (int i = 0; i < bullets.size(); i++){
      tempBullet = bullets.get(i);
      //remove bullet when it hits a block
      if (tempBullet.getBounds().intersects(block.getBounds())) {
        removeBullet(tempBullet);
      }
    }
  }

  /**
   * draw 
   * draws the bulets in the gun image
   * @param g, Graphics
   */
  public void draw(Graphics g) {
    for (int i = 0; i < bullets.size(); i++) {
      //loops through bullets array and draws
      tempBullet = bullets.get(i);
      tempBullet.draw(g);
    }
  }
  /**
   * addBullet 
   *adds bullets to the gun
   * @param bullet, an object bullet that the gun has
   */
  public void addBullet(Bullet bullet){
    bullets.add(bullet);
  }
  /**
   * removeBullet 
   *remove bullets to the gun
   * @param bullet, an object bullet that the gun has
   */
  public void removeBullet(Bullet bullet){
    bullets.remove(bullet);
  }
}

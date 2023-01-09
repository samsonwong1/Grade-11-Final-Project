import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Rectangle;
import java.awt.Graphics;

/**
 * [Player.java]
 * the player that the person controlls through w,a,d,space
 * @author Samson Wong, Jayeong Lee
 * @version 1.0 2021/06/15
 **/

class Player extends Mammal implements Moveable { 
  //variables (Samson Wong)
  private Gun gun;
  private BufferedImage[] sprites;
  private boolean jumping;
  private boolean walking;
  private boolean falling;
  private boolean allowLeft,allowRight;
  private boolean hitLeftBoundary;
  private int leftXCordBoundary;
  private boolean collided;
  private int xDistance;
  private int coinCounter; 
  private int jumpCounter;
  private int slowAnimationSpeed;
  
  // Variables for Scrolling (Jayeong Lee)
  private int xBackground; 
  private int xOffset;
  private int walkingSprite;
  
  /**
   * Player constructor
   * @param xCord, the xCord of the player
   * @param yCord, the yCord of the player
   * @author Samson Wong
   */
  Player(int xCord, int yCord) { 
    super(xCord,yCord);
    //create a gun for the player that contains bullets
    gun = new Gun();
    //create an array for the images of the player
    sprites = new BufferedImage[19];
   
    //initliaze variables
    slowAnimationSpeed = 0;
    jumpCounter = 0;
    walkingSprite = 1;
    collided=false;
    jumping = false;
    walking = false;
    falling = false;
    allowLeft = true;
    allowRight = true;
    hitLeftBoundary = false;
    leftXCordBoundary = 0;
    xDistance = 0; 
    
    xBackground = 1320;
    xOffset = 0;
    
    //load sprite
    loadSprite();
  }

  /**loadSprites
    * A method that tries to load the animated walkingSprites
    * catches Exception if failed to load
    * @author Samson Wong
    */
  public void loadSprite() {
    try {
      sprites[0] = ImageIO.read(new File("images/Minotaur Idle.png"));
      for (int i = 1; i < 19; i++) {
        sprites[i] = ImageIO.read(new File("images/Minotaur Walking "+i+".png"));
      }
    } catch(Exception e) { System.out.println("error loading player");}
  }
  
   /**getBounds
    * a method that draws a rectangle around the box for collision
    * @return Rectangle, the rectangle box around the sprite 
    * @author Samson Wong
    */
  public Rectangle getBounds() {
    return new Rectangle(200, getYCord(), getWidth(), getHeight());
  }

  
   /**draw
    * a method that draws the gun's bullets and the player
    * @param g, Graphics
    * @author Samson Wong
    */
  public void draw(Graphics g) { 
    //draw the gun's bullets
    gun.draw(g);
    
    //when the player is idle, there is a single sprite used
    if (!walking){
      g.drawImage(sprites[0], 145, getYCord()-15, null);
    }else{
      //draw the person walking, which contains multiple images
      g.drawImage(sprites[walkingSprite], 145, getYCord()-15, null);
    }
    
    //slows down the animation
    if (slowAnimationSpeed % 5 == 0){
      //change the image of the player walking
      walkingSprite++;
    }
    slowAnimationSpeed++;
    
    //changes the int back to 1 becuase there are only 19 images
    if (walkingSprite == 18){
      walkingSprite = 1;
    }
  }

  
  /**update
    * a method that updates the gun and the player
    * @author Samson Wong, Jayeong Lee
    */
  public void update() {
    //author Jayeong Lee
    //If player gets to 1320 along the x axis, the offset value must be reset because the background is now fully off screen
    if (getXOffset() == 1320) {
      setXOffset(0);

      //If entire backgrounds are off the screen now, reset X cord of background back to zero
      if (getXBackground() % 1320 == 0 && getXBackground() > 1320) {
        setXBackground(1320);
      }
    }
    
    //author Samson Wong
    //update the gun
    gun.update();
    //update the boundry, the furthest left the player can go
    leftXCordBoundaryChecker();
       
   //player jumping
    if (jumping) {
      //makes the player go up by decreasing the y cord, there are three diffrent y direction values
      //to make the jumping up look realistic as you slow down when you reach the peak of your jump
      if (jumpCounter < 25) {
        setYDirection(-5);
      } else if (jumpCounter < 45) {
        setYDirection(-3);
      } else if (jumpCounter < 55) {
        setYDirection(-1);
      } else if (jumpCounter == 55) {
        //sets jumpcounter back to 0
        jumpCounter = 0;
        //reached the peak of the jump
        jumping = false;
        falling = true;
      }

      //sets y coordinate
      setYCord(getYCord() + getYDirection());
      jumpCounter++;
    } else {
      //when the person is on the ground
      if (getYCord() == 510) {
        //stop the player falling down 
        setYDirection(0);
        //sets y coordinate
        setYCord(getYCord() + getYDirection());
        //set jump and fall to false becasue player isn't doing either anymore
        jumping = false;
        falling = false;
      } else if (falling) {
        //make character go down/fall
        setYDirection(3);
        //sets y coordinate
        setYCord(getYCord() + getYDirection());
      }
    }
  }
  
  /**allowMoveLeft
    * a method that sets whether the player can go left or not
    * @param allowLeft, boolean that allows the player to go left
    * @author Samson Wong
    */
  public void allowMoveLeft(boolean allowLeft){
    this.allowLeft = allowLeft;
  }
  
  /**allowMoveRight
    * a method that sets whether the player can go right or not
    * @param allowRight, boolean that allows the player to go right
    * @author Samson Wong
    */
  public void allowMoveRight(boolean allowRight){
    this.allowRight = allowRight;
  }
    
  /**leftXCordBoundaryChecker
    * a method that controls how much left the player can go
    * @author Samson Wong
    */
  public void leftXCordBoundaryChecker(){
    
    //the leftXCordBoundary is only set when the player new xCord is greater than the last
    if (getXCord() > leftXCordBoundary){
      leftXCordBoundary = getXCord();
    }
    
    //checks if the player can go left or not
    if ((leftXCordBoundary-200) > getXCord()){
      allowMoveLeft(false);
      hitLeftBoundary = true;
    }else{
      allowMoveLeft(true);
    }
  }
  /**setleftXCordBoundary
    * @param leftXCordBoundary, an integer that sets the left xCord Boundary
    * @author Samson Wong
    */
  public void setleftXCordBoundary(int leftXCordBoundary){
    this.leftXCordBoundary = leftXCordBoundary;
  }
  
  /**getHitLeftBoundary
    * @return hitLeftBoundary, a boolean that determines if the player has hit that left boundary
    * @author Samson Wong
    */
  public boolean getHitLeftBoundary(){
    return hitLeftBoundary;
  }
  
   /**jump
    * a method that allows the player to jump
    * @author Samson Wong
    */
  public void jump() {

    //only jumps when both the player is not jumping and falling
    if (!jumping && !falling) {
      jumping = true;
    }
  }
  
  /**onBlock
    * a method that indiactes the player is on a block
    * @author Samson Wong
    */
  public void onBlock() {
    //set to false so the player won't fall through the block, and player can jump on the block
    jumping = false;
    falling = false;
  }
  
  /**offBlock
    * a method that indiactes the player is off a block
    * @author Samson Wong
    */
  public void offBlock() {
    if (getYCord() != 510){
      falling = true;
    }
  }
  
  /**moveLeft
  * a method that moves the player left
  * @author Samson Wong, Jayeong Lee
  */
  public void moveLeft() {
    //author Samson Wong
    //there is a boolean allowLeft so that it can be controlled when the player goes left
    if (allowLeft){
      //set xCord of the player
      setXCord(getXCord() - 20);
      
      
      //author Jayeong Lee
      //player x cord tracking for background and other scrolling 
      setXBackground(getXBackground() - 20);
      setXOffset(getXOffset() - 20);
      
      //author Samson Wong
      //makes walking true
      walking = true;
      //setting the distance the player travel per key press, so that the monster can move relative to the player
      setXDistance(-20);
    }
  }
  
  /**moveRight
    * a method that moves the player right
    * @author Samson Wong, Jayeong Lee
    */
  public void moveRight() {
    //author Samson Wong
    //there is a boolean allowRight so that it can be controlled when the player goes right
    if (allowRight){
      //set xCord of the player
      setXCord(getXCord() + 20);
      
      //author Jayeong Lee
      //player x cord tracking for background and other scrolling 
      setXBackground(getXBackground() + 20);
      setXOffset(getXOffset() + 20);
      
      //author Samson Wong
      //makes walking true
      walking = true;
      //setting the distance the player travel per key press, so that the monster can move relative to the player
      setXDistance(20);
    } 
  }
  
  /**stopMove
    * a method that makes the player stop 
    * @author Samson Wong
    */
  public void stopMove() {
    //set xCord of the player
    setXDirection(0);
    //makes walking false
    walking = false;
  }
 
  /**setXDistance
    * a method that sets xDistance 
    * @param xDistance, an integer that sets the xDistance
    * @author Samson Wong
    */
  public void setXDistance(int xDistance) {
    this.xDistance = xDistance;
  }
  
  /**getXDistance
    * a method that gets xDistance
    * @author Samson Wong
    */
  public int getXDistance() {
    return xDistance;
  }
  
  /**fire
    * a method that adds bullets to the gun
    * @author Samson Wong
    */
  public void fire() {
    //add bullets to the gun
    gun.addBullet(new Bullet (260,getYCord() + 50));
  }
 
  /**playerBlockcollision
    * a method that checks player and block collision
    * @param block, the obejct block that the player interacts with
    * @author Samson Wong
    */
  public void playerBlockcollision(Block block) {
    //checks the gun's bullets collison with block
    gun.bulletBlockcollision(block);
    
    //when player intersects with the block
    if (getBounds().intersects(block.getBounds())){
      //when player is above block
      if ((getYCord()) <= (block.getYCord()-75)) {
        //set player y cord
        setYCord(block.getYCord() - 80);
        //player is OnBlock now       
        onBlock();
      //when the player is beneath the block
      }else if(getYCord() > block.getYCord()){
        //sets player y cord
        setYCord(getYCord() + 5);
      }
      
      //when the player hits the block from the right
      if ((getYCord() > (block.getYCord() - 80)) && (200 < (block.getXCord() - 5))) {
        //player isn't allow to go right
        allowMoveRight(false);
        //when the player hits the block from the left
      }else if ((getYCord() > (block.getYCord() - 80)) && (200 > (block.getXCord() + getWidth() + 5))) {
        //player isn't allow to go left
        allowMoveLeft(false);
      }
    }
  }
 
  /**playerMonstercollision
    * a method that checks player and monster collision
    * @param monster, the obejct monster that the player interacts with
    * @author Samson Wong
    */
  public void playerMonstercollision(Monster monster) {
    //player guns bullets collison with monster
    gun.bulletMonstercollision(monster);
    
    //when player intersects with the block
    if ((monster.getBounds()).intersects(getBounds())){
      //to prevent multiple collisons 
      if (!collided){
        //when the player is on top of the monster
        if (getYCord() < monster.getYCord()) {
          //set player y cord
          setYCord(monster.getYCord()-80);
        }else if (monster.getYCord() < getYCord()){
          monster.setYCord(getYCord()-80);
        }
        //when the player hits the monster from the right
        if ((200 < monster.getXCord()) && (getYCord() == monster.getYCord())){
          //monster attacks player
          monster.stopMove();
          //player can't move right
          allowMoveRight(false);
          //decrease health
          setHealth(-0.2);
          //set true to indicate the player has intersects with the monster
          collided=true;
        }
      }
    }
    else {
      monster.walk();
      collided=false;
    }
  } 
  
  /**playerSpikecollision
    * a method that checks player and spike collision
    * @param spike, the obejct spike that the player interacts with
    * @author Samson Wong
    */
  public void playerSpikecollision(Spike spike) {
    //when player intersects with the spike
    if ((getBounds()).intersects(spike.getBounds())){
      //decrease health
      setHealth(-0.5);
    }
  }
  
  /**playerHeartcollision
    * a method that checks player and heart collision
    * @param heart, the obejct heart that the player interacts with
    * @author Samson Wong
    */
  public void playerHeartcollision(Heart heart) {
    //when player intersects with the heart
    if ((getBounds()).intersects(heart.getBounds())){
      //increase health
      setHealth(20);
    }
  }

    /**playerCoincollision
    * a method that checks player and coin collision
    * @param coin, the obejct coin that the player interacts with
    * @author Jayeong Lee
    */
  public void playerCoincollision(Coin coin) {

    if ((getBounds()).intersects(coin.getBounds())) {
      setCoinCounter(1);
      coin.setCollected(true);
    }
  }
  
  
  /**getXBackground
    * a method that returns how much the background itself moves from its initial x cord starting point  
    * @author Jayeong Lee
    */
  public int getXBackground() {
    return xBackground;
  }
  
  /**getXOffset
    * a method that returns how much the player has moved relative to background 
    * @author Jayeong Lee
    */
  public int getXOffset() {
    return xOffset;
  }
  
  /**setXBackground
    * a method that is used to set the XBackground value back to its original X cord once an entire background image has passed
    * @author Jayeong Lee
    */
  public void setXBackground(int xBackground) {
    this.xBackground = xBackground;
  }
  
  /**setXOffset
    * a method that is used to reset how much the player has moved relative to background 
    * @author Jayeong Lee
    */
  public void setXOffset(int xOffset) {
    this.xOffset = xOffset;
  }

  /**setCoinCounter
    * a method that is used to adjust and set the coin counter on the screen
    * @author Jayeong Lee
    */
  public void setCoinCounter(int coinCounter){
    this.coinCounter += coinCounter;
  }
  
  /**getCoinCounter
    * a method that will return the coin counter on the screen
    * @author Jayeong Lee
    */
  public int getCoinCounter() {
    return this.coinCounter;
  }
}
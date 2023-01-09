import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

/**
* [Monster.java]
* the monster that attacks the player
* @author Samson Wong
* @version 1.0 2021/06/15
**/

class Monster extends Mammal { 
  
  //Variables
  private BufferedImage[] sprites;
  private boolean walking;
  private boolean stop;
  private boolean attack;
  private boolean onBlock;
  private boolean stuck;
  private int slowAnimationSpeed;
  private int jumpCounter;
  private int attackAnimation;
  private int walkingSprite;
   
  /**
   * Monster constructor
   * @param xCord, the xCord of the monster
   * @param yCord, the yCord of the monster
   */
  Monster(int xCord, int yCord) { 
    super(xCord,yCord);
    
    //initliaze variables
    attackAnimation=19;
    walkingSprite=1;
    jumpCounter=0;
    sprites = new BufferedImage[31];
    walking = false;
    onBlock = false;
    stuck = false;
    attack=false;
    //load sprite
    loadSprite();
  }
  
  /**loadSprites
    * A method that tries to load the animated walkingSprites
    * catches Exception if failed to load
    */
  public void loadSprite() {
    try {
      sprites[0] = ImageIO.read(new File("images/Monster1 Idle.png"));
      
      for (int i = 1; i < 19; i++) {
        sprites[i] = ImageIO.read(new File("images/Monster1 Walking "+i+".png"));
      }
      for (int i=19; i<31;i++){
        sprites[i] = ImageIO.read(new File("images/Monster1 Attack "+(i-18)+".png"));
      }
    } catch(Exception e) { System.out.println("error loading monster");}
  }
  
  /**draw
    * a method that draws monster
    * @param g, Graphics
    */
  public void draw(Graphics g) {
    //sprites when monster is attacking
    if (attack){
      g.drawImage(sprites[attackAnimation], getXCord()-45, getYCord()-15, null);
    }else if (walking){
      //sprites when monster is walking
      g.drawImage(sprites[walkingSprite], getXCord()-45, getYCord()-15, null);
    }else {
      //when the monster not attack or walking it is idle, there is a single sprite used
      g.drawImage(sprites[0], getXCord()-45, getYCord(), null);
    }
     //slows down the animation
    if (slowAnimationSpeed % 10 == 0){
      //change the image of the monster walking and attacking
      walkingSprite++;
      attackAnimation++;
    }
    slowAnimationSpeed++;
    //changes the int back to 1 or 19 for walking or attacking becuase there are only 31 images
    if (walkingSprite == 18){
      walkingSprite = 1;
    }else if (attackAnimation == 30){
      attackAnimation = 19;
    }
    
  }
  
  /**update
    * a method that updates monster
    */
  public void update() {
    //when the monster is stuck, it jumps
    if (stuck){
      //makes the monster go up by decreasing the y cord, there are three diffrent y direction values
      //to make the jumping up look realistic as you slow down when you reach the peak of your jump
      if(jumpCounter < 25){
        setYDirection(-5);
      } else if (jumpCounter < 50) {
        setYDirection(-3);
      } else if (jumpCounter < 60) {
        setYDirection(-1);
      } else if (jumpCounter == 60) {
        //sets jumpCounter back to 0
        jumpCounter = 0;
        stuck = false; 
      }
      //sets y coordinate
      setYCord(getYCord() + getYDirection());
      jumpCounter++;
    } else { 
       //when the person is on the ground
      if (((getYCord() ==510) || onBlock) && (!stuck)){
        //stop the player falling down 
        setYDirection(0);
        //sets y coordinate
        setYCord(getYCord() + getYDirection());
      }else{
        //make character go down/fall
        setYDirection(3);
        //sets y coordinate
        setYCord(getYCord() + getYDirection());
      }
    }
    //makes the monster move left
    if (!stop){
      //set x direction
      setXDirection(-2);
      walking = true;
    }
    //set xCord
    setXCord(getXCord() + getXDirection());
  }

  
  /**stopMove
    * a method that makes the monster stop 
    */
  public void stopMove(){
    setXDirection(0);
    stop = true;
    walking = false;
    attack = true;
  }
  
  /**walk
    * a method that makes walk  
    */
  public void walk(){
    stop = false;
    attack = false;
  }
  
  
  /**monsterBlockcollision
    * a method that checks monster and block collision
    * @param block, the obejct block that the monster interacts with
    */
  public void monsterBlockcollision(Block block) {
    //when player intersects with the block
    if (getBounds().intersects(block.getBounds())){
      //when monster is above block
      if (getYCord() <= block.getYCord() - 75) {
        //set y cord
        setYCord(block.getYCord() - 83);
        //monster is onblock
        onBlock = true;
        //when monster is below block
      } else if(getYCord() > block.getYCord()){
        setYCord(getYCord() + 5);
      }
      //when the monster hits the block from the right
      if ((getYCord() >= block.getYCord()-80) && (getXCord() <= block.getXCord())) {
        //set xcord so the monster doesn't go through block
        setXCord(getXCord() - 2);
        //when the monster hits the block from the right
      } else if ((getYCord() >= block.getYCord()-80) && (getXCord() >= block.getXCord())) {
        //set xcord so the monster doesn't go through block
        setXCord(getXCord() + 2);
        //the monster is stuck becaues it can't advance towrds the player
        stuck = true;
      }
    } else {
      onBlock = false;
    }
  } 
  
   /**monsterMonstercollision
    * a method that checks monster and monster collision
    * @param monster, the obejct monster that the player interacts with
    */
  public void monsterMonstercollision(Monster monster) { 
    //when monster interscts with monster
    if (getBounds().intersects(monster.getBounds())) {
      //makes the monster not go through each other based on xCord
      if (getXCord() < monster.getXCord()){
        monster.setXCord(monster.getXCord() + 2);
      } else if (getXCord() > monster.getXCord()){
        setXCord(getXCord() + 2);
      }
      //makes the monster not go through each other based on yCord
      if (getYCord() > monster.getYCord()) {
        monster.setYCord(monster.getYCord() + 3);
      } else if (getYCord() < monster.getYCord()){
        setYCord(getYCord() + 3);
      }  
    }   
  }
}

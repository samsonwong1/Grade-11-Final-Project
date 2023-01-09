import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
* [GameAreaPanelOOP.java]
* class for the the game area - This is where all the drawing of the screen occurs
* @author Samson Wong, Jayeong Lee, Mr. Mangat
**/

class GameAreaPanelOOP extends JPanel {
  
  //Variables 
  private static Player player;
  private Level levelOneMap;
  private Level levelTwoMap; 
  private Level levelThreeMap;
  private boolean run;
  private int level;

  //Sound effects 
  public static AudioInputStream audioStream; 
  public static Clip levelUpSoundEffect; 
  public static Clip deathSoundEffect; 
  
  //Jframe
  JFrame parentFrame;

  /**
   * GameAreaPanelOOP constructor
   * @param parent, the JFrame of the game Frame
   */
  GameAreaPanelOOP (JFrame parent) {
    parentFrame=parent;
    run = true;
    level = 1; //initialize current level 

    //create the player and levels
    player = new Player(0,510);
    levelOneMap = new Level(player,"levels/LevelOne.txt","images/levelSign.png"); 
    levelTwoMap = new Level(player,"levels/LevelTwo.txt","images/levelSign.png"); 
    levelThreeMap = new Level(player,"levels/LevelThree.txt","images/End.png"); 

    //Listeners
    PlayerKeyListener keyListener = new PlayerKeyListener(player);
    this.addKeyListener(keyListener);
    
    //JPanel Stuff
    this.setFocusable(true);
    this.requestFocusInWindow(); 
    
    //Start the game loop in a separate thread (allows simple frame rate control)
    //the alternate is to delete this and just call repaint() at the end of paintComponent()
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
    t.start();
  }    
  
  /**
   * animate
   * The main gameloop - this is where the game state is updated
   * @author Jaeyong Lee, Samson Wong, Mr. Mangat
   */
  public void animate() { 

    //Load level up sound clip
    try {
      File levelUpFile = new File("sounds/levelUp.wav");
      audioStream = AudioSystem.getAudioInputStream(levelUpFile);
      levelUpSoundEffect = AudioSystem.getClip();
      levelUpSoundEffect.open(audioStream);
    } catch (Exception ex) {
      System.out.println("Error loading level up sound");
    } 

    //Load death sound clip
    try {
      File deathFile = new File("sounds/death.wav");
      audioStream = AudioSystem.getAudioInputStream(deathFile);
      deathSoundEffect = AudioSystem.getClip();
      deathSoundEffect.open(audioStream);
    } catch (Exception ex) {
      System.out.println("Error loading death sound");
    } 
    
    while(run){
      //Update game content
      if (level == 1) {
        levelOneMap.update();
        levelOneMap.collision();
      } else if (level == 2) {
        levelTwoMap.update();
        levelTwoMap.collision();  
      } else if (level == 3) {
        levelThreeMap.update();
        levelThreeMap.collision();  
      }
      
      //Change levels 
      if ((player.getXCord() >= levelOneMap.getSignXCord()) && (level == 1)) {
        levelUpSoundEffect.start();
        level = 2;
        player.setXCord(0);
        player.setleftXCordBoundary(0);
      } else if ((player.getXCord()>=levelTwoMap.getSignXCord()) && (level==2)) {
        levelUpSoundEffect.stop();
        levelUpSoundEffect.flush();
        levelUpSoundEffect.setFramePosition(0);
        levelUpSoundEffect.start();
        level = 3;
        player.setXCord(0);
        player.setleftXCordBoundary(0);
      }
    
      //delay
      try{ Thread.sleep(10);} 
      catch (Exception exc){
        System.out.println("Thread Error");
      }
      
      //To display game over menu if player dies
      if (player.getHealth()==0){
        deathSoundEffect.start();
        parentFrame.dispose();
        new GameOverFrame();
        run = false;        
      }

      //To display "game win" panel if player reaches the end of level 3 
      if ((level==3) && (player.getXCord()>= levelThreeMap.getSignXCord()-150)) {
        parentFrame.dispose();
        new GameWinFrame();
        run = false;     
      }
      
      //repaint request
      this.repaint();
    }
  }

  /**
   * paintComponent
   * paintComponnent Runs everytime the screen gets refreshed
   * @param g, Graphics
   */
  public void paintComponent(Graphics g) {   
    super.paintComponent(g); //required
    setDoubleBuffered(true); 
   
    if (level == 1) {
      levelOneMap.draw(g, 1);
    } else if (level == 2) {
      levelTwoMap.draw(g, 2);  
    } else if (level == 3) {
      levelThreeMap.draw(g, 3);  
    }
    
    //draw the player 
    player.draw(g); 
  }   
}


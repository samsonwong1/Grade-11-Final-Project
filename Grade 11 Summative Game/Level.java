import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage; 
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.awt.Font;

/**
 * [Level.java]
 * where all object for each level are created, and File IO for level maps occur 
 * @author Jaeyong Lee, Samson Wong
 **/
public class Level {
    
  //Create Objects
  private Player player;
  private Block tempBlock; 
  private Spike tempSpike; 
  private Monster tempMonster;
  private Heart tempHeart;
  private Coin tempCoin;
  private Coin drawCoin;
  private BufferedImage background, groundTile, levelSign;
  
  //Array lists for map elements 
  private ArrayList<Coin> coins = new ArrayList<Coin>();
  private ArrayList<Block> platforms = new ArrayList<Block>();
  private ArrayList<Spike> spikeTraps = new ArrayList<Spike>();
  private ArrayList<Monster> monster = new ArrayList<Monster>();
  private ArrayList<Heart> heart = new ArrayList<Heart>(); 

  //Variables for level maps & File IO
  private char [][] levelMap;
  private String line;
  private int blockNumber;
  private int signXCord, signYCord;
  private int rows, columns;
  private int playerCollisoncounter;
  private int currentLevel;
  private String filename, signname;
  
  /**
   * Level constructor
   * @param Player, the player of the game
   * @param filename, the name of the level file
   * @param signname, the name of the sign png
   * @author Jayeong Lee
   */
  Level(Player player, String filename, String signname) {
    this.player = player; 
    this.filename = filename;
    this.signname = signname;
    drawCoin = new Coin(50,90);
    playerCollisoncounter=0;
    loadMap();
    loadMapFeatures();
    loadImages();
  }

  /**
   * loadMap
   * a method that loads the level map text files to arraylists
   * @author Jayeong Lee
   */
  public void loadMap (){
    Scanner input = new Scanner(System.in);
    File myfile = new File (filename);    
    try{
      //Create scanner associated with the file
      input = new Scanner(myfile);
      rows = input.nextInt();
      columns = input.nextInt();
      input.nextLine();
      levelMap = new char [rows][columns];
      
      //gets the map from the file
      while (input.hasNext()) {   
        for (int i = 0; i < rows; i++) {
          line = input.nextLine();
          for (int j = 0; j < columns; j++) {
            levelMap[i][j] = line.charAt(j);
          }
        }
      }
      //close the scanner
      input.close();
    } catch (FileNotFoundException e){ System.out.println("error loading Map");}
  }
  
  /**
   * loadMapFeatures
   * a method that initializes the objects from the symbols which represent the blocks, monster, hearts, coins, spikes
   * @author Jayeong Lee
   */
  public void loadMapFeatures() {
    for (int i = 0; i < levelMap.length; i++) { //rows
      for (int j = 0; j < levelMap[i].length; j++) { //columns
        if (levelMap[i][j] == 'B') {
          blockNumber = levelMap[i][j+1]-'0';
          platforms.add(new Block (j*56+500, 250+i*50, blockNumber));
        } else if (levelMap[i][j] == 'M') {
          monster.add(new Monster(j*56+500, 0));
        } else if (levelMap[i][j] == 'S') {
          spikeTraps.add(new Spike(j*56+500, 250+i*53));
        } else if (levelMap[i][j] == 'H') {
          heart.add(new Heart(j*56+500, 250+i*50));
        } else if (levelMap[i][j] == 'C') {
          coins.add(new Coin(j*56+500, 250+i*50));
        } else if (levelMap[i][j] == 'E') {
          signXCord = j*56+500;
          signYCord = 510;
        }
      }
    } 
  }
  
  /**
   * loadImages
   * a method loads the background, ground tile and level sign images
   * @author Jayeong Lee
   */
  public void loadImages() { 
    try {
      background = ImageIO.read(new File("images/background.png"));
      groundTile = ImageIO.read(new File("images/ground.png"));
      levelSign = ImageIO.read(new File(signname));
    } catch(Exception e) { 
      System.out.println("Error loading images");};
  }
  
  /**
   * draw
   * A method that draws all the images for the game
   * @param g, Graphics
   * @author Samson Wong, Jayeong Lee
   */
  public void draw(Graphics g, int currentLevel) {
    this.currentLevel = currentLevel;

    //Draw the starting backgrounds 
    g.drawImage(background, -1320-player.getXOffset(), 0, null); //Background to the left so player can move left
    
    //1320 is background image width. Variable xBackground starts at 1320 and changes with player movement. 
    //As xBackground gets larger, it will appear the background moves to the right 
    g.drawImage(background, 1320-player.getXBackground(), 0, null);
    
    //Continuously draw new backgrounds after the first one is gone 
    //xOffset tracks player movement 
    if (player.getXCord() >= 0) {
      g.drawImage(background, 1320-player.getXOffset(), 0, null);
    } 
    
    //Draw the Coin counter in top left of screen
    Integer y = new Integer(player.getCoinCounter()); // Make integer object to be converted to string later
    Font coinFont = new Font("Helvetica", Font.PLAIN, 25);
    g.setFont(coinFont); 
    g.drawString("x" + y.toString(), 88, 107);
    drawCoin.draw(g);

    //Draw display for what level they are on
    Integer level = new Integer(currentLevel); // Make integer object to be converted to string later
    Font levelFont = new Font("Helvetica", Font.PLAIN, 25);
    g.setFont(levelFont); 
    g.drawString("LEVEL: " + level.toString(), 50, 45);
    
    //draw the level sign
    g.drawImage(levelSign, signXCord-(player.getXCord()), signYCord, null);
    
    // draw the ground tiles
    for (int j = 0; j/116 < 30; j+=116) {
      g.drawImage(groundTile, -500 + j-(player).getXOffset(), 590, null);
    }
    
    // draw health bar
    g.drawRect(50, 50, 200, 30);
    g.drawRect(49, 49, 201, 30);
    g.setColor(Color.RED);
   
    // Update the health bar   
    g.fillRect(50, 50, 200 - ((100 - (int) Math.round(player.getHealth()))*2), 30);
    
    //author Samson Wong
    //drawing the platfroms, spiketraps, monsters, heart and coins, one for loop that can loop through everything and raw
    for (int i = 0; i < (Math.max(platforms.size(), Math.max(spikeTraps.size(), Math.max(monster.size(),Math.max(heart.size(),coins.size()))))); i++) {

      //draw platforms
      if (i < platforms.size()) {
        tempBlock = platforms.get(i);
        tempBlock.draw(g);
      }
      //draw spiketraps
      if (i < spikeTraps.size()) {
        tempSpike = spikeTraps.get(i);
        tempSpike.draw(g);
      }
      //draw monsters
      if (i < monster.size()) {
        tempMonster = monster.get(i);
        tempMonster.draw(g);
      }
      //draw heart
      if (i < heart.size()) {
        tempHeart = heart.get(i);
        tempHeart.draw(g); 
      }
      //draw coins
      if (i < coins.size()) {
        tempCoin = coins.get(i);
        tempCoin.draw(g);
      }
    }
  }

  
  /**
   *collision
   *method that checks collison between eveyrthing in the program
   *@author Samson Wong
   */
  public void collision(){
    //loop through  the arraylist
    for (int i = 0; i < (Math.max(platforms.size(), Math.max(spikeTraps.size(), Math.max(monster.size(),Math.max(heart.size(),coins.size()))))); i++) {
      
      //checking player collisons with the block
      if (i < platforms.size()) {
        tempBlock = platforms.get(i);
        player.playerBlockcollision(tempBlock);
        //this counts how many things the player is touching
        if (player.getBounds().intersects(tempBlock.getBounds())) {
          playerCollisoncounter++;
        }
      }
      //checking player collisons with the spike
      if (i < spikeTraps.size()){
        tempSpike = spikeTraps.get(i);
        player.playerSpikecollision(tempSpike);
      }
      //checking player collisons with the monster
      if (i < monster.size()){
        tempMonster = monster.get(i);
        player.playerMonstercollision(tempMonster);
        //this counts how many things the player is touching
        if (player.getBounds().intersects(tempMonster.getBounds())){
          playerCollisoncounter++;
        }
        //checking monster collisons with the block
        for (int j = 0; j < platforms.size(); j++) {
          tempBlock = platforms.get(j);
          tempMonster.monsterBlockcollision(tempBlock);
        }
        //checking playermonster collisons with other monster
        for (int k=i+1; k < monster.size()-i; k++) {
          tempMonster.monsterMonstercollision(monster.get(k));
        }
      }
      //checking player collisons with the heart
      if (i < heart.size()) {
        tempHeart = heart.get(i);
        player.playerHeartcollision(tempHeart);
        if (player.getBounds().intersects(tempHeart.getBounds())) {
          heart.remove(tempHeart);
        }
      }
      //checking player collisons with the coin
      if (i < coins.size()){
        tempCoin = coins.get(i);
        player.playerCoincollision(tempCoin);
      }
    } 
    //if the player has no collsions, it is off the block(offblock just makes the player fall to the most bottom level)
    if (playerCollisoncounter == 0){
      player.offBlock();
    }
    //if the player is at the bottom level and allows player to move back the same direction after it tries to go through
    //a block
    if ((playerCollisoncounter == 0) && (player.getYCord() == 510)) {
      player.allowMoveRight(true);
      if (!player.getHitLeftBoundary()){
        player.allowMoveLeft(true);
      }
    }
    //if the player is at the on a block and it just allows the player to move back the same direction after it tries
    //to go through a block
    if ((playerCollisoncounter == 1) && (player.getYCord() != 510)) {
      player.allowMoveRight(true);
      if (!player.getHitLeftBoundary()) {
        player.allowMoveLeft(true);
      }
    }    
    playerCollisoncounter = 0;
  }
  
  

  /**
   *update
   *method that updates everything in the program
   *@author Samson Wong, Jaeyong Lee
   */
  public void update() {
    //player update
    player.update();  
    
    //loop through arraylist
    for (int i = 0; i < (Math.max(platforms.size(), Math.max(spikeTraps.size(), Math.max(monster.size(),Math.max(heart.size(),coins.size()))))); i++){
      
      if (i < platforms.size()){
        tempBlock = platforms.get(i);
        //move platform based on player movement
        tempBlock.setXCord(tempBlock.getStartingXCord() - player.getXCord());
        //deletes block after it passes the player after a distance
        if (tempBlock.getXCord() <= -550){
          platforms.remove(tempBlock);
        }
      }
      if (i < spikeTraps.size()){
        tempSpike = spikeTraps.get(i);
        //move spike based on player movement
        tempSpike.setXCord(tempSpike.getStartingXCord() - player.getXCord());
        //deletes spike after it passes the player after a distance
        if (tempSpike.getXCord() <= -250){
          spikeTraps.remove(tempSpike);
        }
      }
      
      //update monster
      if (i < monster.size()){
        tempMonster = monster.get(i);
        //delete monster when player kills a monster
        if (tempMonster.getHealth() == 0){
          monster.remove(tempMonster);
          //deletes monster after it passes the player after a distance
        }else if (tempMonster.getXCord() <= -250){
          monster.remove(tempMonster);
        }//this moves the mosnter based upon the player movment
        tempMonster.setXCord(tempMonster.getXCord() - player.getXDistance());
        //update monster
        tempMonster.update();
      }
      
      
      if (i < heart.size()){
        tempHeart = heart.get(i);
        //move heart based on player movement
        tempHeart.setXCord(tempHeart.getStartingXCord() - player.getXCord());
        //deletes heart after it passes the player after a distance
        if (tempHeart.getXCord() <= -250){
          heart.remove(tempHeart);
        }
      }
      
      if (i < coins.size()){
        tempCoin = coins.get(i);
        //move heart based on player movement
        tempCoin.setXCord(tempCoin.getStartingXCord() - player.getXCord());
        //deletes coin after it passes the player after a distance
        if (tempCoin.getXCord() <= -250){
          coins.remove(tempCoin);
        }//@Jayeong Lee, when player hits a coin, and coin is counted and removed
         if (tempCoin.getCollected()){
          coins.remove(tempCoin);
        }
      }
    }
    player.setXDistance(0);
  }
  
  /**
   *getSignXCord
   *method that gets the signxCord
   *@return signXCord, an int that is the x cord of the sign(end of each level) 
   *@author Samson Wong
   */
  public int getSignXCord(){
    return this.signXCord;
  }
  
}
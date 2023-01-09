import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

/**
* [GameOverFrame.java]
* frame when the player dies
* @author Jayeong Lee, Mr. Mangat
* @version 1.0 2021/06/15
**/
class GameOverFrame extends JFrame { 

  JFrame thisFrame;
  
  //Constructor - this runs first
  GameOverFrame() { 
    super("You Died!");
    this.thisFrame = this; //lol  

    //configure the window
    this.setSize(1325, 750);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new BackgroundPanel();
    
    //Create a JButton for the centerPanel
    JButton playAgainButton = new JButton("PLAY AGAIN");
    playAgainButton.setPreferredSize(new Dimension(240, 50));
    playAgainButton.addActionListener(new ExitButtonListener(this,1));
    
    
    //Create a JButton for the centerPanel
    JButton exitButton = new JButton("EXIT");
    exitButton.setPreferredSize(new Dimension(240, 50));
    exitButton.addActionListener(new ExitButtonListener(this,2));

    
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(playAgainButton);
    mainPanel.add(exitButton);
    
    //add the main panel to the frame
    this.add(mainPanel);
    this.setVisible(true);
    this.requestFocusInWindow();
  }
  
  //This is an inner class
  /**
   * [BackgroundPanel.java]
   * Creates the Jpanel for starting Frame
   * @author Mr.Mangat, Jaeyong Lee
   * @version 1.0 2021/06/15
   */
  class BackgroundPanel extends JPanel { 
    private BufferedImage background;

    /**
     * BackgroundPanel constructor
     */
    BackgroundPanel() {
      //load image
      try {
      background = ImageIO.read(new File("images/playerDied.png"));
      
      } catch(Exception e) { System.out.println("error loading background");}
    }
    
  /**
   * paintComponent 
   * draws the background image
   * @param g, Graphics
   */
    public void paintComponent(Graphics g) {   
      super.paintComponent(g); //required
      setDoubleBuffered(true); 
      g.drawImage(background,0, 0, null);
    }
  } 
}
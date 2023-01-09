//Imports
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
* [StartingFrameOOP.java]
* start menu/frame of the final project
* @author Samson Wong, Mr. Mangat, Jaeyong Lee
* @version 1.0 2021/06/15
**/

public class StartingFrameOOP extends JFrame { 
  //variables
  JFrame thisFrame;
  
  //Constructor - this runs first
  StartingFrameOOP() { 
    super("Start Screen");
    this.thisFrame = this; //lol 

    
    //configure the window
    this.setSize(1325, 750);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new BackgroundPanel();
    
    //Create a JButton for the centerPanel
    JButton startButton = new JButton("PLAY");
    startButton.setPreferredSize(new Dimension(240, 50));
    startButton.setBackground(new Color(0, 0, 0, 0)); // button color
    startButton.addActionListener(new StartButtonListener(this,1));
    
    //Create a JButton for the centerPanel
    JButton instButton = new JButton("INSTRUCTIONS");
    instButton.setPreferredSize(new Dimension(240, 50));
    instButton.setBackground(new Color(0, 0, 0, 0));
    instButton.addActionListener(new StartButtonListener(this,2));
 
    //Create a JButton for the centerPanel
    JButton exitButton = new JButton("EXIT");
    exitButton.setPreferredSize(new Dimension(240, 50));
    exitButton.addActionListener(new StartButtonListener(this,3));

    
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(startButton);
    mainPanel.add(instButton); 
    mainPanel.add(exitButton); 
    
    //add the main panel to the frame
    this.add(mainPanel);
   
    this.setVisible(true);
    
    this.requestFocusInWindow();
  }
  

  /**
   * main 
   * Main method that starts this application
   * @param args, String array arguments
   */
  public static void main(String[] args) { 
    new StartingFrameOOP();
  }
  

  //This is an inner class
  /**
   * [BackgroundPanel.java]
   * Creates the Jpanel for starting Frame
   * @author Mr.Mangat, Samson Wong
   * @version 1.0 2021/06/15
   */
  class BackgroundPanel extends JPanel { 
    //variables
    private BufferedImage background;
    /**
     * BackgroundPanel constructor
     */
    BackgroundPanel(){
      
      //load image
      try {
        background = ImageIO.read(new File("images/backgroundMenu.png"));
      
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
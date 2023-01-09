//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;

//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

/**
* [GameFrameOOP.java]
* This template can be used as reference or a starting point
* for your final summative project, game frame of the project
* @author Samson Wong, Jaeyong Lee, Mr. Mangat
* @version 1.0 2021/06/15
**/

class GameFrameOOP extends JFrame { 
  
  //Game Screen
  static GameAreaPanelOOP gamePanel;
  
  GameFrameOOP() { 
    super("Minotaur Runner");  
    
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1325, 750);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    
    //Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanelOOP(this);
    this.add(new GameAreaPanelOOP(this));

    this.setFocusable(false);  //we will focus on the JPanel
    this.setVisible(true);    
  } 
}





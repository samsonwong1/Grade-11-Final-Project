import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
* [StartButtonListener.java]
* This is a class that is used to detect a button press
* @author Jayeong Lee, Mr. Mangat
* @version 1.0 2021/06/15
**/

public class StartButtonListener implements ActionListener {  
  JFrame parentFrame;
  private int buttonOption; 

  //Sound effects 
  public static AudioInputStream audioStream; 
  public static Clip buttonSoundEffect; 
  
  /**
  * StartButtonListener constructor
  * @param parent, the JFrame of the Starting Frame
  * @param buttonOption, number that corresponds with the button pressed in the starting frame
  */
  public StartButtonListener(JFrame parent, int buttonOption) { 
    this.buttonOption=buttonOption;
    parentFrame = parent;
  }

  /**
  * actionPerformed 
  * checks all interactions when a key is pressed
  * @param event, ActionEvent
  */
  public void actionPerformed(ActionEvent event) { 

    //Load the sound effect for when a button is pressed
    try {
      File buttonSoundFile = new File("sounds/buttonClick.wav");
      audioStream = AudioSystem.getAudioInputStream(buttonSoundFile);
      buttonSoundEffect = AudioSystem.getClip();
      buttonSoundEffect.open(audioStream);
    } catch (Exception ex) {
      System.out.println("Error loading button click sound");
    }  
    
    if (buttonOption == 1) { //Button 1 is play button
      buttonSoundEffect.start(); //Play sound
      System.out.println("Starting new Game");
      parentFrame.dispose();
      new GameFrameOOP();
    } else if (buttonOption == 2) { //Button 2 is instructions button
      buttonSoundEffect.start(); //Play sound
      ImageIcon instructions = new ImageIcon("images/instructions.png");
      JOptionPane.showMessageDialog(null, null, "Instructions Page", JOptionPane.INFORMATION_MESSAGE, instructions);
    } else if (buttonOption == 3) { //Button 3 is exit button
      System.exit(0);
    }
  }  
}

  
  


  
  
 
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
* [ExitButtonListener.java]
* This is a class that is used to detect a button press in the game win and lose menus
* @author Jayeong Lee, Mr. Mangat
* @version 1.0 2021/06/15
**/
class ExitButtonListener implements ActionListener {  
   JFrame parentFrame;
   private int number;

  //Sound effects 
  public static AudioInputStream audioStream; 
  public static Clip buttonSoundEffect; 

   /**
   * ExitButtonListener constructor
   * @param parent, the parent JFrame
   * @param number, the button that was pressed by the user on the menu
   */
   ExitButtonListener(JFrame parent, int number) { 
     parentFrame = parent;
     this.number=number;
   }
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

      if (number==1) {
        buttonSoundEffect.start();
        parentFrame.dispose();
        new StartingFrameOOP(); //create a new frame after removing the current one
      } else if (number==2) {
        System.exit(0);
      }
    }
 }
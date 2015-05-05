package com.christopherbahn;
// TODO This is not actually part of the program - it's code I found that I'm looking at to see how it works
// modifying this: http://www.deitel.com/articles/java_tutorials/20060422/LoadingPlayingAudioClips/
/**
 * Created by christopherbahn on 4/28/15.
 */
// Fig. 21.5: LoadAudioAndPlay.java
// Load an audio clip and play it.
import java.applet.AudioClip;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class Audio extends JApplet
   	{
      private AudioClip sound1, sound2, currentSound;
      private JButton playJButton, loopJButton, stopJButton;
      private JComboBox soundJComboBox;

       // load the image when the applet begins executing
      public void init()
      {
         setLayout( new FlowLayout() );

         String choices[] = { "Welcome", "Hi" };
         soundJComboBox = new JComboBox( choices ); // create JComboBox

         soundJComboBox.addItemListener(

                  new ItemListener() // anonymous inner class
                    {
                        // stop sound and change to sound to user's selection
                       public void itemStateChanged( ItemEvent e )
                       {
                          currentSound.stop();
                          currentSound = soundJComboBox.getSelectedIndex() == 0 ?
                             sound1 : sound2;
                          } // end method itemStateChanged
                       } // end anonymous inner class
                    ); // end addItemListener method call

                    add( soundJComboBox ); // add JComboBox to applet

                     // set up button event handler and buttons
                    ButtonHandler handler = new ButtonHandler();

                    // create Play JButton
                    playJButton = new JButton( "Play" );
                    playJButton.addActionListener( handler );
                    add( playJButton );

                    // create Loop JButton
                    loopJButton = new JButton( "Loop" );
                    loopJButton.addActionListener( handler );
                    add( loopJButton );

                    // create Stop JButton
                    stopJButton = new JButton( "Stop" );
                    stopJButton.addActionListener( handler );
                    add( stopJButton );

                     // load sounds and set currentSound
                    sound1 = getAudioClip( getDocumentBase(), "welcome.wav" );
                    sound2 = getAudioClip( getDocumentBase(), "hi.au" );
                    currentSound = sound1;
                 } // end method init

                  // stop the sound when the user switches Web pages
                 public void stop()
           	{
                    currentSound.stop(); // stop AudioClip
                 } // end method stop



        // private inner class to handle button events
                 private class ButtonHandler implements ActionListener
   {
        // process play, loop and stop button events
        public void actionPerformed( ActionEvent actionEvent )
        {
        if ( actionEvent.getSource() == playJButton )
        currentSound.play(); // play AudioClip once
        else if ( actionEvent.getSource() == loopJButton )
        currentSound.loop(); // play AudioClip continuously
        else if ( actionEvent.getSource() == stopJButton )
        currentSound.stop(); // stop AudioClip
        } // end method actionPerformed
        } // end class ButtonHandler
        } // end class LoadAudioAndPlay
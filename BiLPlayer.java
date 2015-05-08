package com.christopherbahn;
// Code by Christopher Bahn, building from code from the following sources:
// https://www.daniweb.com/software-development/java/threads/475808/how-to-play-mp3-files-in-java-using-eclipse
// http://stackoverflow.com/questions/18340125/how-to-tell-if-mediaplayer-is-playing
// https://docs.oracle.com/javafx/2/api/javafx/scene/media/MediaPlayer.html
// http://stackoverflow.com/questions/7359906/swing-jtoolbarbutton-pressing/7360696#7360696

// todo ths should be replaced by the music player that works

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.applet.AudioClip;


/**
 * Created by christopherbahn on 4/21/15.
 */
public class BiLPlayer extends JFrame implements WindowListener{
    private JTable musicDataTable;
    private JPanel rootPanel;
    private JButton playButton;
    private JLabel nowPlayingLabel;
    private JButton quitButton;
    private JLabel playerStatusLabel;
    private JButton stopMusic;

    private MediaPlayer player = null;
//    private JLabel playPauseStopIcon;
    // Create a "clickable" image icon.
    ImageIcon icon = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/Media-Player-Pause-fast-forward-backward-button-icon-vector.jpg");


    private JToggleButton playPauseStopIcon;
    private JTree albumTree;

    private AudioClip songToPlay;
    private boolean isPlaying = false;

    public BiLPlayer(MusicDataModel musicDataModel) {
        setContentPane(rootPanel);
        pack();
        setVisible(true);
        setTitle("Boiled in Lead Music Player");
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon playerIcon_PLAY = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/50px/playerIcon-PLAY.jpg");
        ImageIcon playerIcon_PAUSE = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/50px/playerIcon-PAUSE.jpg");
        ImageIcon playerIcon_STOP = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/50px/playerIcon-STOP.jpg");
        ImageIcon playerIcon_FF = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/50px/playerIcon-FF.jpg");
        ImageIcon playerIcon_REWIND = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/50px/playerIcon-REWIND.jpg");
        ImageIcon playerIcon_MUTE = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/50px/playerIcon-MUTE.jpg");
        playPauseStopIcon.setIcon(playerIcon_PLAY);
        playPauseStopIcon.setSelectedIcon(playerIcon_PAUSE);
        //Set up JTable
        musicDataTable.setGridColor(Color.BLUE);
        musicDataTable.setModel(musicDataModel);

        //Hack to force JavaFX init
        //https://www.daniweb.com/software-development/java/threads/475808/how-to-play-mp3-files-in-java-using-eclipse
        new javafx.embed.swing.JFXPanel();


        String[] stuff = {"first", "second"};
        albumTree = new JTree(stuff);

        playPauseStopIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                System.out.println("CLICKED");
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO If you hit the button more than once while on the same song, it should toggle audio on/off. Right now, it simply starts playing new audio on top of the old one. If you select a new song and hit that, it should stop the first song and start the new one.
                int currentRow = musicDataTable.getSelectedRow();
                Object album = musicDataTable.getValueAt(currentRow, 0);
                Object songTitle = musicDataTable.getValueAt(currentRow, 1);
                Object year = musicDataTable.getValueAt(currentRow, 2);
                Object audioURL = musicDataTable.getValueAt(currentRow, 4);

                nowPlayingLabel.setText("Now playing \"" + songTitle + "\" from " + album + " (" + year + ")!");
                System.out.println("Now playing \"" + songTitle + "\" from " + album + " (" + year + ")!");


                //Once JavaFX init has occurred, can play a MP3 using JavaFX Media/MediaPlayer classes
                //TODO stopping, pausing, not blocking the GUI thread when the song is playing....
                if (player != null) {
                    if (player.getMedia().getSource().equals(audioURL)) { // to find out if the audio is already playing
                        System.out.println("already playing " + audioURL);
                    }

                    player.stop();
                    player.dispose();
                }



                Media mediaObject = new Media((String) audioURL);
                player = new MediaPlayer(mediaObject);
                player.play();
                System.out.println("player is playing");

                playerStatusLabel.setText(player.getStatus() + "!");
                System.out.println(player.getStatus() + "!");




            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicDatabase.shutdown();
                System.exit(0);    //quit if user presses the q key.

            }
        });


        musicDataTable.addComponentListener(new ComponentAdapter() {
        });
        stopMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player != null) {
                    player.stop();
                    player.dispose();
                }
            }
        });
    }


    //windowListener methods. For now, only using windowClosing, which will call DB shutdown code.

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        MusicDatabase.shutdown();}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}

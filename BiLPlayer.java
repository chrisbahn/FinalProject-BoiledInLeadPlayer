package com.christopherbahn;
// Code by Christopher Bahn, building from code from the following sources:
// https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html
// https://www.daniweb.com/software-development/java/threads/475808/how-to-play-mp3-files-in-java-using-eclipse
// http://stackoverflow.com/questions/18340125/how-to-tell-if-mediaplayer-is-playing
// https://docs.oracle.com/javafx/2/api/javafx/scene/media/MediaPlayer.html
// http://stackoverflow.com/questions/7359906/swing-jtoolbarbutton-pressing/7360696#7360696

// todo ths should be replaced by the music player that works

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.applet.AudioClip;
import java.net.URL;


/**
 * Created by christopherbahn on 4/21/15.
 */
public class BiLPlayer extends JFrame implements WindowListener{
    private JTable musicDataTable;
        private JPanel rootPanel;
    private JToggleButton playButton;
    private JLabel nowPlayingLabel;
    private JButton quitButton;
    private JLabel playerStatusLabel;
    private JButton stopButton;
    private MediaPlayer player = null;
//    private JLabel playPauseStopIcon;
    // Create a "clickable" image icon.
//    ImageIcon icon = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/Media-Player-Pause-fast-forward-backward-button-icon-vector.jpg");
    private JToggleButton muteButton;
    private JTree albumTree;
    private JButton fastForwardButton;
    private JButton rewindButton;
    private JTextField thisIsWhereExtendedTextField;
    private JLabel titleLabel;

    public BiLPlayer(MusicTreeModel musicTreeModel) {
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
        ImageIcon bil_SquareLogo = new ImageIcon("/Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/bil-squarelogo.jpg");
        playButton.setIcon(playerIcon_PLAY);
        playButton.setSelectedIcon(playerIcon_PAUSE);
        muteButton.setIcon(playerIcon_MUTE);
        muteButton.setSelectedIcon(playerIcon_MUTE);
        stopButton.setIcon(playerIcon_STOP);
        rewindButton.setIcon(playerIcon_REWIND);
        fastForwardButton.setIcon(playerIcon_FF);
        titleLabel.setIcon(bil_SquareLogo);
        //Set up JTable
//        musicDataTable.setGridColor(Color.YELLOW);
//        musicDataTable.setModel(musicDataModel);
        albumTree.setModel(musicTreeModel);
      //  makeTree();
        //Hack to force JavaFX init
        //https://www.daniweb.com/software-development/java/threads/475808/how-to-play-mp3-files-in-java-using-eclipse
        new JFXPanel();




        if (player != null) {
            playerStatusLabel.setText(player.getStatus() + "!");
            System.out.println(player.getStatus() + "!");
        }

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

                // TODO need a method here for forcing the player to play the first song if the user does not choose a row before hitting play
//                if (player != null) {
//                    if (player.getMedia().getSource().equals(audioURL)) { // to find out if the audio is already playing
//                        player.pause();
//                        System.out.println("already playing " + songTitle);
//                    }
//                }
                // TODO need a proper toggle from pause to play. currently player simply restarts song
                if(playButton.isSelected()){
                    Media mediaObject = new Media((String) audioURL);
                    player = new MediaPlayer(mediaObject);
                    player.play();
                    System.out.println("player is playing");
                } else {
                    player.pause();
                    System.out.println("player is paused");
                }

//                playerStatusLabel.setText(player.getStatus() + "!");
//                System.out.println(player.getStatus() + "!");




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
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player != null) {
                    player.stop();
                    player.dispose();
                }
            }
        });
        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // TODO song selection moves to previous row. Problem: What if user selects a different row while song is playing, then hits rewind? In that case, this would play the song one up from the selected row, not the current song. Also: If you hit rewind while on first song, simply restart first song.
            }
        });
        fastForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // TODO song selection moves to NEXT row. Otherwise this has similar issues as rewindButton.
            }
        });
        muteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        thisIsWhereExtendedTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        albumTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {

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

    public void makeTree() {
        System.out.println("making tree");
        //        String[] stuff = {"first", "second"};
        //     albumTree = new JTree(stuff);
//        albumTree.setModel(musicTreeModel);
        // TODO perhaps you're going about this wrong. If you already have the complete RS, can you select from that as you do with the Music Table to make the Tree data?
//        albumTree.add("The Well Below", ?);

        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode("Boiled in Lead albums");
        createNodes(top);
        albumTree = new JTree(top);
        albumTree.setVisible(true);
        albumTree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

    }

    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode album;
        DefaultMutableTreeNode song;

        album = new DefaultMutableTreeNode("Orb");
        top.add(album);
        top.insert(album,0);

        album = new DefaultMutableTreeNode("The Well Below");
        top.add(album);

        song = new DefaultMutableTreeNode(new SongInfo
                ("Wedding Dress",
                        "tutorial.html"));
        album.add(song);

//        //Tutorial Continued
//        book = new DefaultMutableTreeNode(new BookInfo
//                ("The Java Tutorial Continued: The Rest of the JDK",
//                        "tutorialcont.html"));
//        category.add(book);
//
//        //Swing Tutorial
//        book = new DefaultMutableTreeNode(new BookInfo
//                ("The Swing Tutorial: A Guide to Constructing GUIs",
//                        "swingtutorial.html"));
//        category.add(book);
//
//        //...add more books for programmers...
//
//        category = new DefaultMutableTreeNode("Books for Java Implementers");
//        top.add(category);
//
//        //VM
//        book = new DefaultMutableTreeNode(new BookInfo
//                ("The Java Virtual Machine Specification",
//                        "vm.html"));
//        category.add(book);
//
//        //Language Spec
//        book = new DefaultMutableTreeNode(new BookInfo
//                ("The Java Language Specification",
//                        "jls.html"));
//        category.add(book);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    private class SongInfo {
        public String songName;
// TODO ADD THE NECESSARY COMPONENTS OF A SONG FROM THE SQL DB
        public SongInfo(String song, String filename) {
            songName = song;
//            bookURL = getClass().getResource(filename);
//            if (bookURL == null) {
//                System.err.println("Couldn't find file: "
//                        + filename);
//            }
        }
        public String toString() {
            return songName;
        }
    }


}

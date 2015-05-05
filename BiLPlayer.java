package com.christopherbahn;

// todo ths should be replaced by the music player that works

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.applet.AudioClip;
import javax.swing.JApplet;


/**
 * Created by christopherbahn on 4/21/15.
 */
public class BiLPlayer extends JFrame implements WindowListener{
    private JTable musicDataTable;
    private JPanel rootPanel;
    private JButton playMusic;
    private JLabel testLabel;
    private JButton quitButton;
    private AudioClip songToPlay;


    public BiLPlayer(MusicDataModel musicDataModel) {
        setContentPane(rootPanel);
        pack();
        setVisible(true);
        setTitle("Boiled in Lead Music Player");
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up JTable
        musicDataTable.setGridColor(Color.BLACK);
        musicDataTable.setModel(musicDataModel);

        playMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO this is the "play" button, so when this fires it should toggle audio on/off
                int currentRow = musicDataTable.getSelectedRow();
                Object songTitle = musicDataTable.getValueAt(currentRow, 1);
            // TODO You'll need a field in the SQL table with a URL linking to audio clip. That clip is played here.
                Object audioURL = musicDataTable.getValueAt(currentRow, 4);
                testLabel.setText("Now playing " + songTitle + " from " + audioURL + "!");
          //      songToPlay = getAudioClip(audioURL);
                songToPlay.play();

            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicDatabase.shutdown();
                System.exit(0);    //quit if user presses the q key.

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

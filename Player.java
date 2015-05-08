//package com.christopherbahn;
//
///**
// * Created by christopherbahn on 5/5/15.
// */
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.stage.Stage;
//import javafx.embed.swing.JFXPanel;
//
//import javax.swing.*;
//import java.awt.*;
//
//import static com.sun.javafx.fxml.expression.Expression.add;
//
////References : http://docs.oracle.com/javase/8/javafx/get-started-tutorial/hello_world.htm
////http://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
//
////This is a JavaFX application. Your GUIs have all been Swing applications.
//// However, you can add FX components to Swing
////applications, http://docs.oracle.com/javafx/2/swing/jtable-barchart.htm#CHDBHIJJ
//
//public class Player extends Application { // todo needs to extend JApplet in order to show the music table?
//    boolean playing = false;
//    Media media;
//    MediaPlayer player;
//    // adding MusicTable, copied from BiLPlayer
//    private JTable musicDataTable;
//    private MusicDataModel musicDataModel;
//    private static JFXPanel playerFXPanel;
//    private static final int PANEL_WIDTH_INT = 600;
//    private static final int PANEL_HEIGHT_INT = 400;
//    private static final int TABLE_PANEL_HEIGHT_INT = 100;
//
//    @Override
//    public void start(Stage primaryStage) {
//        Button btn = new Button();
//        btn.setText("Play MP3");
////        String file = "file:///music.mp3";
//        String file = "file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1994AntlerDance/09Rasputin.mp3";
////        String file = musicDataTable.getValueAt(currentRow, 4);
//// TODO In order to play the song a user selects, String file would need to capture the text of musicDataTable.getValueAt(currentRow, 4). Write a method for this in MusicDataModel. Or should that go in BiLPlayer, where the JTable lives now? Can it do something with Object audioURL?
//
//// TODO This now plays audio! But it doesn't like spaces in the filename, so unless there's a way around that (and you'd think there would be), you need to rename all the music files and folders.
//
//
//        // Set up JavaFX panel for the player buttons
//        playerFXPanel = new JFXPanel();
//        playerFXPanel.setPreferredSize(new Dimension(PANEL_WIDTH_INT, PANEL_HEIGHT_INT));
//
//        // TODO I need to move the JTable out of BiLPlayer and into here. Its placement seems to have something to do with Stackpane below.
//        //Set up JTable
//        JTable musicDataTable = new JTable(musicDataModel);
//        musicDataTable.setGridColor(Color.BLACK);
//  //      musicDataTable.setModel(musicDataModel);
//        JScrollPane tablePanel = new JScrollPane(musicDataTable);
//        tablePanel.setPreferredSize(new Dimension(PANEL_WIDTH_INT, TABLE_PANEL_HEIGHT_INT));
//
//        JPanel musicTablePanel = new JPanel();
//        musicTablePanel.setLayout(new BorderLayout());
//
//        //Create split pane that holds both the bar chart and table
//        JSplitPane jsplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        jsplitPane.setTopComponent(musicTablePanel);
//        jsplitPane.setBottomComponent(tablePanel);
//        jsplitPane.setDividerLocation(410);
//        musicTablePanel.add(playerFXPanel, BorderLayout.CENTER);
//
//        //Add the split pane to the content pane of the application
//        add(jsplitPane, BorderLayout.CENTER);
//
//
//
//        // final URL resources = getClass().getResource("file:///C:/music.mp3");
//        Media mediaObject = new Media(file);
//        final MediaPlayer player = new MediaPlayer(mediaObject);
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                player.play();
//            }
//        });
//
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
////        root.getChildren().add(musicTablePanel);
//
//
//        Scene scene = new Scene(root,300,250);
//
//        primaryStage.setTitle("Hello MP3!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) throws RuntimeException {
//        launch(args);
//
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//                } catch (Exception e) {}
//
//                JFrame frame = new JFrame("Swing JTable");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                JApplet applet = new SwingInterop();
//                applet.init();
//
//                frame.setContentPane(applet.getContentPane());
//
//                frame.pack();
//                frame.setLocationRelativeTo(null);
//                frame.setVisible(true);
//
//                applet.start();
//            }
//        });
//
//    }
//}
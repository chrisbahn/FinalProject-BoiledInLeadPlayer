package com.christopherbahn;

// some code on this page found/modified from here: http://www.codejava.net/java-se/swing/jtree-basic-tutorial-and-examples

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.sql.*;
import java.util.*;

/**
 * Created by christopherbahn on 4/24/15.
 */

// A modified version of the movie database from Lab 12

public class MusicDatabase {
    private static final String PROTOCOL = "jdbc:derby:";
    private static final String DB_NAME = "musicDB";
    private static final String USER = "user";
    private static final String PASS = "password";

    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;

    public final static String MUSIC_TABLE_NAME = "music";
    public final static String RELEASEORDER_COLUMN = "album";
    public final static String ALBUM_COLUMN = "album";
    public final static String TRACKNUMBER_COLUMN = "album";
    public final static String SONG_COLUMN = "song";
    public final static String YEAR_COLUMN = "year_released";
    public final static String DURATION_COLUMN = "duration";
    public final static String AUDIOURL_COLUMN = "audioURL";
// TODO need variables for: Artist ID (it may not always be Boiled In Lead! Make this an int, unique across all tables), Artist Name (String), Album ID (int, unique across all tables), Song ID (int, unique across all tables), Album art (links to a jpg, probably lives in the Album table), Audio (links to the audio file, probably lives in the Song table)
    // TODO Add an XML database with more information on the songs: Band members (bios?), lyrics, songwriting credits, Trivia (i.e. "Rasputin was originally performed by a German disco band."), playable video, Amazon link, Wikipedia page

    private static MusicDataModel musicDataModel;
    private static MusicTreeModel musicTreeModel;

    // TODO Should this main method be moved to the Main.java page?
    public static void main(String args[]) {

        //setup creates database (if it doesn't exist), opens connection, and adds sample data
        setup();
      //  loadAllMusic();
        loadTreeData();

        //Start GUI
//        final BiLPlayer biLPlayer = new BiLPlayer(musicDataModel);
        final BiLPlayer biLPlayer = new BiLPlayer(musicTreeModel);

        // TODO do I need this to help populate the JTree?
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
//                    biLPlayer.makeTree();
                }
            });



    }

    //Create or recreate a ResultSet containing the whole database, and give it to musicDataModel
    public static void loadAllMusic(){
        try{
            if (rs!=null) {
                rs.close();
            }
            String getAllData = "SELECT * FROM music";
            rs = statement.executeQuery(getAllData);

            if (musicDataModel == null) {
                //If no current musicDataModel, then make one
                musicDataModel = new MusicDataModel(rs);
            } else {
                //Or, if one already exists, update its ResultSet
                musicDataModel.updateResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("Error loading or reloading music");
            System.out.println(e);
        }
    }

    //Create or recreate a ResultSet containing what albumTree needs to show, and give it to musicTreeModel
    public static void loadTreeData(){
        try{
            if (rs!=null) {
                rs.close();
            }
            String getTreeData = "SELECT * FROM music";
            rs = statement.executeQuery(getTreeData);

            // TODO What if you made the JTree this way:
            // Albums have an albumID of X, and a song ID of 0
            // Songs have an albumID of X, and a song ID of non-0 Y
            // iterate over rs. If songID == 0, create new parent node. If songID > 0, create child node to established parent node.

            try {
                //Move cursor to the start...
                rs.beforeFirst();
                // next() method moves the cursor forward one row and returns true if there is another row ahead
                int checker = 0; // testing to see if this attempt to create the JTree is working
                while (rs.next()) {
                    System.out.println("making tree " + checker);
                    checker++;

                    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Boiled in Lead player");
                    DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode("Boiled in Lead player");
                    DefaultMutableTreeNode songNode = new DefaultMutableTreeNode("Boiled in Lead player");
//                    Album album;
//                    Song song;
                    //                    createNodes(root);
//                    albumTree = new JTree(root);

                    // TODO     This is where the album/song nodes are created.
                    if (rs.getInt(2)==0) { // IF SONGID IS ZERO, THIS IS AN ALBUM NODE
//                        LinkedList<Song> tracks = new LinkedList<Song>();
//                        album = new Album(rs.getInt(0), rs.getString(1), rs.getInt(4), tracks, rs.getString(6));
                        root.add(albumNode);
                    }
                    if (rs.getInt(2)>0) { // IF SONGID > ZERO, THIS IS A SONG NODE
//                        song = new Song(rs.getInt(2), rs.getString(3), rs.getInt(5), rs.getString(6));
//                        album.tracks.add(song);
                        albumNode.add(songNode);
                    }
                    }
                rs.beforeFirst();
            } catch (SQLException se) {
                System.out.println("Error counting rows " + se);
            }


            if (musicTreeModel == null) {
                //If no current musicTreeModel, then make one
                musicTreeModel = new MusicTreeModel(rs);
            } else {
                //Or, if one already exists, update its ResultSet
                musicTreeModel.updateResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("Error loading or reloading tree data");
            System.out.println(e);
        }
    }

    public static void setup(){
        try {
            conn = DriverManager.getConnection(PROTOCOL + DB_NAME + ";create=true", USER, PASS);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // This code deletes any existing music DB before loading/creating a new one, so that any changes made offline are reflected here.
            String deleteOldTableSQL = "DROP TABLE " + MUSIC_TABLE_NAME;
            statement.executeUpdate(deleteOldTableSQL);
            System.out.println("deleted old music table");

            // TODO duration is an int representing seconds. To display this, you'll need to do a calculation to split this up into minutes & seconds. Also, total album length could be derived by adding up the duration for all songs with that particular album ID
            String createTableSQL = "CREATE TABLE "
                    // TODO What are the basic variables for the SQL database? include them all here
                    + MUSIC_TABLE_NAME + " ("
                    + RELEASEORDER_COLUMN + " int, "
                    + ALBUM_COLUMN + " varchar(50), "
                    + TRACKNUMBER_COLUMN + " int, "
                    + SONG_COLUMN + " varchar(50), "
                    + YEAR_COLUMN + " int, "
                    + DURATION_COLUMN + " int, "
                    + AUDIOURL_COLUMN + " varchar(200))";
            statement.executeUpdate(createTableSQL);

            System.out.println("Created music table");
            //
            // TODO Once the database is proven to work, create another database that will include ALL the music. This will be imported into the program ONCE and then should not need to be imported again, if you've done everything right
            // NOTE that the audioURL column is used for the URL of an mp3 in the case of songs, and for the URL of an image file in the case of albums. They will be handled differently during creation of the JTree
            String addDataSQL = "INSERT INTO music VALUES (1, 'Old Lead', 0, null, 1985, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-oldlead.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (2, 'From the Ladle to the Grave', 0, null, 1989, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-fromtheladletothegrave.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (2, 'From the Ladle to the Grave', 1, 'Madman Blues', 1989, 847, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1989FromtheLadletotheGrave/02MadmanMoraBlues.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (2, 'From the Ladle to the Grave', 2, 'The Microorganism', 1989, 847, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1989FromtheLadletotheGrave/06TheMicroorganism.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (3, 'Orb', 0, null, 1990, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-antlerdance.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (4, 'Antler Dance', 0, null, 1994, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-antlerdance.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (4, 'Antler Dance!', 1, 'Newry Highwayman', 1994, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1994AntlerDance/01NewryHighwayman.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (4, 'Antler Dance!', 2, 'Rasputin', 1994, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1994AntlerDance/09Rasputin.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (5, 'Songs From the Gypsy', 0, null, 1995, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-songsfromthegypsy.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (6, 'Alloy', 0, null, 1998, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-alloy.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (7, 'Alloy 2.1: Archive', 0, null, 1998, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-alloy2.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (8, 'Alloy 2.2: Live', 0, null, 1998, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-alloy2.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (9, '20 Years of Rock 'n' Reel, 0, null, 2003, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/bil-squarelogo.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 0, null, 2008, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-silver.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 1, 'Apple Tree Wassail', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/01AppleTreeWassail.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 2, 'The Sunset', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/02TheSunset.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 3, 'Jolly Tinker', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/03JollyTinker.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 4, 'Silver Carp', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/04SilverCarp.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 5, 'Come In From the Rain', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/05ComeInFromTheRain.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 6, 'House Carpenter', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/06HouseCarpenter.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 7, 'Berber', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/07Berber.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 8, 'Death On Hennepin', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/08DeathOnHennepin.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 9, 'Corner House', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/09CornerHouse.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 10, 'Menfi', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/10Menfi.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (10, 'Silver', 11, 'Rushes Green', 2008, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2008Silver/11RushesGreen.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (11, 'The Well Below', 0, null, 2012, null, /Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/images/100px/albumcover-wellbelow.jpg)";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (11, 'The Well Below', 1, 'Wedding Dress', 2012, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2012TheWellBelow/01WeddingDress.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (11, 'The Well Below', 2, 'The Well Below The Valley', 2012, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2012TheWellBelow/02TheWellBelowTheValley.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (11, 'The Well Below', 3, 'Western Borders', 2012, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2012TheWellBelow/03WesternBorders.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES (11, 'The Well Below', 4, 'Transylvanian Stomp', 2012, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2012TheWellBelow/04TransylvanianStomp.mp3')";
            statement.executeUpdate(addDataSQL);
        } catch (SQLException se) {
            System.out.println("The Music table (probably) already exists, verify with following error message.");
            System.out.println(se);
        }
    }

    //Close the ResultSet, statement and connection, in that order.
    public static void shutdown(){
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Result set closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (statement != null) {
                statement.close();
                System.out.println("Statement closed");
            }
        } catch (SQLException se){
            //Closing the connection could throw an exception too
            se.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database connection closed");
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

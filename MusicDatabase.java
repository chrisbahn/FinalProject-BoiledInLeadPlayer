package com.christopherbahn;

// some code on this page found/modified from here: http://www.codejava.net/java-se/swing/jtree-basic-tutorial-and-examples

import javax.swing.*;
import java.sql.*;

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
    public final static String ALBUM_COLUMN = "album";
    public final static String SONG_COLUMN = "song";
    public final static String YEAR_COLUMN = "year_released";
    public final static String DURATION_COLUMN = "duration";
    public final static String AUDIOURL_COLUMN = "audioURL";
// TODO need variables for: Artist ID (it may not always be Boiled In Lead! Make this an int, unique across all tables), Artist Name (String), Album ID (int, unique across all tables), Song ID (int, unique across all tables), Album art (links to a jpg, probably lives in the Album table), Audio (links to the audio file, probably lives in the Song table)
    // TODO Add an XML database with more information on the songs: Band members (bios?), lyrics, songwriting credits, Trivia (i.e. "Rasputin was originally performed by a German disco band."), playable video, Amazon link, Wikipedia page

    private static MusicDataModel musicDataModel;
//    private static MusicTreeModel musicTreeModel;

    // TODO Should this main method be moved to the Main.java page?
    public static void main(String args[]) {

        //setup creates database (if it doesn't exist), opens connection, and adds sample data
        setup();
        loadAllMusic();
//        loadTreeData();

        //Start GUI
        final BiLPlayer biLPlayer = new BiLPlayer(musicDataModel);

        // TODO do I need this to help populate the JTree?
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    biLPlayer.makeTree();
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
//    public static void loadTreeData(){
//        try{
//            if (rs!=null) {
//                rs.close();
//            }
//            String getTreeData = "SELECT ALBUM_COLUMN, SONG_COLUMN, YEAR_COLUMN, DURATION_COLUMN FROM music";
//            rs = statement.executeQuery(getTreeData);
//
//            if (musicTreeModel == null) {
//                //If no current musicDataModel, then make one
//                musicTreeModel = new MusicTreeModel(rs);
//            } else {
//                //Or, if one already exists, update its ResultSet
//                musicTreeModel.updateResultSet(rs); // todo copy/mod this from MusicDataModel into MusicTreeModel
//            }
//        } catch (Exception e) {
//            System.out.println("Error loading or reloading tree data");
//            System.out.println(e);
//        }
//    }

    public static void setup(){
        try {
            conn = DriverManager.getConnection(PROTOCOL + DB_NAME + ";create=true", USER, PASS);

            // The first argument ResultSet.TYPE_SCROLL_INSENSITIVE
            // allows us to move the cursor both forward and backwards through the RowSet
            // we get from this statement.

            // (Some databases support TYPE_SCROLL_SENSITIVE, which means the ResultSet will be updated when
            // something else changes the database. Since Derby is embedded we don't need to worry about anything
            // else updating the database. If you were using a server DB you might need to be concerned about this.)

            // The TableModel will need to go forward and backward through the ResultSet.
            // by default, you can only move forward - it's less
            // resource-intensive than being able to go in both directions.
            // If you set one argument, you need the other.
            // The second one (CONCUR_UPDATABLE) means you will be able to change the ResultSet and see the changes in the DB
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // This code deletes any existing music DB before loading/creating a new one, so that any changes made offline are reflected here.
            String deleteOldTableSQL = "DROP TABLE " + MUSIC_TABLE_NAME;
            statement.executeUpdate(deleteOldTableSQL);
            System.out.println("deleted old music table");

            //Create a table in the database with 5 columns: Album, song, year, duration and audioURL.
            // TODO duration is an int representing seconds. To display this, you'll need to do a calculation to split this up into minutes & seconds. Also, total album length could be derived by adding up the duration for all songs with that particular album ID
            String createTableSQL = "CREATE TABLE "
                    // TODO What are the basic variables for the SQL database? include them all here
                    + MUSIC_TABLE_NAME + " ("
                    + ALBUM_COLUMN + " varchar(50), "
                    + SONG_COLUMN + " varchar(50), "
                    + YEAR_COLUMN + " int, "
                    + DURATION_COLUMN + " int, "
                    + AUDIOURL_COLUMN + " varchar(200))";
            statement.executeUpdate(createTableSQL);

            System.out.println("Created music table");
            //
            // TODO Add some test data using the music dataset variables. For a small starting dataset, choose songs from four different albums
            // TODO Once the database itself is proven to work, you'll need to create another, much larger database that will include ALL the music. This will be imported into the program ONCE and then should not need to be imported again, if you've done everything right
            String addDataSQL = "INSERT INTO music VALUES ('From the Ladle to the Grave', 'Madman Blues', 1989, 847, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1989FromtheLadletotheGrave/02MadmanMoraBlues.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES ('From the Ladle to the Grave', 'The Microorganism', 1989, 847, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1989FromtheLadletotheGrave/06TheMicroorganism.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES ('Antler Dance!', 'Newry Highwayman', 1994, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1994AntlerDance/01NewryHighwayman.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES ('Antler Dance!', 'Rasputin', 1994, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/1994AntlerDance/09Rasputin.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES ('The Well Below', 'Wedding Dress', 2012, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2012TheWellBelow/01WeddingDress.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES ('The Well Below', 'The Well Below The Valley', 2012, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2012TheWellBelow/02TheWellBelowTheValley.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES ('The Well Below', 'Western Borders', 2012, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2012TheWellBelow/03WesternBorders.mp3')";
            statement.executeUpdate(addDataSQL);
            addDataSQL = "INSERT INTO music VALUES ('The Well Below', 'Transylvanian Stomp', 2012, 867, 'file:///Users/christopherbahn/IdeaProjects/FinalProject-BoiledInLeadPlayer/audio/2012TheWellBelow/04TransylvanianStomp.mp3')";
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

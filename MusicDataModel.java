package com.christopherbahn;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by christopherbahn on 4/28/15.
 */
public class MusicDataModel extends AbstractTableModel {

    private int rowCount = 0;
    private int colCount = 0;
    ResultSet resultSet;

    public MusicDataModel(ResultSet rs) {
        this.resultSet = rs;
        setup();
    }

    private void setup(){
        countRows();
        try{
            colCount = resultSet.getMetaData().getColumnCount();
        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }
    }

    public void updateResultSet(ResultSet newRS){
        resultSet = newRS;
        setup();
    }

    private void countRows() {
        rowCount = 0;
        try {
            //Move cursor to the start...
            resultSet.beforeFirst();
            // next() method moves the cursor forward one row and returns true if there is another row ahead
            while (resultSet.next()) {
                rowCount++;
            }
            resultSet.beforeFirst();
        } catch (SQLException se) {
            System.out.println("Error counting rows " + se);
        }

    }
    @Override
    public int getRowCount() {
        countRows();
        return rowCount;
    }

    @Override
    public int getColumnCount(){
        return colCount;
    }

    @Override
    public Object getValueAt(int row, int col){
        try{
            //  System.out.println("get value at, row = " +row);
            resultSet.absolute(row+1);
            Object o = resultSet.getObject(col+1);
            return o.toString();
        }catch (SQLException se) {
            System.out.println(se);
            //se.printStackTrace();
            return se.toString();

        }
    }

    @Override
    //This is called when user edits an editable cell
//    public void setValueAt(Object newValue, int row, int col) {
//
//        //Make sure o is an integer AND that it is in the range of valid ratings
//
//        int newRating;
//
//        try {
//            newRating = Integer.parseInt(newValue.toString());
//
//            if (newRating < MusicDatabase.MOVIE_MIN_RATING || newRating > MusicDatabase.MOVIE_MAX_RATING) {
//                throw new NumberFormatException("Movie rating must be within the valid range");
//            }
//        } catch (NumberFormatException ne) {
//            //Error dialog box. First argument is the parent GUI component, which is only used to center the
//            // dialog box over that component. We don't have a reference to any GUI components here
//            // but are allowed to use null - this means the dialog box will show in the center of your screen.
//            JOptionPane.showMessageDialog(null, "Try entering a number between " + MusicDatabase.MOVIE_MIN_RATING + " " + MusicDatabase.MOVIE_MAX_RATING);
//            //return prevents the following database update code happening...
//            return;
//        }
//
//        //This only happens if the new rating is valid
//        //Derby will permit you to update a ResultSet and see the changes in the
//        //ResultSet and the database. Compare to inserting/deleting where you need
//        //to make a new ResultSet to see the changes.
//        try {
//            resultSet.absolute(row + 1);
//            resultSet.updateInt(MusicDatabase.RATING_COLUMN, newRating);
//            resultSet.updateRow();
//            fireTableDataChanged();
//        } catch (SQLException e) {
//            System.out.println("error changing rating " + e);
//        }
//
//
//    }


//    @Override
    //We only want user to be able to edit column 2 - the rating column.
    //If this method always returns true, the whole table will be editable.

    //TODO how can we avoid using a magic number (if col==2) ) here? This code depends on column 2 being the rating.
    //TODO To fix: look into table column models, and generate the number columns based on the columns found in the ResultSet.
    public boolean isCellEditable(int row, int col){
        if (col == 2) {
            return true;
        }
        return false;
    }

    //Delete row, return true if successful, false otherwise
    public boolean deleteRow(int row){
        try {
            resultSet.absolute(row + 1);
            resultSet.deleteRow();
            //Tell table to redraw itself
            fireTableDataChanged();
            return true;
        }catch (SQLException se) {
            System.out.println("Delete row error " + se);
            return false;
        }
    }

    //returns true if successful, false if error occurs
    public boolean insertRow(String album, String song, int year, int duration, String audioURL) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSet.moveToInsertRow();
            resultSet.updateString(MusicDatabase.ALBUM_COLUMN, album);
            resultSet.updateString(MusicDatabase.SONG_COLUMN, song);
            resultSet.updateInt(MusicDatabase.YEAR_COLUMN, year);
            resultSet.updateInt(MusicDatabase.DURATION_COLUMN, duration);
            resultSet.updateString(MusicDatabase.AUDIOURL_COLUMN, audioURL);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            fireTableDataChanged();
            //This change goes to DB but is *not* reflected in this result set
            //So need to close and re-open result set to see latest data
            //Return true to the calling method so we know that the ResultSet
            //was successfully updated, and it can request a new ResultSet for this tablemodel.
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }

    @Override
    public String getColumnName(int col){
        //Get from ResultSet metadata, which contains the database column names
        //TODO translate DB column names into something nicer for display, so "YEAR_RELEASED" becomes "Year Released"
        try {
            return resultSet.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }


}

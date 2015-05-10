package com.christopherbahn;

// Research on this section:
//        "The Definitive Guide To Java Swing," by John Zukowski, 3rd Edition, particularly "Chapter 17: Trees"
//        http://stackoverflow.com/questions/10014015/java-jtree-populated-by-sql-query
//        http://javaknowledge.info/populate-jtree-from-mysql-database/
//          https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html

        import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.*;
import javax.swing.JTree;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
* Created by christopherbahn on 5/8/15.
*/


public class MusicTreeModel implements TreeModel {
    private int rowCount = 0;
    private int colCount = 0;
    ResultSet resultSet;

    public MusicTreeModel(ResultSet rs) {
        this.resultSet = rs;
        setup();
    }

    private void setup() { // TODO this is copied from the MusicDataModel class. Is it useful here, and how should it be modified?
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
    //@Override
    public int getRowCount() {
        countRows();
        return rowCount;
    }

    // TODO Create a method to populate the JTree out of the musicTreeModel rs. You will eventually want album art in here too
    //returns true if successful, false if error occurs
    public boolean insertRow(String album, String song, int year, int duration) {
        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSet.moveToInsertRow();
            resultSet.updateString(MusicDatabase.ALBUM_COLUMN, album);
            resultSet.updateString(MusicDatabase.SONG_COLUMN, song);
            resultSet.updateInt(MusicDatabase.YEAR_COLUMN, year);
            resultSet.updateInt(MusicDatabase.DURATION_COLUMN, duration);
//            resultSet.updateString(MusicDatabase.AUDIOURL_COLUMN, audioURL);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
//            fireTableDataChanged(); // todo did you need an equivalent of this?
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

    @SuppressWarnings("CallToThreadDumpStack")
    public final JTree pop_tree() {
       JTree albumTree = new JTree();
        try {
            ArrayList list = new ArrayList();
            list.add("Category List");


            while (resultSet.next()) {
                // TODO TO ITERATE, you may need albumID and songID fields in the SQL
                Object value[] = {resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)};
                list.add(value);
            }
            Object hierarchy[] = list.toArray();
//            DefaultMutableTreeNode root = processHierarchy(hierarchy);

//            musicTreeModel = new DefaultTreeModel(root);
//            albumTree.setModel(treeModel);
        } catch (Exception e) {
        }
        return albumTree;
    }

    @SuppressWarnings("CallToThreadDumpStack")
//    public DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
//        DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
//        try {
//            int ctrow = 0;
//            int i = 0;
//            try {
//
//                String sql = "SELECT catid, catname from category";
//                ResultSet rs = stm.executeQuery(sql);
//
//                while (rs.next()) {
//                    ctrow = rs.getRow();
//                }
//                String L1Nam[] = new String[ctrow];
//                String L1Id[] = new String[ctrow];
//                ResultSet rs1 = stm.executeQuery(sql);
//                while (rs1.next()) {
//                    L1Nam[i] = rs1.getString("catname");
//                    L1Id[i] = rs1.getString("catid");
//                    i++;
//                }
//                DefaultMutableTreeNode child, grandchild;
//                for (int childIndex = 0; childIndex < L1Nam.length; childIndex++) {
//                    child = new DefaultMutableTreeNode(L1Nam[childIndex]);
//                    node.add(child);//add each created child to root
//                    String sql2 = "SELECT scatname from subcategory where catid= '" + L1Id[childIndex] + "' ";
//                    ResultSet rs3 = stm.executeQuery(sql2);
//                    while (rs3.next()) {
//                        grandchild = new DefaultMutableTreeNode(rs3.getString("scatname"));
//                        child.add(grandchild);//add each grandchild to each child
//                    }
//                }
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        } catch (Exception e) {
//        }
//
//        return (node);
//    }




    @Override
    public Object getRoot() {
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        return false;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}

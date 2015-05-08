package com.christopherbahn;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;

/**
 * Created by christopherbahn on 5/8/15.
 */


public class MusicTreeModel implements TreeModel {
    ResultSet resultSet;

    public MusicTreeModel(ResultSet rs) {
        this.resultSet = rs;
        setup();
    }

    private void setup() { // TODO this is copied from the MusicDataModel class. Is it useful here, and how should it be modified?
    }

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

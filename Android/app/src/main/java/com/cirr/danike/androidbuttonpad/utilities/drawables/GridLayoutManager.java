package com.cirr.danike.androidbuttonpad.utilities.drawables;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 2018-03-03.
 */

public class GridLayoutManager {

    public class ViewReference {
        private GridLayoutManager manager;

        ViewReference(GridLayoutManager mgr) {
            manager = mgr;
        }

        View _view;
        int _x;
        int _width;
        int _y;
        int _height;

        public View view() {
            return _view;
        }
        public int x() {
            return _x;
        }
        public int y() {
            return _y;
        }
        public int width() {
            return _width;
        }
        public int height() {
            return _height;
        }
    }

    private List<List<ViewReference>> gridViewRefs;
    private GridLayout layout;
    private LayoutInflater inflator;
    private int cellContentId;

    public GridLayoutManager(GridLayout grid, LayoutInflater inflater, int cellContentId) {
        gridViewRefs = new ArrayList<>();

        layout = grid;
        inflator = inflater;
        this.cellContentId = cellContentId;
    }
}

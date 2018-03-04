package com.cirr.danike.androidbuttonpad.utilities.drawables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStructure;
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
        //gridViewRefs = new ArrayList<>();

        layout = grid;
        inflator = inflater;
        this.cellContentId = cellContentId;

        setSize(1,1);
    }

    private int width = -1;
    private int height = -1;

    /**
     * Changes the size of the grid. Also resets the grid contents.
     *
     * @param w Width.
     * @param h Height.
     */
    public void setSize(int w, int h) {
        layout.removeAllViews();

        width = w;
        height = h;

        layout.setColumnCount(width);
        layout.setRowCount(height);

        createViews();
    }

    public ViewReference get(int x, int y) {
        return gridViewRefs.get(x).get(y);
    }

    void set(int x, int y, ViewReference ref) {
        if (gridViewRefs.get(x).size() <= y)
            gridViewRefs.get(x).add(ref);
        gridViewRefs.get(x).set(y, ref);
    }

    private void createViews() {
        // initialize lists
        gridViewRefs = new ArrayList<>(width);
        for (int i = 0; i < width; i++ ) {
            if (gridViewRefs.size() <= i)
                gridViewRefs.add(new ArrayList<>(height));
            else
                gridViewRefs.set(i, new ArrayList<>(height));

            for (int j = 0; j < height; j++) {

                // insert view and create viewreference for it
                ViewReference vr = new ViewReference(this);
                vr._width = 1;
                vr._height = 1;
                vr._x = i;
                vr._y = j;

                View view = inflator.inflate(cellContentId, layout, false);

                vr._view = view;

                // set view layoutparams: row, column (y,x) as opposed to column, row (x,y)
                GridLayout.LayoutParams lparams = new GridLayout.LayoutParams(
                        GridLayout.spec(j,1, 1),
                        GridLayout.spec(i,1, 1)
                );
                view.setLayoutParams(lparams);

                // add the inflated View to the layout
                layout.addView(view);

                this.set(i,j,vr);
            }
        }
    }
}

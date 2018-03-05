package com.cirr.danike.androidbuttonpad.utilities.drawables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public void setWidth(int width) {
            manager.setWidth(_x, _y, width);
        }
        public void setHeight(int height) {
            manager.setHeight(_x, _y, height);
        }
    }

    void setWidth(int x, int y, int width) {
        if (x+width > this.width)
            throw new IndexOutOfBoundsException("New width puts button off screen");

        ViewReference ref = get(x, y);
        int old_width = ref._width;
        ref._width = width;
        View view = ref.view();
        // update columnSpec
        ((GridLayout.LayoutParams)view.getLayoutParams()).columnSpec = GridLayout.spec(x, width, width);

        for (int i = x+1; i < x+width; i++) {
            for (int j = y; j < y+ref._height; j++) {
                ViewReference vr = get(i, j);

                View vw = vr.view();
                ((ViewGroup) vw.getParent()).removeView(vw);

                set(i, j, ref);
            }
        }

        for (int i = x+width; i < x+old_width; i++) {
            for (int j = y; j < y+ref._height; j++) {
                ViewReference vr = createView(i, j, 1, 1);
                set(i, j, vr);
            }
        }
    }

    void setHeight(int x, int y, int height) {
        if (y+height > this.height)
            throw new IndexOutOfBoundsException("New height puts button off screen");

        ViewReference ref = get(x, y);
        int old_height = ref._height;
        ref._height = height;
        View view = ref.view();
        // update rowSpec
        ((GridLayout.LayoutParams)view.getLayoutParams()).rowSpec = GridLayout.spec(y, height, height);

        for (int i = y+1; i < y+height; i++) {
            for (int j = x; j < x+ref._width; j++) {
                ViewReference vr = get(j, i);

                View vw = vr.view();
                ((ViewGroup) vw.getParent()).removeView(vw);

                set(j, i, ref);
            }
        }

        for (int i = y+height; i < y+old_height; i++) {
            for (int j = x; j < x+ref._width; j++) {
                ViewReference vr = createView(j, i, 1, 1);
                set(j, i, vr);
            }
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
    }

    public void init() {
        setSize(6,4);
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
        if (gridViewRefs.get(x).size() == y)
            gridViewRefs.get(x).add(ref);
        else
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
                ViewReference vr = createView(i, j, 1, 1);

                this.set(i,j,vr);
            }
        }
    }

    private ViewReference createView(int x, int y, int width, int height) {
        // insert view and create viewreference for it
        ViewReference vr = new ViewReference(this);
        vr._width = width;
        vr._height = height;
        vr._x = x;
        vr._y = y;

        View view = inflator.inflate(cellContentId, layout, false);

        vr._view = view;

        // set view layoutparams: row, column (y,x) as opposed to column, row (x,y)
        GridLayout.LayoutParams lparams = new GridLayout.LayoutParams(
                GridLayout.spec(y,height, height),
                GridLayout.spec(x,width, width)
        );
        view.setLayoutParams(lparams);

        // add the inflated View to the layout
        layout.addView(view);

        return vr;
    }
}

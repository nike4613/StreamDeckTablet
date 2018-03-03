package com.cirr.danike.androidbuttonpad.drawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.cirr.danike.androidbuttonpad.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get ahold of the instance of your layout
        GridLayout grid = (GridLayout) findViewById(R.id.main_btnGrid);

        grid.setColumnCount(6);
        grid.setRowCount(5);

        // assuming your Wizard content is in content_wizard.xml
        View btn = getLayoutInflater()
                .inflate(R.layout.layout_button, grid, false);

        GridLayout.LayoutParams lparams = new GridLayout.LayoutParams(
                GridLayout.spec(0,1, 1),
                GridLayout.spec(0,1, 1)
        );

        //lparams.height = ViewGroup.LayoutParams.FILL_PARENT;
        //lparams.width = GridLayout.LayoutParams.MATCH_PARENT;

        btn.setLayoutParams(lparams);

        // add the inflated View to the layout
        grid.addView(btn);

        // assuming your Wizard content is in content_wizard.xml
        View btn2 = getLayoutInflater()
                .inflate(R.layout.layout_button, grid, false);

        GridLayout.LayoutParams lparams2 = new GridLayout.LayoutParams(
                GridLayout.spec(0,1, 1),
                GridLayout.spec(1,1, 1)
        );

        //lparams2.height = GridLayout.LayoutParams.MATCH_PARENT;
        //lparams2.width = GridLayout.LayoutParams.MATCH_PARENT;

        btn2.setLayoutParams(lparams2);

        // add the inflated View to the layout
        grid.addView(btn2);
    }
}

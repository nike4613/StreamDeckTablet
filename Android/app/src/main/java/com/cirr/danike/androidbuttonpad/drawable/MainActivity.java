package com.cirr.danike.androidbuttonpad.drawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.cirr.danike.androidbuttonpad.R;
import com.cirr.danike.androidbuttonpad.networking.NetDataBlock;
import com.cirr.danike.androidbuttonpad.utilities.drawables.GridLayoutManager;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get ahold of the instance of your layout
        GridLayout grid = (GridLayout) findViewById(R.id.main_btnGrid);

        GridLayoutManager manager = new GridLayoutManager(grid, getLayoutInflater(), R.layout.layout_button);
        manager.init();

        manager.get(0,0).setWidth(3);
        manager.get(0,0).setHeight(2);

        manager.get(0,2).setWidth(2);
        manager.get(0,2).setHeight(2);

        manager.get(2,2).setWidth(2);
        manager.get(2,2).setHeight(2);

        manager.get(3,0).setWidth(1);
        manager.get(3,0).setHeight(2);

        manager.get(4,0).setWidth(2);
        manager.get(4,0).setHeight(1);

        manager.get(4,2).setWidth(2);
        manager.get(4,2).setHeight(2);

        List<NetDataBlock.NetDataBlockDef> defs;
        try {
            defs = NetDataBlock.fromJsonDefs("{\"ButtonUpdate\":{\"guid\":\"52c6dfc9-2973-43a6-bc92-635dd6118774\",\"fields\":[\"x_pos\",\"y_pos\"]},\"ButtonSizeUpdate\":{\"guid\":\"d4397081-800c-41e4-9637-5a69f46270ef\",\"fields\":[\"x_pos\",\"y_pos\",\"new_width\",\"new_height\"]}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

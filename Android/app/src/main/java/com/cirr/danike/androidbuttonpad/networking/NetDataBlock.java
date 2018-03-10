package com.cirr.danike.androidbuttonpad.networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * Created by aaron on 2018-03-10.
 */

public class NetDataBlock {

    public static class NetDataBlockDef {
        String name;
        UUID guid;
        //public Func<NetDataBlock> createFunc;
        HashSet<String> fields;

        Function<UUID,NetDataBlock> createFunc;

        NetDataBlockDef(String name, JSONObject obj) throws JSONException {
            this.name = name;

            guid = UUID.fromString(obj.getString("guid"));
            JSONArray arr = obj.getJSONArray("fields");

            fields = new HashSet<>();
            for (int i = 0; i < arr.length(); i++) {
                fields.add(arr.getString(i));
            }
        }
    }

    public static List<NetDataBlockDef> fromJsonDefs(JSONObject obj) throws JSONException {
        List<NetDataBlockDef> defs = new ArrayList<>();

        for (Iterator<String> it = obj.keys(); it.hasNext(); ) {
            String key = it.next();

            JSONObject sobj = obj.getJSONObject(key);
            defs.add(new NetDataBlockDef(key, sobj));
        }

        return defs;
    }

    public static List<NetDataBlockDef> fromJsonDefs(String json) throws JSONException {
        return fromJsonDefs(new JSONObject(json));
    }



}

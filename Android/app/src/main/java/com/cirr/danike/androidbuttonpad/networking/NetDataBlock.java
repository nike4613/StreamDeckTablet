package com.cirr.danike.androidbuttonpad.networking;

import com.cirr.danike.androidbuttonpad.networking.annotations.NetworkDataBlock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by aaron on 2018-03-10.
 */

public class NetDataBlock {
    protected static byte[] blockHeader = "NET-dObj".getBytes(StandardCharsets.UTF_8);
    private static Map<Class<? extends NetDataBlock>, UUID> uuidLookup = new HashMap<>();
    private static Map<String, NetDataBlockDef> defLookup = new HashMap<>();

    public static class NetDataBlockDef {
        String name;
        UUID guid;
        //public Func<NetDataBlock> createFunc;
        HashSet<String> fields;

        Method createFunc;

        Class<? extends NetDataBlock> clazz;

        NetDataBlockDef() {

        }

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

    private static Map<String, PropertyCallbacks<? extends NetDataBlock>> GetPropertiesFor(Class<? extends NetDataBlock> cls) throws Throwable {
        try {
            Method m = cls.getDeclaredMethod("GetProperties");
            if (!Map.class.isAssignableFrom(m.getReturnType())) {
                throw new IllegalArgumentException("'GetProperties' method must return a map of String -> PropertyCallbacks!");
            }
            return (Map<String, PropertyCallbacks<? extends NetDataBlock>>) m.invoke(null);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class must have a 'GetProperties' method!");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("'GetProperties' method must be accessible!");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    public static void RegisterBlockType(Class<? extends NetDataBlock> cls) throws Throwable {
        NetworkDataBlock dblock = cls.getAnnotation(NetworkDataBlock.class);
        if (dblock == null) {
            throw new IllegalArgumentException("Class to be registered must have a NetworkDataBlock annotation!");
        }

        NetDataBlockDef def = new NetDataBlockDef();
        def.name = dblock.name();
        def.fields = new HashSet<>();
        def.clazz = cls;

        try {
            Method m = cls.getDeclaredMethod("Create", UUID.class);
            if (!NetDataBlock.class.isAssignableFrom(m.getReturnType())) {
                throw new IllegalArgumentException("'Create' method of class " + cls.toString() + "does not return a valid type!");
            }
            def.createFunc = m;
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class to be registered must have a 'Create' static method!");
        }

        Map<String, PropertyCallbacks<? extends NetDataBlock>> props = GetPropertiesFor(cls);
        for (String s : props.keySet())
            def.fields.add(s);

        defLookup.put(def.name, def);
    }

    public static abstract class PropertyCallbacks<T extends NetDataBlock> {
        public abstract byte[] serialize(T self);
        public abstract void deserialize(T self, byte[] data);
    }

    private UUID TypeUUID;
    private Map<Long, PropertyCallbacks<?>> crcSerializer = new HashMap<>();



}

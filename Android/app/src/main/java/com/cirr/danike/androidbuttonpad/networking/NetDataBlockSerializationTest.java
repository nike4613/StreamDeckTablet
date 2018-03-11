package com.cirr.danike.androidbuttonpad.networking;

import com.cirr.danike.androidbuttonpad.networking.annotations.NetworkDataBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by aaron on 2018-03-11.
 */

@NetworkDataBlock(name = "SerializationTest")
public class NetDataBlockSerializationTest extends NetDataBlock {
    public static NetDataBlockSerializationTest Create(UUID uuid) {
        return new NetDataBlockSerializationTest();
    }
    public static Map<String, PropertyCallbacks<? extends NetDataBlock>> GetProperties() {
        Map<String, PropertyCallbacks<? extends NetDataBlock>> map = new HashMap<>();
        map.put("str", new PropertyCallbacks<NetDataBlockSerializationTest>() {

            @Override
            public byte[] serialize(NetDataBlockSerializationTest self) {
                return new byte[0];
            }

            @Override
            public void deserialize(NetDataBlockSerializationTest self, byte[] data) {

            }
        });
        map.put("int", new PropertyCallbacks<NetDataBlockSerializationTest>() {

            @Override
            public byte[] serialize(NetDataBlockSerializationTest self) {
                return new byte[0];
            }

            @Override
            public void deserialize(NetDataBlockSerializationTest self, byte[] data) {

            }
        });

        return map;
    }

    public String s;
    public int i;
}

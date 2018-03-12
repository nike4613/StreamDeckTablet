package com.cirr.danike.androidbuttonpad.networking;

import com.cirr.danike.androidbuttonpad.networking.annotations.NetworkDataBlock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by aaron on 2018-03-11.
 */

@NetworkDataBlock(name = "SerializationTest")
public class NetDataBlockSerializationTest extends NetDataBlock {

    private static UUID typeUuid;

    public NetDataBlockSerializationTest() throws Throwable {
    }

    public static void SetTypeUUID(UUID uuid) {
        typeUuid = uuid;
    }

    public static NetDataBlockSerializationTest Create(UUID uuid) throws Throwable {
        return new NetDataBlockSerializationTest();
    }

    public static Map<String, PropertyCallbacks<? extends NetDataBlock>> GetProperties() {
        Map<String, PropertyCallbacks<? extends NetDataBlock>> map = new HashMap<>();
        map.put("str", new PropertyCallbacks<NetDataBlockSerializationTest>() {

            @Override
            public byte[] serialize(NetDataBlockSerializationTest self) {
                return self.s.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public void deserialize(NetDataBlockSerializationTest self, byte[] data) {
                self.s = new String(data, StandardCharsets.UTF_8);
            }
        });
        map.put("int", new PropertyCallbacks<NetDataBlockSerializationTest>() {

            @Override
            public byte[] serialize(NetDataBlockSerializationTest self) {
                return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(self.i).array();
            }

            @Override
            public void deserialize(NetDataBlockSerializationTest self, byte[] data) {
                self.i = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getInt();
            }
        });

        return map;
    }

    public String s;
    public int i;
}

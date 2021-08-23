package dev.orf1.cobalt.common.packet;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class Packet {
    public static String getType(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject.get("type").getAsString();
    }

    public String toJson() {
        return new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(this);
    }
}

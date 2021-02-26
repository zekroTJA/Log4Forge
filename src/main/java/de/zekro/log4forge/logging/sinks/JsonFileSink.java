package de.zekro.log4forge.logging.sinks;

import com.google.gson.Gson;
import de.zekro.log4forge.logging.Entry;
import net.minecraft.util.math.Vec3i;

class GenericEntry {
    public final String timestamp;
    public final String playerName;
    public final String playerId;
    public final String world;
    public final Vec3i position;
    public final String type;
    public final String affectedObjects;
    public final String notes;

    public GenericEntry(Entry entry) {
        this.timestamp = entry.getTimestamp().toString();
        this.playerName = entry.getPlayer().getDisplayNameString();
        this.playerId = entry.getPlayer().getPersistentID().toString();
        this.world = entry.getWorld().getWorldInfo().getWorldName();
        this.position = entry.getPosition();
        this.type = entry.getType().getName();
        this.affectedObjects = entry.getAffectedObject();
        this.notes = entry.getNotes();
    }
}

/**
 * ISink implementation which writes entries to
 * a file formatted as JSON object.
 */
public class JsonFileSink extends FileSink {

    private final Gson parser;

    public JsonFileSink() {
        this.parser = new Gson();
        this.sinkName = "jsonFileSink";
        this.sinkDescription = "A file sink which formats entries in JSON objects.";
    }

    @Override
    public String format(Entry entry) {
        return parser.toJson(new GenericEntry(entry));
    }
}

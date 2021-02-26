package de.zekro.log4forge.logging;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Date;

/**
 * Event Log Entry.
 */
public class Entry {
    private final Date timestamp;
    private final EntityPlayer player;
    private final World world;
    private final Vec3i position;
    private final EventType type;
    private final String affectedObject;
    private final String notes;

    public Entry(EntityPlayer player,
                 World world,
                 Vec3i position,
                 EventType type)
    { this(player, world, position, type, "", ""); }

    public Entry(EntityPlayer player,
                 World world,
                 Vec3i position,
                 EventType type,
                 String affectedObject)
    { this(player, world, position, type, affectedObject, ""); }

    public Entry(EntityPlayer player,
                 World world,
                 Vec3i position,
                 EventType type,
                 String affectedObject,
                 String notes)
    {
        this.timestamp = new Date();
        this.player = player;
        this.world = world;
        this.position = position;
        this.type = type;
        this.affectedObject = affectedObject;
        this.notes = notes;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public Vec3i getPosition() {
        return position;
    }

    public EventType getType() {
        return type;
    }

    public String getAffectedObject() {
        return affectedObject;
    }

    public String getNotes() {
        return notes;
    }
}

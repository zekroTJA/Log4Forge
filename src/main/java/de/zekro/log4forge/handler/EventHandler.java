package de.zekro.log4forge.handler;

import de.zekro.log4forge.Log4Forge;
import de.zekro.log4forge.logging.Entry;
import de.zekro.log4forge.logging.EventType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Event subscriber which watches specific events
 * and logs them to the main EventLogger instance.
 */
@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void blockPlaceHandler(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer))
            return;

        Log4Forge.getEventLogger().log(new Entry(
                (EntityPlayer) event.getEntity(),
                event.getWorld(),
                event.getPos(),
                EventType.BLOCK_PLACE,
                event.getPlacedBlock().getBlock().getUnlocalizedName()
        ));
    }

    @SubscribeEvent
    public static void blockBreakHandler(BlockEvent.BreakEvent event) {
        Log4Forge.getEventLogger().log(new Entry(
                event.getPlayer(),
                event.getWorld(),
                event.getPos(),
                EventType.BLOCK_BREAK,
                event.getState().getBlock().getUnlocalizedName()
        ));
    }

    @SubscribeEvent
    public static void farmlandTrampleHandler(BlockEvent.FarmlandTrampleEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer))
            return;

        Log4Forge.getEventLogger().log(new Entry(
                (EntityPlayer) event.getEntity(),
                event.getWorld(),
                event.getPos(),
                EventType.FARMLAND_TRAMPLE
        ));
    }

    @SubscribeEvent
    public static void containerHandler(PlayerContainerEvent event) {
        String target;
        if (event.getContainer() instanceof ContainerChest)
            target = "Chest";
        else if (event.getContainer() instanceof ContainerFurnace)
            target = "Furnace";
        else if (event.getContainer() instanceof ContainerBrewingStand)
            target = "Brewing Stand";
        else if (event.getContainer() instanceof ContainerHopper)
            target = "Hopper";
        else if (event.getContainer() instanceof ContainerDispenser)
            target = "Dispenser";
        else
            return;

        EventType type;
        if (event instanceof PlayerContainerEvent.Open)
            type = EventType.OPEN_CONTAINER;
        else if (event instanceof  PlayerContainerEvent.Close)
            type = EventType.CLOSE_CONTAINER;
        else
            return;

        Log4Forge.getEventLogger().log(new Entry(
                event.getEntityPlayer(),
                event.getEntity().getEntityWorld(),
                event.getEntity().getPosition(),
                type,
                target
        ));
    }
}

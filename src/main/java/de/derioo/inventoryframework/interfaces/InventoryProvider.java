package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.implementations.InventoryContentsImpl;
import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.entity.Player;

public interface InventoryProvider {

    /**
     * Used to set the contents
     * @param player the player who opens it
     * @param contents the changeable contents
     */
    default void init(Player player, InventoryContents contents){

    }




}

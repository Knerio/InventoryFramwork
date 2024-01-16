package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.implementations.InventoryContentsImpl;
import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Provides the inventory contents
 */
public interface InventoryProvider {

    /**
     * Used to set the contents
     * @param player the player who opens it
     * @param contents the changeable contents
     */
    default void init(Player player, InventoryContents contents){

    }

    /**
     * Is executed when the inventory is closed
     * @param player the player
     * @param event the event
     */
    default void onClose(Player player, InventoryContents contents, InventoryCloseEvent event) {

    }
    /**
     * Is executed when the inventory is clicked
     * @param player the player
     * @param event the event
     */
    default void onClick(Player player, InventoryContents contents, InventoryClickEvent event) {

    }




}

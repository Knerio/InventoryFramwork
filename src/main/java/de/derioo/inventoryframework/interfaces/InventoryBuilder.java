package de.derioo.inventoryframework.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.index.qual.NonNegative;

/**
 * An interface for the InventoryBuilder
 */
public interface InventoryBuilder {

    /**
     * Has to be executed
     * Used to set up the Inventory
     * @param title the title
     * @param size the size of the inventory
     * @return the builder
     */
    InventoryBuilder setup(String title,@NonNegative int size);

    /**
     * Has to be executed
     * Used to set up the Inventory
     * @param title the title
     * @param size the size of the inventory
     * @param maxPages the fixed max pages
     * @return the builder
     */
    InventoryBuilder setup(String title,@NonNegative int size,@NonNegative int maxPages);

    /**
     * Used to provide the contents of the inventory
     * @param provider the provider
     * @return the builder
     */
    InventoryBuilder provider(InventoryProvider provider);

    /**
     * Builds the inventory, used before open
     * @return the builder
     */
    InventoryBuilder build();


    /**
     * Gets the contents
     * @return the contents
     */
    InventoryContents getContents();

    /**
     * Opens the inventory for 1 player
     * @param player the player
     */
    void open(Player player);

    /**
     * Opens the inventory for more than 1 player
     * @param players the players
     */
    void open(Player... players);

    /**
     * Gets the provider
     * @return the provider
     */
    InventoryProvider getProvider();

    /**
     * Gets the player
     * @return the player
     */
    Player getPlayer();

    /**
     * Updates the inventory, used if you add items after init
     */
    void update();

    /**
     * reloads the inventory
     */
    void reload();

    /**
     * Gets the inventory, can change
     * @return the Inventory
     */
    Inventory getInventory();

    /**
     * Sets the new inventory
     * @param inventory the inv
     */
    void setInventory(Inventory inventory);
}

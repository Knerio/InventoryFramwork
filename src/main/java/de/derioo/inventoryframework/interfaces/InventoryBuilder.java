package de.derioo.inventoryframework.interfaces;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.checkerframework.checker.index.qual.NonNegative;

import javax.annotation.Nonnull;

public interface InventoryBuilder {

    /**
     * Sets the title of the Inventory
     * @param title the title
     */
    InventoryBuilder title(@Nonnull String title);

    /**
     * Sets the rows of the inventory
     * @see InventoryBuilder#size
     * @param rows the rows
     */
    InventoryBuilder rows(@NonNegative int rows);


    /**
     * Sets the maxPage of the inventory
     * @param size the size
     */
    InventoryBuilder maxPage(@NonNegative int size);
    /**
     * Sets the size
     * @see InventoryBuilder#rows
     * @param size the size (max 54)
     */
    InventoryBuilder size(@NonNegative int size);

    /**
     * The Inventory Provider for the Inventory
     * @param provider the provider
     */
    InventoryBuilder provider(InventoryProvider provider);

    /**
     * Builds the inventory
     */
    InventoryBuilder build();


    InventoryContents getContents();

    /**
     * Opens the Inventory for the player
     * @param player the player
     */
    void open(Player player);

    /**
     * Opens the Inventory for the players
     * @param players the players
     */
    void open(Player... players);

    InventoryProvider getProvider();

    Player getPlayer();
}

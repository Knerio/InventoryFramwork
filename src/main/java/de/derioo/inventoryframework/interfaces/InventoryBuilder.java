package de.derioo.inventoryframework.interfaces;

import org.bukkit.entity.Player;

public interface InventoryBuilder {

    InventoryBuilder setup(String title, int size);

    InventoryBuilder setup(String title, int size, int maxPages);

    InventoryBuilder provider(InventoryProvider provider);

    InventoryBuilder build();


    InventoryContents getContents();

    void open(Player player);

    void open(Player... players);

    InventoryProvider getProvider();

    Player getPlayer();
}

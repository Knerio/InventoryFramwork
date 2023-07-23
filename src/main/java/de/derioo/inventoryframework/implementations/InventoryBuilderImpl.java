package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.InventoryProvider;
import de.derioo.inventoryframework.objects.InventoryFramework;
import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class InventoryBuilderImpl implements InventoryBuilder, Listener {

    private String title = "NOT_CONFIGURED";
    private int size = 9;
    private int maxPage;
    private InventoryProvider provider;
    private InventoryType type;

    private Inventory inventory;

    private Player player;

    private InventoryContents contents;


    @Override
    public InventoryBuilder title(@NotNull String t) {
        title = t;
        return this;
    }

    @Override
    public InventoryBuilder rows(@NonNegative int rows) {
        size(rows * 9);
        return this;
    }

    /**
     * has to be set AFTER size
     * @param size the size
     * @return
     */
    @Override
    public InventoryBuilder maxPage(@NonNegative int size) {
        if (contents == null) contents = new InventoryContentsImpl(size, this);
        maxPage = size;
        contents.setMaxPages(maxPage);
        return this;
    }

    @Override
    public InventoryBuilder size(@NonNegative int s) {
        size = s;
        contents = new InventoryContentsImpl(size, this);
        return this;
    }

    @Override
    public InventoryBuilder provider(InventoryProvider p) {
        provider = p;
        return this;
    }

    @Override
    public InventoryBuilder build() {
        if (contents == null) contents = new InventoryContentsImpl(size, this);

        InventoryFramework.initListener(this);
        inventory = Bukkit.createInventory(null, size, title);

        return this;
    }

    @Override
    public InventoryContents getContents() {
        if (contents == null) contents = new InventoryContentsImpl(size, this);
        return contents;
    }


    @Override
    public void open(Player player) {
        this.player = player;

        player.openInventory(inventory);
        provider.init(player, contents);

        for (int i = 0; i < inventory.getSize(); i++) {
            SmartItem item = contents.getItem(i);
            if (item == null) continue;
            inventory.setItem(i, item.getItem());
        }

    }

    @Override
    public void open(Player... players) {
        for (Player player : players) {
            clone().open(player);
        }
    }

    @Override
    public InventoryProvider getProvider() {
        return provider;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public InventoryBuilder clone() {
        return InventoryFramework.builder()
                .title(title)
                .size(size)
                .maxPage(maxPage)
                .provider(provider)
                .build();
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (inventory == null) return;
        if (!e.getInventory().equals(inventory)) return;
        Player p = (Player) e.getWhoClicked();
        e.setCancelled(true);


        int i = 0;
        for (SmartItem item : contents.getItems()) {
            if (e.getRawSlot() == i) {
                item.getConsumer().accept(e, item);
            }

            i++;
        }

        for (i = 0; i < inventory.getSize(); i++) {
            SmartItem item = contents.getItem(i);
            if (item == null) continue;
            inventory.setItem(i, item.getItem());
        }

    }
}
